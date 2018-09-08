package ecoz.symbol

import java.io._

import ecoz.rpt.magenta

case class SymbolSequence(
                           M: Int,
                           symbols: List[Int],
                           distortion: Option[Float] = None,
                           classNameOpt: Option[String] = None,
                           filenameOpt: Option[String] = None
                         ) {

  val T: Int = symbols.length

  override def toString: String = {
    val distStr = distortion.map { d ⇒
      val c = magenta(d.toString)
      s"  (distortion=$c)"
    }.getOrElse("")

    s"""T = $T  M = $M$distStr
       |seq = ${symbols.mkString("<", ", ", ">")}
     """.stripMargin
  }
}

object SymbolSequences {

  def save(sequence: SymbolSequence, file: File): Unit = {
    val s = new DataOutputStream(new FileOutputStream(file))

    val classNameStr = "%-64s" format sequence.classNameOpt.getOrElse("")
    s.writeBytes(classNameStr.take(64))

    // vocabulary size:
    s.writeShort(sequence.M)

    // number of symbols:
    s.writeShort(sequence.T)

    sequence.symbols foreach { symbol ⇒ s.writeShort(symbol) }

    // distortion (NaN if unknown)
    s.writeFloat(sequence.distortion.getOrElse(Float.NaN))

    s.close()
    println(s"sequence saved: $file")
  }

  def load(file: File): SymbolSequence = {
    val s = new DataInputStream(new FileInputStream(file))
    val classNameArray = new Array[Byte](64)
    s.readFully(classNameArray)
    val className = new String(classNameArray).trim
    val classNameOpt = if (className.nonEmpty) Some(className) else None

    val M = s.readShort()
    val T = s.readShort()

    val symbols = Array.fill[Int](T)(s.readShort())

    // distortion (NaN if unknown)
    val dist = s.readFloat()

    s.close()

    SymbolSequence(M, symbols.toList,
      distortion = if (dist.isNaN) None else Some(dist),
      classNameOpt = classNameOpt,
      filenameOpt = Some(file.getPath)
    )
  }

  def showSequences(filenames: List[String]): Unit = {
    filenames foreach showSequence
  }

  def showSequence(filename: String): Unit = {
    println(s"$filename:")
    showSequence(new File(filename))
  }

  def showSequence(file: File): Unit = {
    showSequence(load(file))
  }

  def showSequence(seq: SymbolSequence): Unit = {
    println(seq.toString)
  }
}
