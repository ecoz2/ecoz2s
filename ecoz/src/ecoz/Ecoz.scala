package ecoz

import ecoz.selXtor.SelXtor
import ecoz.signal.Signals
import ecoz.vpl._
import ecoz.hmm._
import ecoz.symbol.SymbolSequences

object Ecoz {

  def usage(error: String = ""): Unit = {
    println(s"""$error
       |
       | ecoz - main program.
       |
       | Usage:
       |
       |   ecoz selxtor ...
       |   ecoz sig.show ...
       |   ecoz prd.show ...
       |   ecoz vpl ...
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
      case "selxtor" :: rest ⇒
        SelXtor.main(rest.toArray)

      case "sig.show" :: wavFilenames ⇒
        Signals.showSignals(wavFilenames)

      case "lpc" :: wavFilenames ⇒
        Vpl.lpcSignals(wavFilenames)

      case "prd.show" :: prdFilenames ⇒
        Vpl.showPredictors(prdFilenames)

      case "vpl" :: rest ⇒
        Vpl.main(rest.toArray)

      case "vq.learn" :: rest ⇒
        VqLearn.main(rest)

      case "cb.show" :: rest :: Nil ⇒
        Codebooks.showCodebook(rest)

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
