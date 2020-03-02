# ECOZ

This is a Scala implementation of Linear Predictive Coding and
Hidden Markov Modeling for pattern recognition.

## System setup

This project uses [Mill](http://www.lihaoyi.com/mill/) as build tool.

Run the following to generate the executable jar file:

    $ ./mill ecoz.assembly

As a convenience:

    $ alias ecoz='java -jar ./out/ecoz/assembly/dest/out.jar'

A number of possible commands can be run through the main program:

    $ ecoz
     ecoz - main program.

     Usage:

       ecoz sig.xtor ...
       ecoz sig.show ...
       ecoz lpa ...
       ecoz lpc ...
       ecoz prd.show ...
       ecoz vq.learn ...
       ecoz cb.show ...
       ecoz vq.quantize ...
       ecoz seq.show ...
       ecoz vq.classify ...
       ecoz hmm.learn ...
       ecoz hmm.show ...
       ecoz hmm.prob ...
       ecoz hmm.classify ...
       ecoz hmm.test ...

Call a particular command with no arguments to get usage details, eg:

    $ ecoz lpc
     lpc - LP coding

     lpc -classes <className> ...
     lpc -signals <wav-file> ...

     Predictor files are generated under data/predictors.
     These files are processed by vq.learn for codebook generation
     and by vq.quantize for quantization.


### History

This project is a direct translation of a C implementation I did as part
of my BS thesis on isolated word speech recognition completed in 1993.
