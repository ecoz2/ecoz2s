package ecoz.hmm

import ecoz.rpt.ConfusionMatrix
import ecoz.rpt.quoted
import ecoz.symbol.SymbolSequence

class HmmClassifier(hmms: List[Hmm],
                    showRanked: Boolean = false
                   ) {

  private val classNames = collection.mutable.HashSet[String]()
  classNames ++= hmms.map(_.classNameOpt.get)

  private val bySeqName = collection.mutable.HashMap[String, List[List[(Hmm,BigDecimal)]]]()

  /**
    * Performs classification of the given sequence,
    * @param seq          Sequence
    * @param seqName      Sequence's associated class name
    * @return             Ranked list of models with associated probabilities
    *                     (sorted in descending probability).
    */
  def classifySequence(seq: SymbolSequence, seqName: String): List[(Hmm,BigDecimal)] = {
    classNames += seqName
    val list = bySeqName.getOrElseUpdate(seqName, List.empty)
    val hmmsAndProbs = hmms map { hmm ⇒ (hmm, hmm.probability(seq)) }
    val sortedByProb = hmmsAndProbs.sortBy(-_._2)
    bySeqName.update(seqName, sortedByProb :: list)
    sortedByProb
  }

  def reportResults(): Unit = {
    val cm = new ConfusionMatrix(classNames.toSet)
    bySeqName foreach { case (seqName, instances) ⇒
      //println(s"${quoted(seqName)} ${instances.length} instances")
      instances foreach { sortedByProb ⇒
        if (showRanked) {
          println(s":: seqName=$seqName:")
          sortedByProb foreach { case (hmm, prob) ⇒
            val pStr = "%s prob =" format quoted(hmm.classNameOpt.get)
            printf(s"  %20s %s\n", pStr, prob)
          }
        }

        val hmm = sortedByProb.head._1
        cm.setWinner(seqName, hmm.classNameOpt.get)
      }
    }
    cm.show()
  }
}
