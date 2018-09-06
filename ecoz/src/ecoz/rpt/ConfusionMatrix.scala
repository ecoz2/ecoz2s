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

    def formatIndex(i: Int): String = {
      if (sortedNames.length <= 99) "[%02d]" format i
      else "[%03d]" format i
    }

    val colHeaders = sortedNames.indices map formatIndex

    val quotedNames = colHeaders.zipWithIndex map { case (colHeader, i) ⇒
      quoted(sortedNames(i)) + " " + colHeader
    }

    val firstColWidth = quotedNames.map(_.length).max

    val corner = "actual \\ predicted"
    val leftWidth = math.max(corner.length, firstColWidth)
    val colSep = "  "

    val cols = collection.mutable.ArrayBuffer[String]()
    cols += w(corner, leftWidth)
    cols ++= colHeaders.map(n ⇒ w(n, n.length))
    cols += w("tests", "tests".length)
    cols += w("correct", "correct".length)
    cols += w("percent", "percent".length)

    println(" " + cols.mkString(colSep))

    def lines(): Unit = {
      cols.clear()
      cols += div(leftWidth)
      cols ++= colHeaders.map(n ⇒ div(n.length, "="))
      cols += div("tests".length)
      cols += div("correct".length)
      cols += div("percent".length)
      println(" " + cols.mkString(colSep))
    }

    lines()

    var totalTests = 0
    var totalCorrect = 0

    for (i ← matrix.indices) {
      val colVals = matrix(i).zipWithIndex map { case (n, j) ⇒
        val str = w(n, colHeaders(j).length)
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
      cols += w(quotedNames(i), leftWidth)
      cols ++= colVals
      cols += w(tests, "tests".length)
      cols += w(correct, "correct".length)
      cols += w(percent, "percent".length)

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
      val x = w(colVal, colHeaders(j).length)
      cols += (if (colVal > 0) red(x).toString() else x)
    }

    val totalPercent = if (totalTests > 0 )
      "%.2f%%" format 100F * totalCorrect / totalTests
    else ""

    cols += w(totalTests, "tests".length)
    cols += w(totalCorrect, "correct".length)
    cols += w(totalPercent, "percent".length)

    println(" " + cols.mkString(colSep))
  }

  def w(s: Any, w: Int): String = s"%${w}s" format s

  def div(w: Int, s: String = "-"): String = s * w
}
