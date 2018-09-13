package ecoz.vq

import java.io.File

import ecoz.config.Config
import ecoz.config.Config.dir
import ecoz.config.Config.lpa.P
import ecoz.lpc._
import ecoz.rpt._


object VqLearn {

  private val defaultEpsilonStr = "0.005"
  private val defaultTakeT = Int.MaxValue

  def usage(error: String = ""): Unit = {
    println(s"""$error
       |
       | vq.learn - creates codebooks for vector quantization.
       |
       | vq.learn [options] <predictor> ...
       |
       | Options:
       |   -w className
       |   -p prefix
       |   -e epsilon        ($defaultEpsilonStr)
       |   -take #           take up this # of vectors from each predictor ($defaultTakeT)
       |
       | Predictor files are read from ${dir.predictors}/.
       | Codebooks are generated under ${dir.codebooks}/.
     """.stripMargin)
      sys.exit()
  }


  def main(args: List[String]): Unit = {
    var classNameOpt: Option[String] = None
    var prefixOpt: Option[String] = None
    var epsilonStr = defaultEpsilonStr
    var takeT = defaultTakeT
    var prdFilenames = List[String]()

    def processArgs(opts: List[String]): Unit = {
      opts match {
        case "-w" :: className :: rest ⇒
          classNameOpt = Some(className)
          processArgs(rest)

        case "-p" :: pref :: rest ⇒
          prefixOpt = Some(pref)
          processArgs(rest)

        case "-e" :: eps :: rest ⇒
          epsilonStr = eps
          processArgs(rest)

        case "-take" :: num :: rest ⇒
          takeT = num.toInt
          require(takeT > 0, "-take expects positive number")
          processArgs(rest)

        case opt :: _ if opt.startsWith("-") ⇒
          usage(s"unrecognized option: $opt")

        case filenames ⇒
          prdFilenames = filenames
      }
    }
    processArgs(args)
    if (prdFilenames.isEmpty) {
      usage("indicate predictor files for training")
    }

    val prefix = prefixOpt getOrElse {
      val cnStr = classNameOpt.map(cn ⇒ s"${cn}_"). getOrElse("")
      s"${cnStr}eps_${epsilonStr}_"
    }

    println(
      s"""className   : ${classNameOpt.map(n ⇒ s""""$n"""").getOrElse("(not given)")}
         |prefix      : "$prefix"
         |epsilon     : $epsilonStr
         |predictors  : ${prdFilenames.length}
         |takeT       : $takeT
       """.stripMargin)

    val trainingSet = loadTrainingSet(prdFilenames, takeT)
    println(s"loaded ${trainingSet.length} vectors as training set")

    new VqLearn(prefix, epsilonStr, trainingSet, classNameOpt).learn()
  }

  def loadTrainingSet(prdFilenames: List[String], takeT: Int): Array[Array[Float]] = {
    val allVectors = collection.mutable.MutableList[Array[Float]]()
    prdFilenames foreach { prdFilename ⇒
      val predictor = Predictors.load(new File(prdFilename))
      predictor.vectors.take(takeT) foreach { vector ⇒
        allVectors += vector
      }
    }
    allVectors.toArray
  }
}

class VqLearn(prefix: String,
              epsilonStr: String,
              trainingSet: Array[Array[Float]],
              classNameOpt: Option[String]
             ) {

  private val ε = epsilonStr.toFloat

  private val lpa = new Lpa()

  private val totVectors = trainingSet.length

  private val report = new Report(prefix, totVectors, ε)

  private var codebook: Codebook = _
  private var codebookFile: File = _

  object Cells {
    var cells: Array[Array[Float]] = _
    var cardd: Array[Int] = _
    var discel: Array[Float] = _

    def initCells(): Unit = {
      // all zero vectors:
      cells = Array.fill[Float](codebook.size, P + 1)(0F)
      // cardinality and distortions:
      cardd = Array.fill[Int](codebook.size)(0)
      discel = Array.fill[Float](codebook.size)(0F)
    }

    // adds autocorrelation rx to i-th cell and
    // accumulates the distortion associated to such cell
    def addToCell(i: Int, rx: Array[Float], ddmin: Float): Unit = {
      val cell = cells(i)
      var n = 0
      while (n < (1+P)) {
        cell(n) += rx(n)
        n += 1
      }
      // update cell cardinality;
      cardd(i) += 1

      // update cell distortion:
      discel(i) += ddmin - 1
    }

    def reviewCells(): Unit = if (false) {
      if (cardd.contains(0)) {
        val msg = s"reviewCells: there are empty cell(s)"
        warn(msg)
        //println(s"Sum of cardinalities = ${cardd.sum}")
        //cells.zipWithIndex foreach { case (cell, index) ⇒
        //  println(s"cell $index:  card=${cardd(index)}   distortion=${discel(index)}")
        //  pprint.pprintln(cell)
        //}
        //throw new Exception(msg)
      }
    }
  }

  def learn(): Unit = {
    def prepareCodebookFile(): Unit = if (codebook.size <= Config.maxCodebookSize) {
      codebookFile = new File(
        Config.dir.codebooks,
        "%s_%04d.cbook" format (prefix, codebook.size)
      )
      println("=" * codebookFile.getName.length)
      println(codebookFile)
    }

    var DDprv = Float.MaxValue

    var pass = 0

    codebook = Codebooks.initialCodebook(classNameOpt)

    prepareCodebookFile()

    do {
      Cells.initCells()

      var DD = 0F

      trainingSet foreach { rxg ⇒
        var ddmin = Float.MaxValue

        var raa_min = -1
        var i = 0
        while (i < codebook.size) {
          val dd = distortion(rxg, codebook.raas(i))
          if (dd < ddmin) {
            ddmin = dd
            raa_min = i
          }
          i += 1
        }
        DD += ddmin - 1
        Cells.addToCell(raa_min, rxg, ddmin)
      }

      val avgDistortion = DD / totVectors
      val avgDistortionChange = (DDprv - DD) / DD

      println(" ===> [%2d] codebook.size=%d  DP=%s  DDprv=%g  DD=%g  (DD-DDprv)/DD=%-15g  Dchange=%g" format (
        pass,
        codebook.size,
        magenta("%-12g" format avgDistortion),
        DDprv, DD, (DD-DDprv)/DD,
        avgDistortionChange
      ))

      Cells.reviewCells()

      // calculate reflections and raas:
      calculateReflections()

      if ( pass > 0 && math.abs(avgDistortionChange) < ε) {
        // distortion reduction is insignificant.

        Codebooks.save(codebook, codebookFile)

        val sigma = calculateSigma(codebook, Cells.cells, avgDistortion)
        report.step(codebookFile, pass + 1, avgDistortion, sigma, Cells.cardd, Cells.discel)
        println()

        if (codebook.size <= Config.maxCodebookSize) {
          // prepare things for next double sized codebook
          pass = 0
          growCodebook()
          prepareCodebookFile()
        }
      }
      else {
        pass += 1
      }

      DDprv = DD

    } while (codebook.size <= Config.maxCodebookSize)

    report.closeReport()
  }

  def growCodebook(): Unit = {
    val pert0 = 0.99f
    val pert1 = 1.01f

    val num_raas = codebook.size

    // current reflections:
    val reflections1 = codebook.reflections

    // new reflections (twice the number of vectors):
    val reflections2 = Array.ofDim[Float](2 * num_raas, P + 1)

    // Initialize codewords by perturbing each centroid
    // with the two multiplication factors pert0 and pert1:
    var i = 0
    while (i < num_raas) {
      val curr = reflections1(i)
      val new0 = reflections2(i)
      val new1 = reflections2(num_raas + i)

      var n = 0
      while (n < P + 1) {
        new0(n) = curr(n) * pert0
        new1(n) = curr(n) * pert1
        n += 1
      }
      i += 1
    }

    codebook = Codebook(reflections2, classNameOpt)
  }

  /**
    * Updates each cell's centroid in the form of reflection
    * coefficients by solving the LPC equations corresponding to
    * the average autocorrelation.
    */
  def calculateReflections(): Unit = {
    assert(codebook.size == Cells.cells.length)

    val cardd = Cells.cardd

    val (nonEmptyCells, emptyCells) = cardd.zipWithIndex.partition(_._1 > 0)

    if (false) if (emptyCells.nonEmpty) {
      warn(s"calculateReflections: codebook.size=${codebook.size}: " +
        s"empty cells: ${emptyCells.length}"  //: ${emptyCells.map(_._2).toList}"
      )
    }

    for (i ← Cells.cells.indices) {
      val cardd = Cells.cardd(i)
      val cell = Cells.cells(i)

      // if (cardd == 0) {
      //   println(s"  CELL $i: cardinality=$cardd  codebook.size=${codebook.size}")
      //   //println(s"cell:"); pprint.pprintln(cell)
      // }

      if (cardd > 0) {
        var k = 0
        while (k <= P) {
          cell(k) /= cardd
          k += 1
        }

        val lpaResult = lpa.lpca_r(cell)

        codebook.updateEntryWith(i, lpaResult)

        // normalize accumulated autocorrelation sequence by gain
        // in this cell for the sigma ratio calculation:
        if (lpaResult.pe != 0F) {
          k = 0
          while (k <= P) {
            cell(k) /= lpaResult.pe
            k += 1
          }
        }
      }
    }
  }

  /**
    * Computes `dpc / avgDistortion`, the sigma ratio for a given codebook,
    * where `dpc` is the average inter-cell distortion
    * and `avgDistortion` is the average intra-cell distortion
    */
  def calculateSigma(codebook: Codebook,
                     cells: Array[Array[Float]],
                     avgDistortion: Float
                     ): Float = {
    var dpc = 0F
    for ((raa, i) ← codebook.raas.zipWithIndex) {
      var dis_i = 0F
      for ((rcc, j) ← cells.zipWithIndex) {
        if ( i != j ) {
				  dis_i += distortion(rcc, raa) - 1
        }
      }
      dpc += dis_i
    }
    dpc /= (codebook.size * (codebook.size - 1))

    dpc / avgDistortion
  }
}
