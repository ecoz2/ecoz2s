This document describes a small but complete classification exercise.
Although only using provided frame selections from a single file
(`HBSe_20151207T070326.wav`), it has served as a basis for the new
implementation in terms of testing and preparations for future exercises.

NOTE: At this point, data file structure as well as indication of some
of the core parameters (eg., order of linear prediction) are still preliminary.

## Base configuration

Captured in `config/src/ecoz/config/Config.scala`.

## Extraction of selections

The extracted selections get created under `../data/signals/`.

    $ ecoz sig.xtor ../data/HBSe_20151207T070326.wav ../data/20151207T070326.txt --all

    $ ls ../data/signals
    _                          bark (D)                   groan + purr               gurgle + trill             purr
    ascending moan             bark?                      groan +purr                gurgle?                    purr (D)
    ascending moan (D)         descending moan            grunt                      gurgle?_ascending _        trill
    ascending moan?            descending moan (F)        grunt echo                 modulated cry              trill (D)
    ascending shriek           descending shriek          grunts                     modulated cry (D)          trill?
    ascending shriek (D)       descending shriek (D)      gurgle                     modulated moan             trumpet?
    bark                       groan                      gurgle + descending shriek modulated moans (F)

    $ ls -l ../data/signals/bark
    total 336
    -rw-r--r--  1 carueda  staff  49266 Aug 28 14:20 from_HBSe_20151207T070326__126.9616_127.730705.wav
    -rw-r--r--  1 carueda  staff  52064 Aug 28 14:20 from_HBSe_20151207T070326__64.29427_65.10707.wav
    -rw-r--r--  1 carueda  staff  61852 Aug 28 14:20 from_HBSe_20151207T070326__73.51441_74.48016.wav

## Linear predictor generation

Generate predictor vectors for all signals:

    $ ecoz lpc -classes ../data/signals/*

**NOTE** look out for occasional 'non positive prediction error' warnings
(I've seen 2 of them so far). Remove the corresponding predictor files.

## Separate predictor vectors

I'm manually doing this, putting training predictors under
`../data/predictors/TRAIN/P36/<className>/`
and testing predictors under
`../data/predictors/TEST/P36/<className>/`.

Note: the basic directory scheme is to put the files under a parent
directory named with the corresponding class.
This applies to predictors as well as to sequences (see below).

## Codebook generation

Only with training predictors:

    $ ecoz vq.learn -e 0.0005  ../data/predictors/TRAIN/P36/*/*.prd

With both training and test predictors:

    $ ecoz vq.learn -e 0.0005 ../data/predictors/TRAIN/P36/*/*.prd ../data/predictors/TEST/P36/*/*.prd

## Quantization

Generate training sequences:

    $ ecoz vq.quantize -cb ../data/codebooks/eps_0.0005__0256.cbook ../data/predictors/TRAIN/P36/*/*.prd

The sequences get generated under `../data/sequences/`.
I'm manually moving them to be under `../data/sequences/TRAIN/`:

    $ mkdir ../data/sequences/TRAIN
    $ mv ../data/sequences/M256 ../data/sequences/TRAIN/

Generate test sequences:

    $ ecoz vq.quantize -cb ../data/codebooks/eps_0.0005__0256.cbook ../data/predictors/TEST/P36/*/*.prd

Ditto for training sequences:

    $ mkdir ../data/sequences/TEST
    $ mv ../data/sequences/M256 ../data/sequences/TEST/

## HMM training

With training sequences:

    $ for class in _  groan grunt gurgle 'gurgle?' purr; do ecoz hmm.learn -N 10 -a 0.005  ../data/sequences/TRAIN/M256/$class/*; done
    $ ecoz hmm.learn -N 10 -a 0.005  ../data/sequences/TRAIN/M256/'ascending moan'/*
    $ ecoz hmm.learn -N 10 -a 0.005  ../data/sequences/TRAIN/M256/'descending moan'/*
    $ ecoz hmm.learn -N 10 -a 0.005  ../data/sequences/TRAIN/M256/'descending shriek'/*
    $ ecoz hmm.learn -N 10 -a 0.005  ../data/sequences/TRAIN/M256/'modulated cry'/*

## HMM based sequence classification

On training sequences:

    $ ecoz hmm.classify -hmm  ../data/hmms/N10__M256/*.hmm -seq ../data/sequences/TRAIN/M256/*/*
    hmms      : 10: "purr", "modulated cry", "gurgle?", "gurgle", "grunt", "groan", "descending shriek", "descending moan", "ascending moan", "_"
    sequences : 65

    *****************************************************************
    Confusion matrix:
           actual \ predicted  [00]  [01]  [02]  [03]  [04]  [05]  [06]  [07]  [08]  [09]  tests  correct  percent
     ------------------------  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  -----  -------  -------
                     "_" [00]    10     0     0     0     0     0     0     0     0     0     10       10  100.00%

        "ascending moan" [01]     0     3     0     0     0     0     0     0     0     0      3        3  100.00%

       "descending moan" [02]     0     0     6     0     0     0     0     0     0     0      6        6  100.00%

     "descending shriek" [03]     0     0     0     4     0     0     0     0     0     0      4        4  100.00%

                 "groan" [04]     0     0     0     0     6     0     0     0     0     0      6        6  100.00%

                 "grunt" [05]     0     0     0     0     0     3     0     0     0     0      3        3  100.00%

                "gurgle" [06]     0     0     0     0     0     0    17     0     0     0     17       17  100.00%

               "gurgle?" [07]     0     0     0     0     0     0     0     4     0     0      4        4  100.00%

         "modulated cry" [08]     0     0     0     0     0     0     0     0     8     0      8        8  100.00%

                  "purr" [09]     0     0     0     0     0     0     0     0     0     4      4        4  100.00%

     ------------------------  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  -----  -------  -------
                                  0     0     0     0     0     0     0     0     0     0     65       65  100.00%

On test sequences:

    $ ecoz hmm.classify -hmm  ../data/hmms/N10__M256/*.hmm -seq ../data/sequences/TEST/M256/*/*
    hmms      : 10: "purr", "modulated cry", "gurgle?", "gurgle", "grunt", "groan", "descending shriek", "descending moan", "ascending moan", "_"
    sequences : 32

    ********************************
    Confusion matrix:
           actual \ predicted  [00]  [01]  [02]  [03]  [04]  [05]  [06]  [07]  [08]  [09]  tests  correct  percent
     ------------------------  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  -----  -------  -------
                     "_" [00]     5     0     0     0     0     0     0     0     1     0      6        5   83.33%

        "ascending moan" [01]     0     1     0     0     0     0     0     0     0     0      1        1  100.00%

       "descending moan" [02]     0     0     3     0     0     0     0     0     0     0      3        3  100.00%

     "descending shriek" [03]     0     0     0     2     0     0     0     0     0     0      2        2  100.00%

                 "groan" [04]     0     0     0     0     4     0     0     0     0     0      4        4  100.00%

                 "grunt" [05]     0     0     0     0     0     0     1     0     0     0      1        0    0.00%

                "gurgle" [06]     0     0     0     0     0     0     7     0     0     0      7        7  100.00%

               "gurgle?" [07]     0     0     0     0     0     0     1     1     0     0      2        1   50.00%

         "modulated cry" [08]     0     0     1     1     0     0     0     0     2     0      4        2   50.00%

                  "purr" [09]     0     0     0     0     0     0     0     0     0     2      2        2  100.00%

     ------------------------  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  -----  -------  -------
                                  0     0     1     1     0     0     2     0     1     0     32       27   84.38%
