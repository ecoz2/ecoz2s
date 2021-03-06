package ecoz.hmm

import java.io._

import ecoz.hmm.HmmType.HmmType
import ecoz.symbol.SymbolSequence

object Hmms {

  def create(className: String,
             N: Int, M: Int, typ: HmmType,
             sequencesOpt: Option[List[SymbolSequence]] = None
            ): Hmm = {

    def createPi(): Array[BigDecimal] = typ match {
      case HmmType.Uniform =>
        uniformDistribution(N)

      case _ =>
        randomDistribution(N)
    }

    def createA(): Array[Array[BigDecimal]] = {
      val A = Array.ofDim[BigDecimal](N, N)
      typ match {
        case HmmType.Cascade3 | HmmType.Cascade3 =>
          val len = if (typ == HmmType.Cascade3) 3 else 2
          var from = 0
          for (i <- A.indices) {
            A(i) = cascadeDistribution(N, from, len)
            from += 1
          }

        case HmmType.Random =>
          for (i <- A.indices) {
            A(i) = randomDistribution(N)
          }

        case HmmType.Uniform =>
          for (i <- A.indices) {
            A(i) = uniformDistribution(N)
          }
      }
      A
    }

    def createB(): Array[Array[BigDecimal]] = {
      val B = Array.ofDim[BigDecimal](N, M)
      for (i <- B.indices) {
        B(i) = randomDistribution(M)
      }
      B
    }

    val pi = createPi()
    val A = createA()
    val B = createB()

    Hmm(pi, A, B, classNameOpt = Some(className))
  }

  def save(hmm: Hmm, file: File): Unit = {
    val s = new PrintWriter(file)

    s.println("HMM")
    s.println("className=" + hmm.classNameOpt.getOrElse(""))

    s.println("N=" + hmm.N)
    s.println("M=" + hmm.M)

    s.println("pi=")
    hmm.pi foreach writeValue

    s.println("A=")
    hmm.A foreach { _ foreach writeValue }

    s.println("B=")
    hmm.B foreach { _ foreach writeValue }

    s.close()
    println(s"hmm saved: $file")

    def writeValue(v: BigDecimal): Unit = {
      val str0 = v.toString()
      val str = if (str0.indexOf('E') < 0) {
        // remove any trailing insignificant zeroes:
        val pointPos = str0.lastIndexOf('.')
        if (pointPos >= 0) str0.replaceFirst("0+$", "")
        else str0
      }
      else {
        // haven't seen such issue with representation having exponent ('E')
        str0
      }

      s.println(str)
    }
  }

  def load(file: File): Hmm = {
    val s = new BufferedReader(new InputStreamReader(new FileInputStream(file)))

    def read(prefix: String): String = {
      val line = s.readLine()
      assert(line.startsWith(prefix), s"line='$line'  prefix='$prefix'")
      line.substring(prefix.length).trim
    }

    def readValue(): BigDecimal = BigDecimal(s.readLine())

    read("HMM")

    val className = read("className=")
    val classNameOpt = if (className.nonEmpty) Some(className) else None

    val N = read("N=").toInt
    val M = read("M=").toInt

    read("pi=")
    val pi = Array.fill[BigDecimal](N)(readValue())
    read("A=")
    val A = Array.fill[BigDecimal](N, N)(readValue())
    read("B=")
    val B = Array.fill[BigDecimal](N, M)(readValue())
    s.close()

    Hmm(pi, A, B, classNameOpt)
  }

  def showHmms(filenames: List[String]): Unit = {
    filenames foreach showHmm
  }

  def showHmm(filename: String): Unit = {
    val hmm = load(new File(filename))
    println(s"\n$filename:\n$hmm")
  }
}
