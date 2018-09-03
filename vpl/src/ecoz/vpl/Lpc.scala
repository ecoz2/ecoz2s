package ecoz.vpl

import ecoz.config.Config.lpc
import ecoz.rpt.warn
import ecoz.signal.Signal

case class LpcaResult(r: Array[Float],  // autocorrelation
                      rc: Array[Float], // reflection coefficients
                      a: Array[Float],  // predictor coefficients
                      pe: Float         // prediction error
                     )

case class LpcaException(msg: String) extends Exception(msg)


class Lpc(P: Int = lpc.P,
          winLenMs: Int = lpc.windowLengthMs,
          offsetLenMs: Int = lpc.offsetLengthMs,
         ) {

  def lpcvector(signal: Signal): List[LpcaResult] = {
    val R = signal.sampleRate
    val winLen = ( (winLenMs * R) / 1000 ).toInt

    if (winLen > signal.x.length) {
      throw LpcaException("signal is too short")
    }

    val offsetLen = ( (offsetLenMs * R) / 1000 ).toInt

    // T: total number of sections
    val T = {
      val t = (signal.x.length - (winLen-offsetLen)) / offsetLen
      // discard last section if incomplete:
      if ( (t-1)*offsetLen + winLen > signal.x.length )
        t - 1
      else t
    }

    println(s" lpcvector: T = $T")

    val hamm = hamming(winLen)

    // section under analysis
    val section = new Array[Float](winLen)

    val results = collection.mutable.MutableList[LpcaResult]()

    // perform LPC analysis to each section:
    for (t ← 0 until T) {
      // println(s"analysing section: t = $t")
      var p = t * offsetLen

      // apply hamming weighting:
      for (n ← 0 until winLen) {
        section(n) = hamm(n) * signal.x(p)
        p += 1
      }

      try {
        val lpcaResult = lpca(section)
        // gain normalize autocorrelation:
        if (lpcaResult.pe != 0F) {
          val r = lpcaResult.r
          for (n ← r.indices) {
            r(n) /= lpcaResult.pe
          }
        }
        else {
          println("No prediction error!")
        }
        results += lpcaResult
      }
      catch {
        case e: LpcaException ⇒
          println(s"problem with section t=$t: " + e.getMessage)
      }
    }

    results.toList
  }

  /**
    * lpca: Computes predictor coefficients and reflection coefficients
    * for an input time series x by autocorrelation method
    * using Levinson (Durbin) recursion.
    * (Parsons, 1987)
    */
  def lpca(x: Array[Float]): LpcaResult = {

    val N = x.length

    // autocorrelation:
    val r = new Array[Float](P + 1)

    // compute autocorrelation:
    for (i ← 0 to P) {
      var sum = 0F
      for (k ← 0 until N - i) {
        sum += x(k) * x(k + i)
      }
      r(i) = sum
    }

    lpca_r(r)
  }

  /**
    * lpca_r: Computes predictor coefficients and reflection coefficients
    * for a given autocorrelation sequence using Levinson (Durbin) recursion.
    * (Parsons, 1987)
    *
    * @param r   autocorrelation
    */
  def lpca_r(r: Array[Float]): LpcaResult = {

    val r0 = r(0)
    if (r0 == 0F) {
      throw LpcaException("lpca_r: signal power is zero")
    }

    // compute prediction coefficients and reflection coefficients
    // note: first entry (0) of reflection vector is ignored.
    val rc = new Array[Float](P + 1)
    val a = new Array[Float](P + 1)

    var pe = r0
    a(0) = 1F

    for (k ← 1 to P) {
      // new reflection coefficient:
      var sum = 0F
      for (i ← 1 to k) {
        sum -= a(k-i) * r(i)
      }
      val akk = sum / pe

      //assert(math.abs(akk) <= 1F, s"lpca_r: akk: |$akk| not <= 1\n r=${r.toList}")

      rc(k) = akk

      // new prediction coefficients:
      a(k) = akk

      val k2 = k >> 1
      for (i ← 1 to k2) {
        val ai = a(i)
        val aj = a(k-i)
        a(i) = ai + akk*aj
        a(k-i) = aj + akk*ai
      }

      // new prediction error:
      pe *= (1F - akk*akk)
      if (pe <= 0F) {
        warn(s"lpca_r: non positive prediction error: pe=$pe  akk=$akk\n r=${r.toList}")
        //throw LpcaException(s"lpca_r: non positive prediction error: $pe\n r=${r.toList}")
      }
    }
    LpcaResult(r = r, rc = rc, a = a, pe = pe)
  }

  /**
    * lpca_rc: Computes predictor coefficients
    * given a reflection vector.
    * (Parsons, 1987)
    *
    * @param rc   reflection vector
    */
  def lpca_rc(rc: Array[Float]): Array[Float] = {
    val a = new Array[Float](P + 1)
    a(0) = 1F
    for (k ← 1 to P) {
      // reflection coefficient:
      val akk = rc(k)

      if (math.abs(akk) > 1F) {
        warn(s"lpca_rc: akk: |$akk| not <= 1\n rc=${rc.toList}")
      }
      //assert(math.abs(akk) <= 1F, s"lpca_rc: akk: |$akk| not <= 1\n rc=${rc.toList}")

      // new prediction coefficients:
      a(k) = akk

      val k2 = k >> 1
      for (i ← 1 to k2) {
        val ai = a(i)
        val aj = a(k-i)
        a(i) = ai + akk*aj
        a(k-i) = aj + akk*ai
      }
    }
    a
  }

  def hamming(length: Int): Array[Float] = {
    val length1 = length - 1
    val x = new Array[Float](length)
    for (n ← 0 until length) {
      x(n) = (.54 -.46 * math.cos(n * twoPi / length1)).toFloat
    }
    x
  }

  private val twoPi = 2 * math.Pi
}
