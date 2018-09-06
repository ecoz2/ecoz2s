package ecoz.vq

import java.io.File

import ecoz.lpc.Predictors
import ecoz.rpt._

object VqClassify {

  def usage(error: String = ""): Unit = {
    println(s"""$error
       |
       | vq.classify - VQ based pattern recognition.
       |
       | vq.classify -cb <codebook> ... -prd <predictor> ...
       |
       | Classifies each predictor according to given codebooks.
       | Only named codebooks are considered.
     """.stripMargin)
      sys.exit()
  }

  def main(args: List[String]): Unit = {
    var cbFilenames = List[String]()
    var prdFilenames = List[String]()

    def processArgs(opts: List[String]): Unit = {
      opts match {
        case "-cb" :: rest ⇒
          val (cbs, rest2) = rest.span(!_.startsWith("-"))
          cbFilenames = cbs
          processArgs(rest2)

        case "-prd" :: rest ⇒
          val (prds, rest2) = rest.span(!_.startsWith("-"))
          prdFilenames = prds
          processArgs(rest2)

        case opt :: _ ⇒
          usage(s"unrecognized option or missing arguments: $opt")

        case Nil ⇒
      }
    }
    processArgs(args)
    if (cbFilenames.isEmpty) {
      usage("indicate codebook(s)")
    }
    if (prdFilenames.isEmpty) {
      usage("indicate predictor files to be recognized")
    }

    // load codebooks right away, but process predictors one by one:

    var codebooks = List[Codebook]()

    cbFilenames foreach { n ⇒
      val cb = Codebooks.load(new File(n))
      cb.classNameOpt match {
        case Some(_) ⇒
          codebooks = cb :: codebooks

        case None ⇒
          warn(s"Unnamed codebook ignored: $n")
      }
    }

    if (codebooks.isEmpty) {
      usage("Indicate codebook(s) with explicit associated class")
    }

    val cbNames = codebooks.map(_.classNameOpt.get)

    println(
      s"""codebooks   : ${codebooks.length}: ${cbNames.map(quoted).mkString(", ")}
         |predictors  : ${prdFilenames.length}
       """.stripMargin)

    val classifier = new VqClassifier(codebooks)

    prdFilenames foreach { prdFilename ⇒
      val predictor = Predictors.load(new File(prdFilename))

      predictor.classNameOpt match {
        case Some(className) ⇒
          classifier.addPredictor(predictor, className)

        case None ⇒
          warn(s"Unnamed predictor ignored: $prdFilename")
      }
    }

    classifier.reportResults()
  }
}
