package ecoz.lpc

import java.io._

import ecoz.config.Config.lpa.P
import ecoz.symbol.SymbolSequence

/**
  * A codebook is mainly defined (and saved) in terms of reflection vectors.
  * But here we also maintain the corresponding autocorrelation vectors ("raas")
  * of the predictor coefficient vectors corresponding to those reflections.
  * NOTE: Use `updateEntryWith` exclusively for any updates to this codebook.
  */
case class Codebook(
                    reflections: Array[Array[Float]],
                    classNameOpt: Option[String] = None
                   ) {
  require(reflections.nonEmpty)

  val size: Int = reflections.length

  val P: Int = reflections.head.length - 1

  val raas: Array[Array[Float]] = {
    val vectors = Array.ofDim[Float](size, P + 1)
    val lpa = new Lpa()
    reflections.zipWithIndex foreach { case (rc, index) ⇒
      val a = lpa.lpca_rc(rc)
      updateRaa(a, vectors(index))
    }
    vectors
  }

  def updateEntryWith(index: Int, lpaResult: LpaResult): Unit = {
    require(lpaResult.rc.length == P + 1)

    // set reflection vector:
    Array.copy(lpaResult.rc, 0, reflections(index), 0, P + 1)

    // set autocorrelation vector:
    // TODO just copy lpaResult.r as it should be that already
    updateRaa(lpaResult.a, raas(index))
  }

  private def updateRaa(a: Array[Float], raa: Array[Float]): Unit = {
    for (n ← 0 to P) {
      var sum = 0F
      for (k ← 0 to P - n) {
        sum += a(k) * a(k + n)
      }
      raa(n) = sum
    }
  }

  def quantize(predictor: Predictor): SymbolSequence = {
    val T = predictor.size

    val symbols = new Array[Int](T)
    var ddprm = 0F
    for (t ← 0 until T) {
      val rxg = predictor.vectors(t)
      var ddmin = Float.MaxValue
      var i_min = -1
      for (i ← raas.indices) {
        val dd = distortion(rxg, raas(i))
        if (dd < ddmin) {
          ddmin = dd
          i_min = i
        }
      }
      symbols(t) = i_min
      ddprm += ddmin - 1
    }
    SymbolSequence(size, symbols.toList, Some(ddprm / T),
      predictor.classNameOpt)
  }
}

object Codebooks {

  def initialCodebook(classNameOpt: Option[String]): Codebook = {
    // note: first entry (0) of reflection vector is ignored.

    // 2 vectors in initial codebook:
    val reflections = Array.fill[Float](2, P + 1)(0)
      reflections(0)(1) = -.5F
      reflections(1)(1) = +.5F

      // remaining entries with zero:
      for (i ← 2 until P) {
        reflections(0)(i) = 0F
        reflections(1)(i) = 0F
    }
    val codebook = Codebook(reflections, classNameOpt)
    //println(s"initialCodebook (size=${codebook.size}):")
    //pprint.pprintln(codebook)
    codebook
  }

  def save(codebook: Codebook, file: File): Unit = {
    val s = new DataOutputStream(new FileOutputStream(file))

    val classNameStr = "%-64s" format codebook.classNameOpt.getOrElse("")
    s.writeBytes(classNameStr.take(64))

    // number of vectors:
    s.writeInt(codebook.reflections.length)

    // P + 1: vector size:
    s.writeShort(codebook.reflections.head.length)

    // the vectors
    codebook.reflections foreach { _ foreach s.writeFloat }
    s.close()
    println(s"codebook saved: $file")
    //pprint.pprintln(codebook)
  }

  def load(file: File): Codebook = {
    val s = new DataInputStream(new FileInputStream(file))
    val classNameArray = new Array[Byte](64)
    s.readFully(classNameArray)
    val className = new String(classNameArray).trim
    val classNameOpt = if (className.nonEmpty) Some(className) else None

    val numVectors = s.readInt()
    val Pplus1 = s.readShort()

    val reflections = Array.fill[Float](numVectors, Pplus1)(s.readFloat())
    s.close()

    Codebook(reflections, classNameOpt)
  }

  def showCodebooks(filenames: List[String]): Unit = {
    filenames foreach showCodebook
  }

  def showCodebook(filename: String): Unit = {
    val codebook = load(new File(filename))
    println(s"\n$filename:")
    showCodebook(codebook)
  }

  def showCodebook(codebook: Codebook): Unit = {
    val classNameStr = codebook.classNameOpt.map(b ⇒ s""""$b"""").getOrElse("(unknown)")
    println(s""" className=$classNameStr size=${codebook.size}""")
    codebook.reflections.zipWithIndex foreach { case (vector, index) ⇒
      println(s" %2d: %s" format (index, vector.mkString("[", ", ", "]")))
    }
  }
}
