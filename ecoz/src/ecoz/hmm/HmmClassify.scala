package ecoz.hmm

import java.io.File

import ecoz.rpt._
import ecoz.symbol.SymbolSequences

object HmmClassify {

  def usage(error: String = ""): Unit = {
    println(s"""$error
       |
       | hmm.classify - HMM based pattern recognition.
       |
       | hmm.classify -hmm <hmm> ... -seq <sequence> ...
       |
       | Options:
       |   -sr <n>     Show n most probable models per unrecognized sequence
       |
       | Classifies each sequence according to given models.
     """.stripMargin)
      sys.exit()
  }

  def main(args: List[String]): Unit = {
    var hmmFilenames = List[String]()
    var seqFilenames = List[String]()
    var showRanked = 0

    def processArgs(opts: List[String]): Unit = {
      opts match {
        case "-hmm" :: rest ⇒
          val (hmms, rest2) = rest.span(!_.startsWith("-"))
          hmmFilenames = hmms
          processArgs(rest2)

        case "-seq" :: rest ⇒
          val (seqs, rest2) = rest.span(!_.startsWith("-"))
          seqFilenames = seqs
          processArgs(rest2)

        case "-sr" :: num :: rest ⇒
          showRanked = num.toInt
          processArgs(rest)

        case opt :: _ ⇒
          usage(s"unrecognized option or missing arguments: $opt")

        case Nil ⇒
      }
    }
    processArgs(args)
    if (hmmFilenames.isEmpty) {
      usage("Indicate the hmm model(s)")
    }
    if (seqFilenames.isEmpty) {
      usage("Indicate the sequence files to be classified")
    }

    // load hmms right away, but process sequences one by one:

    var hmms = List[Hmm]()

    hmmFilenames foreach { n ⇒
      val cb = Hmms.load(new File(n))
      cb.classNameOpt match {
        case Some(_) ⇒
          hmms = cb :: hmms

        case None ⇒
          warn(s"Unnamed hmm ignored: $n")
      }
    }

    if (hmms.isEmpty) {
      usage("Indicate hmm(s) with explicit associated class")
    }

    val hmmNames = hmms.map(_.classNameOpt.get)

    println(
      s"""hmms      : ${hmms.length}: ${hmmNames.map(quoted).mkString(", ")}
         |sequences : ${seqFilenames.length}
       """.stripMargin)

    val sequences = seqFilenames map (fn ⇒ SymbolSequences.load(new File(fn)))
    val namedSequences = sequences filter { seq ⇒
      seq.classNameOpt.isDefined && hmmNames.contains(seq.classNameOpt.get)
    }

    val classifier = new HmmClassifier(hmms, showRanked)

    namedSequences foreach { seq ⇒
      val hmmAndProbs = classifier.classifySequence(seq)
      val className = seq.classNameOpt.get
      print(coloredDot(className, hmmAndProbs))
      Console.flush()
    }

    println()

    classifier.reportResults()
  }

  private def coloredDot(seqName: String, hmmAndProbs: List[(Hmm,BigDecimal)]): String = {
    val rank = hmmAndProbs.zipWithIndex.find(_._1._1.classNameOpt.contains(seqName))
      .map(_._2).getOrElse(Int.MaxValue)
    coloredRankMarker(rank)
  }
}
