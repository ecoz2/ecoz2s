package ecoz.lpc

import java.io._

case class Predictor(
                     vectors: Array[Array[Float]],
                     classNameOpt: Option[String] = None
                    ) {
  val size: Int = vectors.length
}

object Predictors {
  def save(predictor: Predictor, prdFile: File): Unit = {
    val s = new DataOutputStream(new FileOutputStream(prdFile))

    //s.write("<predictor>\32___".map(_.toByte).toArray)

    val classNameStr = "%-64s" format predictor.classNameOpt.getOrElse("")
    s.writeBytes(classNameStr.take(64))

    // T: number of vectors:
    s.writeShort(predictor.vectors.length)

    // P + 1: vector size:
    s.writeShort(predictor.vectors.head.length)

    predictor.vectors foreach { _ foreach s.writeFloat }
    s.close()
  }

  def load(prdFile: File): Predictor = {
    val s = new DataInputStream(new FileInputStream(prdFile))
    val classNameArray = new Array[Byte](64)
    s.readFully(classNameArray)
    val className = new String(classNameArray).trim
    val classNameOpt = if (className.nonEmpty) Some(className) else None

    val T = s.readShort()
    val Pplus1 = s.readShort()

    val vectors = Array.fill[Float](T, Pplus1)(s.readFloat())
    s.close()

    Predictor(vectors, classNameOpt)
  }
}
