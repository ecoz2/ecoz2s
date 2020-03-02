package ecoz.hmm

import ecoz.rpt
import ecoz.rpt.{ConfusionMatrix, quoted}
import ecoz.symbol.SymbolSequence

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class HmmClassifier(hmms: List[Hmm],
                    showRanked: Int = 0
                   ) {

  private val classNames = collection.mutable.HashSet[String]()
  classNames ++= hmms.map(_.classNameOpt.get)

  private val bySeqName = collection.mutable.HashMap[String, List[(SymbolSequence, List[(Hmm,BigDecimal)])]]()

  /**
    * Performs classification of the given sequence,
    * @param seq          Sequence
    * @return             Ranked list of models with associated probabilities
    *                     (sorted in descending probability).
    */
  def classifySequence(seq: SymbolSequence
                      ): List[(Hmm,BigDecimal)] = {
    val seqName = seq.classNameOpt.get
    classNames += seqName
    val list = bySeqName.getOrElseUpdate(seqName, List.empty)

    val hmmsAndProbsFut = Future.sequence {
      hmms map { hmm =>
        Future { (hmm, hmm.probability(seq)) }
      }
    }
    val sortedByProbFut = hmmsAndProbsFut map { hmmsAndProbs =>
      hmmsAndProbs.sortBy(-_._2)
    }
    val sortedByProb = Await.result(sortedByProbFut, Duration.Inf)
    bySeqName.update(seqName, (seq, sortedByProb) :: list)
    sortedByProb
  }

  def reportResults(): Unit = {
    val cm = new ConfusionMatrix(classNames.toSet)

    bySeqName foreach { case (seqName, instances) =>
      instances foreach { case (seq, sortedByProb) =>
        val hmmWinner = sortedByProb.head._1
        val hmmWinnerName = hmmWinner.classNameOpt.get
        cm.setWinner(seqName, hmmWinnerName)

        if (seqName != hmmWinnerName && showRanked > 0) {

          // only show until corresponding model
          val respHmmIndex = sortedByProb.indexWhere(_._1.classNameOpt.contains(seqName))
          val take = 1 + math.min(respHmmIndex, showRanked)
          val reportList = sortedByProb.zipWithIndex.take(take)

          println()
          printf(s"  %s sequence (T=%d) %s:\n",
            quoted(seqName), seq.T, seq.filenameOpt.getOrElse("")
          )
          reportList foreach { case ((hmm, prob), rank) =>
            val hmmNameStr = "%s" format quoted(hmm.classNameOpt.get)

            val (asterisk, probStr) = if (hmm.classNameOpt.contains(seqName))
              ("*", rpt.magenta(prob).toString())
            else
              (" ", prob.toString())

            printf(s"  %s [%02d] %-24s p = %s\n",
              asterisk, rank, hmmNameStr, probStr
            )
          }
          println()
        }
      }
    }

    cm.show()
  }
}
