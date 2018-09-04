2018-09-04

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

        mill ecoz.run vpl -classes "groan + purr" "descending shriek" "descending moan" "ascending shriek" _
        mill ecoz.run vpl -classes bark groan grunts gurgle purr trill "purr (D)" "modulated cry" "gurgle?" "gurgle + descending shriek"

        mill ecoz.run vq.learn -p prefijo ../data/predictors/*/*.prd


2018-08-29

- preparations for vq.learn and vq.quantize
- some more predictor file handling

2018-08-28

- initial commits with preliminaries for selection extraction from wav file,
  linear prediction, and predictor file handling
