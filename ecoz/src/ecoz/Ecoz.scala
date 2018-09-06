package ecoz

import ecoz.hmm._
import ecoz.lpc._
import ecoz.signal.{SignalExtractor, Signals}
import ecoz.symbol.SymbolSequences
import ecoz.vq.{Codebooks, VqClassify, VqLearn, VqQuantize}

object Ecoz {

  def usage(error: String = ""): Unit = {
    println(s"""$error
       |
       | ecoz - main program.
       |
       | Usage:
       |
       |   ecoz sig.xtor ...
       |   ecoz sig.show ...
       |   ecoz lpa ...
       |   ecoz lpc ...
       |   ecoz prd.show ...
       |   ecoz vq.learn ...
       |   ecoz cb.show ...
       |   ecoz vq.quantize ...
       |   ecoz seq.show ...
       |   ecoz vq.classify ...
       |   ecoz hmm.learn ...
       |   ecoz hmm.show ...
       |   ecoz hmm.prob ...
       |   ecoz hmm.classify ...
       |   ecoz hmm.test ...
     """.stripMargin)
    sys.exit()
  }

  def main(args: Array[String]): Unit = {
    args.toList match {
      case "sig.xtor" :: rest ⇒
        SignalExtractor.main(rest.toArray)

      case "sig.show" :: wavFilenames ⇒
        Signals.showSignals(wavFilenames)

      case "lpa" :: wavFilenames ⇒
        Lpc.lpaOnSignals(wavFilenames)

      case "lpc" :: rest ⇒
        Lpc.main(rest.toArray)

      case "prd.show" :: prdFilenames ⇒
        Lpc.showPredictors(prdFilenames)

      case "vq.learn" :: rest ⇒
        VqLearn.main(rest)

      case "cb.show" :: rest ⇒
        Codebooks.showCodebooks(rest)

      case "vq.quantize" :: rest ⇒
        VqQuantize.main(rest)

      case "seq.show" :: rest ⇒
        SymbolSequences.showSequences(rest)

      case "vq.classify" :: rest ⇒
        VqClassify.main(rest)

      case "hmm.learn" :: rest ⇒
        HmmLearn.main(rest)

      case "hmm.show" :: rest ⇒
        Hmms.showHmms(rest)

      case "hmm.prob" :: rest ⇒
        HmmProb.showProbabilities(rest)

      case "hmm.classify" :: rest ⇒
        HmmClassify.main(rest)

      case "hmm.test" :: _ ⇒
        HmmTest.run()

      case other :: _ ⇒
        usage(s"unrecognized program or missing arguments: $other")

      case Nil ⇒
        usage()
    }
  }
}
