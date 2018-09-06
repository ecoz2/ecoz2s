package ecoz

import ecoz.config.Config.lpa.P

package object vq {
  /**
    * Likelihood ratio measure as defined in Juang et al (1982) (except no -1 here).
    *
    * @param rx   gain normalized autocorrelation
    * @param ra   prediction coefficient autocorrelation
    * @return  distortion
    */
  def distortion(rx: Array[Float], ra: Array[Float]): Float = {
    require(rx.length == ra.length, s"rx.length=${rx.length} != ra.length=${ra.length}")
    require(P + 1 == rx.length, s"P=$P + 1 != rx.length=${rx.length}")

    val term1 = rx(0) * ra(0)
    var term2 = 0F
    for (n ← 1 to P) {
      term2 += rx(n) * ra(n)
    }
    term1 + 2F * term2
  }
}
