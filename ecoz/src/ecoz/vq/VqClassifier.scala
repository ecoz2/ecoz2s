package ecoz.vq

import ecoz.lpc.Predictor
import ecoz.rpt.ConfusionMatrix

class VqClassifier(codebooks: List[Codebook]) {

  private val classNames = collection.mutable.HashSet[String]()
  classNames ++= codebooks.map(_.classNameOpt.get)

  private val byPrdName = collection.mutable.HashMap[String, List[List[Float]]]()

  def addPredictor(predictor: Predictor, prdName: String): Unit = {
    classNames += prdName
    val list = byPrdName.getOrElseUpdate(prdName, List.empty)
    val distortions = codebooks map { codebook ⇒
      codebook.quantize(predictor).distortion.get
    }
    byPrdName.update(prdName, distortions :: list)
  }

  def reportResults(): Unit = {
    val cm = new ConfusionMatrix(classNames.toSet)
    byPrdName foreach { case (prdName, instances) ⇒
      //println(s"${quoted(prdName)} ${instances.length} instances")
      instances foreach { instance ⇒
        val sortedByDist = instance.zipWithIndex.sortBy(_._2)
        val winner = sortedByDist.head
        val codebook = codebooks(winner._2)
        cm.setWinner(prdName, codebook.classNameOpt.get)
      }
    }
    cm.show()
  }
}
