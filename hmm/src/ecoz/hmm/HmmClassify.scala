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
       | Classifies each sequence according to given models.
     """.stripMargin)
      sys.exit()
  }

  def main(args: List[String]): Unit = {
    var hmmFilenames = List[String]()
    var seqFilenames = List[String]()

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

        case opt :: _ ⇒
          usage(s"unrecognized option or missing arguments: $opt")

        case Nil ⇒
      }
    }
    processArgs(args)
    if (hmmFilenames.isEmpty) {
      usage("indicate hmm(s)")
    }
    if (seqFilenames.isEmpty) {
      usage("indicate sequence files to be recognized")
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

    val classifier = new HmmClassifier(hmms)

    seqFilenames foreach { seqFilename ⇒
      val seq = SymbolSequences.load(new File(seqFilename))

      seq.classNameOpt match {
        case Some(className) ⇒
          classifier.addSequence(seq, className)

        case None ⇒
          warn(s"Unnamed sequence ignored: $seqFilename")
      }
    }

    classifier.reportResults()
  }
}
