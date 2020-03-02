package ecoz.hmm

import ecoz.rpt.ConfusionMatrix
import ecoz.symbol.SymbolSequence

object HmmTest {
  private val N = 3
  private val M = 3
  private val ε = BigDecimal(1e-5)
  private val typ = HmmType.default
  private val maxRefinements = -1
  private val valAuto = BigDecimal(0.0003)

  case class Class(className: String, sequences: List[SymbolSequence])

  private val classes = List[Class](
    Class(className = "test1",
      List(
        List(0, 0, 1, 1, 1, 2),
        List(0, 1, 0, 1, 1, 1),
      ).map(l => SymbolSequence(M = M, symbols = l,
        classNameOpt = Some("test1")))
    ),
    Class(className = "test2",
      List(
        List(2, 2, 1, 1, 0, 0),
        List(0, 2, 1, 2, 1, 0),
      ).map(l => SymbolSequence(M = M, symbols = l,
        classNameOpt = Some("test2")))
    ),
    Class(className = "test3",
      List(
        List(1, 1, 2, 1, 0, 0),
        List(0, 1, 2, 2, 1, 0),
      ).map(l => SymbolSequence(M = M, symbols = l,
        classNameOpt = Some("test3")))
    ),
  )

  def run(): Unit = {

    val hmms = classes map { clazz =>
      val hmm = new HmmLearn(
        clazz.className, N, M, typ, ε,
        clazz.sequences,
        maxRefinements,
        valAuto
      ).learn()

      println("Trained hmm=" + hmm)

      println("Probabilities:")

//      clazz.sequences foreach { seq =>
//        val prob = hmm.probability(seq)
//        println(s" seq = " + seq.symbols.mkString("<", ",", ">"))
//        println(s"prob = " + ecoz.rpt.magenta(prob.toString()))
//        println()
//      }

      hmm
    }

    val classNames = classes.map(_.className)
    val cm = new ConfusionMatrix(classNames.toSet)

    val allSeqs = classes.flatMap(_.sequences)
    allSeqs foreach { seq =>
      println(s"     seq = " + seq.symbols.mkString("<", ",", ">"))
      var maxProb = BigDecimal(0)
      var argMax = 0
      hmms.zipWithIndex foreach { case (hmm, index) =>
        val prob = hmm.probability(seq)
        if (maxProb < prob) {
          maxProb = prob
          argMax = index
        }
        println(s" Using hmm for " + hmm.classNameOpt.get)
        println(s"    prob = " + ecoz.rpt.magenta(prob.toString()))
        println()
        (prob, index)
      }
      cm.setWinner(seq.classNameOpt.get, hmms(argMax).classNameOpt.get)
    }
    cm.show()
  }
}
