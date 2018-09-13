2018-09-13

- add ToEcoz2, an ad hoc conversion tool export sequences to previous
  ecoz2 system.

2018-09-07

- hmm.learn: more micro optimization using while loops
- hmm.learn: save model every minute (not upon every refinement)
- some compiler flags for optimization
- hmm.learn: allocate various matrices only once
- hmm: check env var RANDOM_SEED to set seed for random values, which
  facilitates performance evaluation upon changes in code.
  For example:

        RANDOM_SEED=0 ecoz hmm.learn -N 10 -a 0.005  data/sequences/TRAIN/M256/bellow/*

- hmm.learn: micro optimization for sequence product probability calculation
- classifySequence: use futures to speed up probability calculation
  across de hmm models
- include sequence filename oin report of model rankings

- ex2: a second, also preliminary exercise with 19 classes from the
  10 labelled song files

- hmm.classify -sr #: only show until corresponding model
- fix mistake in extracting description column
  repeating ex2..

- vp.learn: add -take option (for T max number of symbols)
- lpc: add -minpc and -take options
- hmm.classify: show marker for corresponding model when using -sr option

2018-09-06

- some adjustments while starting exercise with 10 sound files
    - hmm.classify: use digit as a marker to show ranking during progress
      (starting with '0' as correct classification, and using 'x' when
      ranking is >= 10)
    - vq.quantize follows the TRAIN/TEST destination pattern depending on
      whether the input predictor files have 'TRAIN/' or 'TEST/' in their paths
    - new -split option for lpc command, which allows to put the
      generated predictors into two different training and test subsets.
    - sig.xtor: ignore selections with empty description

- refer to `data/` (instead of `../data/`) as base data directory

- update readme and move exercise description to exercises/ex1/
- refact: move Vq* elements to ecoz.vq package
- refact: some general renaming
  (eg., "lpa" for analysis, "lpc" for actual coding)

2018-09-05

- hmm.classify: -sr option now with argument to indicate number of highest
  ranked models to show (with probabilities) for each unrecognized sequence

- first complete HMM exercise with separation of training and test sequences

- more condensed confusion matrix by using indices to class names

- hmm.classify: ignore sequences with no hmm model for associated class.
  This allows to run, for example:
  `ecoz hmm.classify -hmm  data/hmms/N10__M128/*.hmm -seq data/sequences/M128/*/*`
  when there may not be hmm models for all class names indicated in the
  given sequences.

- readme: update instructions of basic "closed" test
- lpc: use simple name in given className path.
  This facilitates running like this:
  `ecoz lpc -classes data/signals/*`

2018-09-04

- put predictor files under `predictors/P%d/`
- put quantized sequences under `sequences/M%d/`
- put trained HMM models under `hmms/N%d__M%d/`
- remove trailing insignificant zeroes when saving hmm model.
  The default BigDecimal.toString method generates representations with
  many such zeros making the resulting models unnecessarily huge.

- good preliminary results
- hmm.classify: show progress with colored dots depending on ranked models
- adjustments/clarifications related with codebook's `raas`

2018-09-03

- implement hmm.learn and hmm.classify.
  Good initial results
- initial
- implement vq.classify
- capture class name in codebook generation

2018-08-31

- implement vq.quantize
- fix: do gain normalize autocorrelation (not predictor coefficients)
- fix growCodebook;  generate report;  initial codebooks
- fix: save autocorrelations for predictor file
  (not the predictor coefficients themselves)

2018-08-30

- more on initial vq.learn implementation;
  dealing with empty cells.

        ecoz lpc -classes "groan + purr" "descending shriek" "descending moan" "ascending shriek" _
        ecoz lpc -classes bark groan grunts gurgle purr trill "purr (D)" "modulated cry" "gurgle?" "gurgle + descending shriek"

        ecoz vq.learn -p prefijo data/predictors/*/*.prd


2018-08-29

- preparations for vq.learn and vq.quantize
- some more predictor file handling

2018-08-28

- initial commits with preliminaries for selection extraction from wav file,
  linear prediction, and predictor file handling
