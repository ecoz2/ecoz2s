package ecoz

package object hmm {

  def cascadeDistribution(size: Int, from: Int, len: Int): Array[BigDecimal] = {
    require(0 <= from && from < size)
    require(len > 0)

    val d = Array.fill[BigDecimal](size)(0)

    val sup = math.min(from + len, size)
    var acum = BigDecimal(0)

    for (i ← from until sup) {
      val r = 1D + random.nextDouble()
      d(i) = r
      acum += r
    }

    // normalize:
    for (i ← from until sup) {
      d(i) /= acum
    }

    d
  }

  // no zero probabilities
  def randomDistribution(size: Int): Array[BigDecimal] = {
    var acum = BigDecimal(0)
    val d = Array.fill[BigDecimal](size) {
      val r = 1D + random.nextDouble()
      acum += r
      r
    }
    for (i ← d.indices) {
      d(i) /= acum
    }
    d
  }

  def uniformDistribution(size: Int): Array[BigDecimal] = {
    val v = BigDecimal(1) / size
    Array.fill[BigDecimal](size)(v)
  }

  private val random = scala.util.Random
}
