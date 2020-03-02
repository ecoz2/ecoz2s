package ecoz.signal
import de.sciss.synth.io._

import java.io.File

case class Signal(x: Array[Float],
                  sampleRate: Double,
                  filename: Option[String] = None
                 ) {

  val mean: Float = x.sum / x.length

  def zeroMean: Signal = {
    val x = this.x
    val N = x.length
    if (N > 0) {
      val mean = x.sum / N
      if (mean != 0) {
        val y = new Array[Float](N)
        for (n <- x.indices) {
          y(n) = x(n) - mean
        }
        this.copy(x = y)
      }
      else this
    }
    else this
  }

  /**
    * Applies `[1 - .95z-1]` to the signal.
    */
  def preemphasis: Signal = {
    val x = this.x
    val N = x.length
    if (N > 0) {
      val y = new Array[Float](N)
      for (n <- N - 1 to 1 by -1) {
        y(n) = x(n) - .95F * x(n - 1)
      }
      y(0) = x(0)
      this.copy(x = y)
    }
    else this
  }
}


object Signals {
  def load(wavFile: File): Signal = {
    val in = AudioFile.openRead(wavFile)
    val bufSize = 8192
    val buf = in.buffer(bufSize)

    val x = new Array[Float](in.numFrames.toInt)
    var index = 0

    var remain = in.numFrames
    while (remain > 0) {
      val chunkLen = math.min(bufSize, remain).toInt
      in.read(buf, 0, chunkLen)
      var i = 0
      while (i < chunkLen) {
        x(index) = buf(0)(i)
        index += 1
        i += 1
      }
      remain -= chunkLen
    }
    in.close()

    Signal(x,
      sampleRate = in.sampleRate,
      filename = Some(wavFile.getName)
    )
  }

  def showSignals(wavFilenames: List[String]): Unit = {
    wavFilenames foreach showSignal
  }

  def showSignal(wavFilename: String): Unit = {
    val signal = load(new File(wavFilename))
    println(s"\n$wavFilename:")
    println(s"sampleRate = ${signal.sampleRate}")
    val mean = signal.x.sum / signal.x.length
    println(s"mean = $mean")
    pprint.pprintln(signal)
  }
}
