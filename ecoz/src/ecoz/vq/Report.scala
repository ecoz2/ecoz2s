package ecoz.vq

import java.io._

import ecoz.config.Config


class Report(prefix: String,
             totVectors: Long,
             ε: Float
            ) {

  val outFile = new File(Config.dir.codebooks, s"$prefix.rpt")

  println(s"Report: $outFile")
  val pw = new PrintWriter(outFile)

  pw.println(
    s"""CODEBOOK GENERATION REPORT
       |
       | $prefix: $totVectors training vectors. (ε = $ε)
       | Base codebook: (none)
     """.stripMargin
  )

  def step(codebookFile: File, passes: Int, avgDistortion: Float, sigma: Float,
           cardd: Array[Int], discel: Array[Float]
          ): Unit = {
    pw.println("\n  ---  %s (%d passes)   Dprm = %f   σ = %f  ---" format (
      codebookFile.getName, passes, avgDistortion, sigma))

    val emptyCells = cardd.count(_ == 0)
    pw.println(s"cardinalities: (emptyCells=$emptyCells)")
    for (c ← cardd) {
		  pw.print("%8d" format c)
    }
    pw.println()

    pw.println("average distortion per cell:")
    for ((d, i) ← discel.zipWithIndex) {
		  pw.print(
        if (cardd(i) > 0) "%8.3g" format d / cardd(i)
        else              "%8s" format "∞"
      )
    }
    pw.println()
    pw.flush()
  }

  def closeReport(): Unit = {
    pw.close()
  }
}
