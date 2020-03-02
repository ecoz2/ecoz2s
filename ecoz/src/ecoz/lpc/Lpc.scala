package ecoz.lpc

import java.io.File

import ecoz.config.Config.dir
import ecoz.rpt
import ecoz.rpt.warn
import ecoz.signal.Signals

import scala.util.Random


object Lpc {

  private val default_split = 0F
  private val default_minpc = 0
  private val default_takeN = Int.MaxValue

  def usage(error: String = ""): Unit = {
    println(s"""$error
       |
       | lpc - Linear Prediction Coding
       |
       | lpc [-minpc #] [-take #] [-split s] -classes <className> ...
       | lpc -signals <wav-file> ...
       |
       | Predictor files are generated under ${dir.predictors}.
       |
       | -minpc #     only process a class if it has at least this number
       |              of signals ($default_minpc)
       |
       | -take #      but only take (randomly) at most this number of signals ($default_takeN)
       |
       | -split s     put the generated predictors into two different training
       |              and test subsets (with given approx ratio s) ($default_split)
       |
       | Training predictors go under ${dir.predictors}/TRAIN/ and testing under
       | ${dir.predictors}/TEST/.
       | These files are processed by vq.learn for codebook generation
       | and by vq.quantize for quantization.
     """.stripMargin)
      sys.exit()
  }

  private val destDirTrain = new File(dir.predictors, s"TRAIN")
  private val destDirTest = new File(dir.predictors, s"TEST")

  def main(args: Array[String]): Unit = {
    var classNames: List[String] = List.empty
    var split = default_split
    var minpc = default_minpc
    var takeN = default_takeN
    var wavFilenames: List[String] = List.empty

    def processArgs(opts: List[String]): Unit = opts match {
      case "-split" :: s :: rest =>
        split = s.toFloat
        require(0F < split && split < 1F)
        processArgs(rest)

      case "-minpc" :: s :: rest =>
        minpc = s.toInt
        require(minpc > 0)
        processArgs(rest)

      case "-take" :: s :: rest =>
        takeN = s.toInt
        require(takeN > 0)
        processArgs(rest)

      case "-classes" :: c1 :: moreClasses =>
        classNames = c1 :: moreClasses

      case "-signals" :: s1 :: moreSignals =>
        wavFilenames = s1 :: moreSignals

      case opt :: _ =>
        usage(s"missing argument or unrecognized option: $opt")

      case Nil =>
        usage()
    }
    processArgs(args.toList)

    // just in case -take has been indicated
    wavFilenames = Random.shuffle(wavFilenames)

    val lpa = new Lpa()

    val classAndWavFiles: List[(String, List[File])] = if (classNames.nonEmpty) {
      classNames map { className =>
        val classDir = new File(className)
        (classDir.getName, classDir.listFiles().toList)
      }
    }
    else {
      // get parent's name as className, eg:
      // if fn == ".../.../foo/bar.wav', then className = foo
      val names = wavFilenames map { fn => new File(fn) }
      val groups = names.groupBy { n =>
        val path = n.getPath
        val slash = path.lastIndexOf('/')
        if (slash >= 0) {
          val slashPrev = path.lastIndexOf('/', slash - 1)
          if (slashPrev >= 0)
            path.substring(slashPrev + 1, slash)
          else ""
        }
        else ""
      }
      groups.toList
    }

    classAndWavFiles foreach { case (className, wavFiles) =>
      if (minpc <= wavFiles.length) {
        processFiles(className, wavFiles.take(takeN))
      }
      else {
        println(rpt.lightGray(s"\n${rpt.quoted(className)}: insufficient #signals ${wavFiles.length}"))
      }
    }

    def processFiles(className: String, wavFiles: List[File]): Unit = {
      val optPredictors = wavFiles map { getPredictor(className, _) }

      val predictors = optPredictors.flatten

      if (split > 0F) {
        val numTrain = math.round(split * predictors.length)
        val shuffled = Random.shuffle(predictors)
        val (train, test) = shuffled.splitAt(numTrain)

        println(s"${rpt.quoted(className)} split: training=${train.length}  test=${test.length}")

        train foreach { save(_, destDirTrain) }
        test  foreach { save(_, destDirTest) }
      }
      else {
        predictors foreach { save(_, dir.predictors) }
      }

      def save(wp: (File, Predictor), destDir: File): Unit = {
        val (wavFile, predictor) = wp
        val prdFile = {
          val baseDir = new File(destDir, s"P%d" format lpa.P)
          val dirPrd = new File(baseDir, className)
          dirPrd.mkdirs()
          val prdFilename = wavFile.getName.replaceFirst("\\.[^.]*$", ".prd")
          new File(dirPrd, prdFilename)
        }
        Predictors.save(predictor, prdFile)
        println(s" saved $prdFile")
      }
    }

    def getPredictor(className: String, wavFile: File): Option[(File, Predictor)] = {
      var signal = Signals.load(wavFile)

      println(s"\nProcessing: $wavFile")
      //println(s" Samples : ${signal.x.length}")
      //println(s" Mean: ${signal.mean}")

      if (signal.mean != 0) {
        //println(s" - removing mean...")
        signal = signal.zeroMean
      }

      //println(s" - applying preemphasis...")
      signal = signal.preemphasis
      //println(s" Mean after preemphasis: ${signal.mean}")

      try {
        val results = lpa.onSignal(signal)
        // note: we save the autocorrelations `.r` as predictor file
        Some((wavFile, Predictor(
          vectors = results.map(_.r).toArray,
          classNameOpt = Some(className)
        )))
      }
      catch {
        case e: LpaException =>
          warn(s"problem with lpa.onSignal: ${e.getMessage}")
          None
      }
    }
  }

  def lpaOnSignals(wavFilenames: List[String]): Unit = {
    val lpa = new Lpa()
    wavFilenames foreach lpcSignal

    def lpcSignal(wavFilename: String): Unit = {
      val signal = Signals.load(new File(wavFilename))
      println(s"\n$wavFilename:")
      try {
        val results = lpa.onSignal(signal)
        println(s"LPC predictor vectors ${results.length}: ")
        results foreach { res =>
          println("  r = " + res.rc.mkString("[", ", ", "]"))
          println(" rc = " + res.rc.mkString("[", ", ", "]"))
          println("  a = " + res.a.mkString("[", ", ", "]"))
          println(" pe = " + res.pe)
          println()
        }
      }
      catch {
        case e: LpaException =>
          println(s"problem with lpa.onSignal: ${e.getMessage}")
      }
    }
  }

  def showPredictors(prdFilenames: List[String]): Unit = {
    prdFilenames foreach showPredictor
  }

  def showPredictor(prdFilename: String): Unit = {
    val predictor = Predictors.load(new File(prdFilename))
    println(s"\n$prdFilename:")
    showPredictor(predictor)
  }

  def showPredictor(predictor: Predictor): Unit = {
    val T = predictor.vectors.length
    val P = predictor.vectors.head.length - 1
    val classNameStr = predictor.classNameOpt.map(b => s""""$b"""").getOrElse("(unknown)")
    println(s""" className=$classNameStr T=$T P=$P""")
    predictor.vectors.zipWithIndex foreach { case (vector, index) =>
      println(s" %2d: %s" format (index, vector.mkString("[", ", ", "]")))
    }
  }
}
