package ecoz.rpt

class ConfusionMatrix(classNames: Set[String]) {
  private val sortedNames = classNames.toList.sorted

  private val matrix = {
    val n = sortedNames.size
    Array.fill[Int](n, n)(0)
  }

  private def index(className: String): Int = sortedNames.indexOf(className)

  def setWinner(instanceName: String, modelName: String): Unit = {
    matrix(index(instanceName))(index(modelName)) += 1
  }

  def show(): Unit = {
    println("Confusion matrix:")

    val quotedNames = sortedNames map quoted

    val colWidth = quotedNames.map(_.length).max

    val corner = "actual \\ predicted"
    val leftWidth = math.max(corner.length, colWidth)
    val colSep = "  "

    val cols = collection.mutable.ArrayBuffer[String]()
    cols += w(corner, leftWidth)
    cols ++= quotedNames.map(w(_, colWidth))
    cols += w("tests", colWidth)
    cols += w("correct", colWidth)
    cols += w("percent", colWidth)

    println(" " + cols.mkString(colSep))

    def lines(): Unit = {
      cols.clear()
      cols += div(leftWidth)
      cols ++= sortedNames.map(_ ⇒ div(colWidth, "="))
      cols += div(colWidth)
      cols += div(colWidth)
      cols += div(colWidth)
      println(" " + cols.mkString(colSep))
    }

    lines()

    var totalTests = 0
    var totalCorrect = 0

    for (i ← matrix.indices) {
      val colVals = matrix(i).zipWithIndex map { case (n, j) ⇒
        val str = w(n, colWidth)
        if (i == j) {
          if (n > 0) green(str).toString()
          else       str
        }
        else {
          if (n > 0) red(str).toString()
          else       str
        }
      }
      val tests = matrix(i).sum
      val correct = matrix(i)(i)
      val percent = if (tests > 0 )
        "%.2f%%" format 100F * correct / tests
      else ""

      totalTests += tests
      totalCorrect += correct

      cols.clear()
      cols += w(quoted(sortedNames(i)), leftWidth)
      cols ++= colVals
      cols += w(tests, colWidth)
      cols += w(correct, colWidth)
      cols += w(percent, colWidth)

      println(s" ${cols.mkString(colSep)}\n")
    }

    lines()

    // misclassifications:
    cols.clear()
    cols += w("", leftWidth)
    for (j ← matrix.indices) {
      var colVal = 0
      for (i ← matrix.indices) {
        if (i != j) colVal += matrix(i)(j)
      }
      cols += (if (colVal > 0)
        red(w(colVal, colWidth)).toString()
      else
        w(colVal, colWidth))
    }

    val totalPercent = if (totalTests > 0 )
      "%.2f%%" format 100F * totalCorrect / totalTests
    else ""

    cols += w(totalTests, colWidth)
    cols += w(totalCorrect, colWidth)
    cols += w(totalPercent, colWidth)

    println(" " + cols.mkString(colSep))
  }

  def w(s: Any, w: Int): String = s"%${w}s" format s

  def div(w: Int, s: String = "-"): String = s * w
}
