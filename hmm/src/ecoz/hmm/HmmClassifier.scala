package ecoz.hmm

import ecoz.rpt.ConfusionMatrix
import ecoz.rpt.quoted
import ecoz.symbol.SymbolSequence

class HmmClassifier(hmms: List[Hmm],
                    showRanked: Boolean = false
                   ) {

  private val classNames = collection.mutable.HashSet[String]()
  classNames ++= hmms.map(_.classNameOpt.get)

  private val bySeqName = collection.mutable.HashMap[String, List[List[BigDecimal]]]()

  def addSequence(seq: SymbolSequence, seqName: String): Unit = {
    classNames += seqName
    val list = bySeqName.getOrElseUpdate(seqName, List.empty)
    val probs = hmms map { hmm ⇒
      hmm.probability(seq)
    }
    bySeqName.update(seqName, probs :: list)
  }

  def reportResults(): Unit = {
    val cm = new ConfusionMatrix(classNames.toSet)
    bySeqName foreach { case (seqName, instances) ⇒
      //println(s"${quoted(seqName)} ${instances.length} instances")
      instances foreach { instance ⇒
        // sort in decreasing probability:
        val sortedByDist = instance.zipWithIndex.sortBy(-_._1)

        if (showRanked) {
          println(s":: seqName=$seqName:")
          sortedByDist foreach { case (prob, index) ⇒
            val hmm = hmms(index)
            val pStr = "%s prob =" format quoted(hmm.classNameOpt.get)
            printf(s"  %20s %s\n", pStr, prob)
          }
        }

        val winner = sortedByDist.head
        val hmm = hmms(winner._2)
        cm.setWinner(seqName, hmm.classNameOpt.get)
      }
    }
    cm.show()
  }
}
