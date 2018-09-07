package ecoz

import fansi.Attr

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

  def coloredRankMarker(rank: Int, width: Int = 1): String = {
    require(rank >= 0)
    val marker = if (width > 1) {
      s"%0${width}d" format rank
    }
    else {
      if (rank < 10) rank.toString else "x"
    }
    colors(math.min(rank, colors.length -1))(marker).toString()
  }

  def rankMark(rank: Int): String = {
    if (rank < 10) rank.toString else "x"
  }

  def rankColor(rank: Int): Attr = {
    colors(math.min(rank, colors.length -1))
  }

  private val colors = List(
    fansi.Color.LightGreen,
    fansi.Color.LightYellow,
    fansi.Color.LightYellow,
    fansi.Color.Yellow,
    fansi.Color.Yellow,
    fansi.Color.LightGray,
    fansi.Color.LightGray,
    fansi.Color.DarkGray,
    fansi.Color.DarkGray,
    fansi.Color.DarkGray,
  )
}
