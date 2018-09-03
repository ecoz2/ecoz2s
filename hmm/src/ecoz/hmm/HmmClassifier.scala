package ecoz.hmm

import ecoz.rpt.ConfusionMatrix
import ecoz.symbol.SymbolSequence

class HmmClassifier(hmms: List[Hmm]) {

  private val classNames = collection.mutable.HashSet[String]()
  classNames ++= hmms.map(_.classNameOpt.get)

  type Distortion = Float

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
        val sortedByDist = instance.zipWithIndex.sortBy(_._2)
        val winner = sortedByDist.head
        val hmm = hmms(winner._2)
        cm.setWinner(seqName, hmm.classNameOpt.get)
      }
    }
    cm.show()
  }
}
