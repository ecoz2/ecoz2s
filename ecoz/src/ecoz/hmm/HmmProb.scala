package ecoz.hmm

import java.io.File

import ecoz.rpt.lightBlue
import ecoz.symbol.SymbolSequences


object HmmProb {

  def usage(error: String = ""): Unit = {
    println(s"""$error
       | hmm.prob <hmm> <sequence> ...
     """.stripMargin)
    sys.exit()
  }


  def showProbabilities(args: List[String]): Unit = {
    if (args.length < 2) {
      usage()
    }

    val hmmFilename = args.head
    val seqFilenames = args.tail

    val hmm = Hmms.load(new File(hmmFilename))

    seqFilenames foreach { seqFilename =>
      val seq = SymbolSequences.load(new File(seqFilename))

      if (seq.M != hmm.M) {
        println(s"WARN: sequence's M=${seq.M} not conformant to hmm's M=${hmm.M}")
      }

      val prob = hmm.probability(seq)

      println("  seq: %s" format seq.symbols.mkString("<", ", ", ">"))
      println(" prob: %s" format lightBlue(prob.toString()))
    }
  }
}
