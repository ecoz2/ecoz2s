A preliminary classification exercise involving 10 song files.

Summary:

- Signal analysis and quantization:
    - 1120 sound unit files extracted and analysed
    - corresponding to 72 different classes
    - 7/3 used as approx train/test split ratio (-split 0.7),
      resulting in:
        - 779 units for training
        - 341 units for testing
    - however, all of the 1120 units used for:
        - codebook generation (vq.learn)
        - quantization (vq.quantize)

- HMM:
    - tests with 27 of the 72 classes
      (several classes ignored due to too few instances)
    - with the split factor indicated above, this resulted in
        - 565 units for training
        - 256 units for testing
    - N = 10
    - M = 256
    - Took a couple of hours on my laptop to train the 27 models
      (3-4 concurrently trained at a time)
    - Did manual termination of the refinement process to
      a couple of models:
        - descending_moan
        - modulated_moan
        - wops

    - classification performance:
        - on training units: 76%
        - on test units: 59%
        - ranking of the models for the incorrectly classified sequences
          show very close approximation in many cases
        - this suggests (not surprisingly) that training has been insufficient


## Base configuration

Captured in `config/src/ecoz/config/Config.scala`.

## Extraction of selections

The extracted selections get created under `data/signals/`.

    for s in `ls data/songs/selections`; do
        name=`basename $s .txt`
        echo "name=$name"
        ecoz sig.xtor data/songs/HBSe_$name.wav data/songs/selections/$name.txt
    done

    name=20151022T015622
    Reading audio file: data/songs/HBSe_20151022T015622.wav
    AudioFile@r(data/songs/HBSe_20151022T015622.wav,Wave,Int16,1,32000.0,Some(LITTLE_ENDIAN),4467081))
    sampleRate: 32000.000000 Hz  duration: 139.596281 secs
    Loading selection file: data/songs/selections/20151022T015622.txt
                         description : #selections
                  "ascending shriek" :  1
                               "cry" :  6
                             "groan" : 13
                            "gurgle" :  8
                    "modulated moan" :  3
          "modulated moan + upsweep" :  4
                             "trill" :  1
    name=20151023T122324
    Reading audio file: data/songs/HBSe_20151023T122324.wav
    AudioFile@r(data/songs/HBSe_20151023T122324.wav,Wave,Int16,1,32000.0,Some(LITTLE_ENDIAN),13314995))
    sampleRate: 32000.000000 Hz  duration: 416.093594 secs
    Loading selection file: data/songs/selections/20151023T122324.txt
                         description : #selections
                    "ascending moan" : 10
             "ascending moan + purr" :  1
                  "ascending shriek" :  1
                               "cry" : 18
                   "descending moan" :  9
                             "groan" :  7
                            "gurgle" : 20
            "gurgle +modulated moan" :  1
                    "modulated moan" : 13
          "modulated moan + upsweep" :  1
                              "purr" :  3
             "purr + modulated moan" :  1
                              "sigh" :  8
                             "siren" :  1
    name=20151121T040102
    Reading audio file: data/songs/HBSe_20151121T040102.wav
    AudioFile@r(data/songs/HBSe_20151121T040102.wav,Wave,Int16,1,32000.0,Some(LITTLE_ENDIAN),13860382))
    sampleRate: 32000.000000 Hz  duration: 433.136938 secs
    Loading selection file: data/songs/selections/20151121T040102.txt
                         description : #selections
                    "ascending moan" :  3
                  "ascending shriek" :  4
                   "broadband burst" :  1
                   "descending moan" :  9
                             "groan" :  1
                            "gurgle" : 31
                    "modulated moan" : 21
                             "thwop" :  1
                             "trill" :  8
    name=20151207T070326
    Reading audio file: data/songs/HBSe_20151207T070326.wav
    AudioFile@r(data/songs/HBSe_20151207T070326.wav,Wave,Int16,1,32000.0,Some(LITTLE_ENDIAN),12719874))
    sampleRate: 32000.000000 Hz  duration: 397.496063 secs
    Loading selection file: data/songs/selections/20151207T070326.txt
                         description : #selections
                                "BB" : 23
                       "BB w sweeps" :  4
       "broadband burst w echo (BB)" :  1
             "broadband burst(1-16)" :  1
                         "downsweep" :  7
                    "downsweep/moan" :  1
                              "echo" :  1
                             "grunt" :  8
                            "grunts" :  6
                              "moan" : 11
                       "moan w echo" :  1
                  "series of grunts" :  3
                        "short moan" :  1
                  "slight downsweep" :  1
                              "tone" : 20
                 "tone + other freq" :  1
           "tone w slight downsweep" : 10
             "tone w slight upsweep" :  3
                         "tone, vib" :  1
               "tone, vibrato/ moan" :  2
                         "tone/moan" :  1
                           "upsweep" : 11
             "upsweep / short grunt" :  1
                     "upsweep /moan" :  1
                     "upsweep, echo" :  1
                     "upsweep, moan" :  2
                     "upsweep/ moan" :  4
              "upsweep/ short grunt" :  1
    name=20151228T103639
    Reading audio file: data/songs/HBSe_20151228T103639.wav
    AudioFile@r(data/songs/HBSe_20151228T103639.wav,Wave,Int16,1,32000.0,Some(LITTLE_ENDIAN),19364685))
    sampleRate: 32000.000000 Hz  duration: 605.146406 secs
    Loading selection file: data/songs/selections/20151228T103639.txt
                         description : #selections
                    "ascending moan" :  1
                  "ascending shriek" : 25
                            "bellow" :  6
                             "croak" : 11
                   "descending moan" : 20
                 "descending shriek" : 13
                             "groan" :  4
                           "grumble" :  1
                             "grunt" : 17
                            "gurgle" : 58
                        "long grunt" : 15
                              "purr" :  8
                             "snort" :  1
                             "trill" : 24
    name=20161101T153358
    Reading audio file: data/songs/HBSe_20161101T153358.wav
    AudioFile@r(data/songs/HBSe_20161101T153358.wav,Wave,Int16,1,32000.0,Some(LITTLE_ENDIAN),16388075))
    sampleRate: 32000.000000 Hz  duration: 512.127344 secs
    Loading selection file: data/songs/selections/20161101T153358.txt
                         description : #selections
                    "ascending moan" :  9
                  "ascending shriek" :  4
                   "descending moan" : 43
                      "growl+gurgle" :  1
                             "grunt" :  3
                            "gurgle" :  1
    name=20161207T115528
    Reading audio file: data/songs/HBSe_20161207T115528.wav
    AudioFile@r(data/songs/HBSe_20161207T115528.wav,Wave,Int16,1,32000.0,Some(LITTLE_ENDIAN),8885692))
    sampleRate: 32000.000000 Hz  duration: 277.677875 secs
    Loading selection file: data/songs/selections/20161207T115528.txt
                         description : #selections
                    "ascending moan" :  5
                  "ascending shriek" :  5
                             "creek" :  1
                   "descending moan" :  1
                 "descending shriek" :  7
                "descending shriek?" :  1
                             "groan" :  3
                      "groan + purr" :  4
                           "grumble" :  2
                            "gurgle" : 29
                          "low yaps" :  4
                     "modulated cry" :  3
                    "modulated moan" : 13
                             "pulse" :  6
                            "pulses" :  2
                              "purr" :  7
                           "violin?" :  1
    name=20170107T085150
    Reading audio file: data/songs/HBSe_20170107T085150.wav
    AudioFile@r(data/songs/HBSe_20170107T085150.wav,Wave,Int16,1,32000.0,Some(LITTLE_ENDIAN),10481889))
    sampleRate: 32000.000000 Hz  duration: 327.559031 secs
    Loading selection file: data/songs/selections/20170107T085150.txt
                         description : #selections
                   "ascending grunt" :  1
                  "ascending shriek" : 11
                            "bellow" :  5
                  "descending grunt" :  1
                   "descending moan" : 19
                      "grunt (long)" :  4
                         "grunt (s)" :  7
                     "grunt (short)" :  5
                            "gurgle" :  8
                    "modulated moan" :  7
          "modulated moan + upsweep" :  1
    name=20170116T054541
    Reading audio file: data/songs/HBSe_20170116T054541.wav
    AudioFile@r(data/songs/HBSe_20170116T054541.wav,Wave,Int16,1,32000.0,Some(LITTLE_ENDIAN),19654958))
    sampleRate: 32000.000000 Hz  duration: 614.217438 secs
    Loading selection file: data/songs/selections/20170116T054541.txt
                         description : #selections
                    "ascending moan" :  3
                  "ascending shriek" : 39
          "ascending shriek, higher" :  1
                             "creek" :  6
                             "croak" :  1
                               "cry" : 13
                   "descending moan" : 31
                             "groan" :  1
                             "grunt" : 38
                            "grunts" :  8
                            "gurgle" : 14
                    "modulated moan" :  6
                              "purr" : 11
                            "scream" : 11
                         "screech ?" :  1
                        "short moan" :  1
                             "trill" :  7
                              "wops" :  1
    name=20170424T102157
    Reading audio file: data/songs/HBSe_20170424T102157.wav
    AudioFile@r(data/songs/HBSe_20170424T102157.wav,Wave,Int16,1,32000.0,Some(LITTLE_ENDIAN),20579522))
    sampleRate: 32000.000000 Hz  duration: 643.110063 secs
    Loading selection file: data/songs/selections/20170424T102157.txt
                         description : #selections
                    "ascending moan" :  5
                              "bark" :  4
                               "cry" : 15
                   "descending moan" : 12
                             "growl" : 12
                             "grunt" : 38
            "grunt + modulated moan" :  1
                            "grunts" : 12
                            "gurgle" : 21
                          "low yaps" :  5
                     "modulated cry" : 13
                    "modulated moan" : 18
                              "wops" :  8


## Linear predictor generation

Apply LPC on all signals, splitting the generated predictors into
training and test subsets (approx 7/3 ratio):

    $ ecoz lpc -classes -split 0.7 data/signals/*

**NOTE** look out for occasional 'non positive prediction error' warnings.
The corresponding predictor files are not generated.

Training predictors get saved under
`data/predictors/TRAIN/P36/<className>/`
and testing predictors under
`data/predictors/TEST/P36/<className>/`.

Note: the basic directory scheme is to put the files under a parent
directory named with the corresponding class.
This applies to predictors as well as to sequences (see below).

## Codebook generation

Done using both training and test predictors:

    $ ecoz vq.learn -e 0.0005 data/predictors/TRAIN/P36/*/*.prd data/predictors/TEST/P36/*/*.prd

## Quantization

Generate training sequences:

    $ ecoz vq.quantize -cb data/codebooks/eps_0.0005__0256.cbook data/predictors/TRAIN/P36/*/*.prd
    $ ecoz vq.quantize -cb data/codebooks/eps_0.0005__0256.cbook data/predictors/TEST/P36/*/*.prd

The sequences get generated under `data/sequences/TRAIN/`
and `data/sequences/TEST/`, respectively.

## HMM training

With training sequences:

TODO list the actual trainings...


## HMM based sequence classification

On training sequences:

    $ ecoz hmm.classify -hmm  data/hmms/N10__M256/*.hmm -seq data/sequences/TRAIN/M256/*/*
    hmms      : 27: "wops", "upsweep", "trill", "tone", "sigh", "scream", "purr", "pulse", "modulated_moan", "modulated_cry", "moan", "grunts", "grunt", "growl", "groan", "downsweep", "descending_shriek", "descending_moan", "cry", "croak", "creek", "bellow", "bark", "ascending_shriek", "ascending_moan", "BB_w_sweeps", "BB"
    sequences : 779

    00000000000000000011011110200100000000010002x0100001000101100000110400000000000000000000000010000000000000000000000000000000000000000000000000000011001000010000042221222212311110142211111000000100000000000000000111201011000100000021221202001000213131122000000001000000000000000000000000000003001000000000000002111024422342223010000000000000000000000000000000000003020340000000100000000000000000000000000000001132421241200000000000000000010000211111222381000000011104100000000010000000000000000000000000000000000000000000111110000000000000000000000000000000010000000

    Confusion matrix:
           actual \ predicted  [00]  [01]  [02]  [03]  [04]  [05]  [06]  [07]  [08]  [09]  [10]  [11]  [12]  [13]  [14]  [15]  [16]  [17]  [18]  [19]  [20]  [21]  [22]  [23]  [24]  [25]  [26]  tests  correct  percent
     ------------------------  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  -----  -------  -------
                    "BB" [00]    14     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     14       14  100.00%

           "BB_w_sweeps" [01]     0     3     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0      3        3  100.00%

        "ascending_moan" [02]     0     0    17     0     0     0     1     0     4     1     0     0     0     0     0     0     0     0     2     0     0     0     0     0     1     0     0     26       17   65.38%

      "ascending_shriek" [03]     0     0     3    46     0     0     1     1     0     0     3     0     0     0     0     0     0     0     1     0     0     0     0     0     2     0     0     57       46   80.70%

                  "bark" [04]     0     0     0     0     3     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0      3        3  100.00%

                "bellow" [05]     0     0     0     0     0     8     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0      8        8  100.00%

                 "creek" [06]     0     0     0     0     0     0     6     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0      6        6  100.00%

                 "croak" [07]     0     0     0     0     0     0     0     9     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0      9        9  100.00%

                   "cry" [08]     0     0     0     0     0     0     0     0    30     0     0     0     0     0     0     1     0     0     2     0     0     0     0     0     1     0     0     34       30   88.24%

       "descending_moan" [09]     0     0     3     2     0     4     0     2     4    50     0     0     0     0     3     6     0     0     9     0     2     0     1     0    16     0     0    102       50   49.02%

     "descending_shriek" [10]     0     0     0     0     0     0     0     0     0     0    13     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     13       13  100.00%

             "downsweep" [11]     0     0     0     0     0     0     0     0     0     0     0     5     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0      5        5  100.00%

                 "groan" [12]     0     0     0     0     0     0     0     0     1     0     0     0    19     0     0     0     0     0     0     0     0     0     1     0     0     0     0     21       19   90.48%

                 "growl" [13]     0     0     0     0     0     0     0     0     0     0     0     0     0     7     0     0     0     0     0     0     0     0     0     0     0     0     0      7        7  100.00%

                 "grunt" [14]     2     0     0     2     0     2     0     3     0     0     0     0     2     0    47     3     1     0     1     0     1     0     0     1     2     0     0     67       47   70.15%

                "grunts" [15]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0    11     1     0     0     0     0     0     0     0     0     0     0     12       11   91.67%

                  "moan" [16]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0    10     0     0     0     0     0     0     0     0     0     0     10       10  100.00%

         "modulated_cry" [17]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0    11     0     0     0     0     0     0     0     0     0     11       11  100.00%

        "modulated_moan" [18]     0     0     3     0     0     3     0     0     8     4     0     0     3     0     3     0     0     1    30     0     0     0     4     0     0     0     0     59       30   50.85%

                 "pulse" [19]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     3     0     0     0     0     0     0     0      3        3  100.00%

                  "purr" [20]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0    18     0     0     0     1     0     0     19       18   94.74%

                "scream" [21]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     7     0     0     0     0     0      7        7  100.00%

                  "sigh" [22]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     6     0     0     0     0      6        6  100.00%

                  "tone" [23]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0    17     0     0     0     17       17  100.00%

                 "trill" [24]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     5     0     0     0     0     0    25     0     0     30       25   83.33%

               "upsweep" [25]     0     0     0     0     0     0     0     0     0     0     0     1     0     0     0     0     0     0     0     0     0     0     0     0     0     8     0      9        8   88.89%

                  "wops" [26]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     7      7        7  100.00%

     ------------------------  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  -----  -------  -------
                                  2     0     9     4     0     9     2     6    17     5     3     1     5     0     6    10     2     1    20     0     3     0     6     1    23     0     0    565      430   76.11%


On test sequences:

    $ ecoz hmm.classify -hmm  data/hmms/N10__M256/*.hmm -seq data/sequences/TEST/M256/*/*
    hmms      : 27: "wops", "upsweep", "trill", "tone", "sigh", "scream", "purr", "pulse", "modulated_moan", "modulated_cry", "moan", "grunts", "grunt", "growl", "groan", "downsweep", "descending_shriek", "descending_moan", "cry", "croak", "creek", "bellow", "bark", "ascending_shriek", "ascending_moan", "BB_w_sweeps", "BB"
    sequences : 341

    0000000011011100x11x8xx00411113x00000000000000000000x100000x10010000461731000322343111117000000010000000001001110330032000009000000558x8700011244000000000012000303102102003100220451000000000000000001312300002001158x10002604622800000000300011011100000020001

    Confusion matrix:
           actual \ predicted  [00]  [01]  [02]  [03]  [04]  [05]  [06]  [07]  [08]  [09]  [10]  [11]  [12]  [13]  [14]  [15]  [16]  [17]  [18]  [19]  [20]  [21]  [22]  [23]  [24]  [25]  [26]  tests  correct  percent
     ------------------------  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  -----  -------  -------
                    "BB" [00]     8     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0      8        8  100.00%

           "BB_w_sweeps" [01]     1     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0      1        0    0.00%

        "ascending_moan" [02]     0     0     3     0     0     0     0     0     5     1     0     0     0     0     0     1     0     0     0     0     0     0     0     0     0     0     0     10        3   30.00%

      "ascending_shriek" [03]     0     0     1    22     0     0     0     1     0     0     3     0     0     0     0     0     0     2     2     0     1     0     0     0     1     0     0     33       22   66.67%

                  "bark" [04]     0     0     0     0     0     0     0     0     0     0     0     0     0     1     0     0     0     0     0     0     0     0     0     0     0     0     0      1        0    0.00%

                "bellow" [05]     0     0     0     0     0     2     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     1     0     0      3        2   66.67%

                 "creek" [06]     0     0     0     0     0     0     1     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0      1        1  100.00%

                 "croak" [07]     0     0     0     0     0     0     0     2     0     0     0     0     0     0     1     0     0     0     0     0     0     0     0     0     0     0     0      3        2   66.67%

                   "cry" [08]     0     0     0     0     0     0     0     0     9     2     0     0     0     0     0     1     0     0     1     0     1     0     0     0     3     0     1     18        9   50.00%

       "descending_moan" [09]     0     0     1     0     0     2     1     1     5    22     0     0     0     0     0     1     0     0     2     0     0     0     1     0     6     0     0     42       22   52.38%

     "descending_shriek" [10]     0     0     0     1     0     0     0     0     0     0     6     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0      7        6   85.71%

             "downsweep" [11]     0     0     0     0     0     0     0     0     0     0     0     2     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0      2        2  100.00%

                 "groan" [12]     0     0     0     0     0     1     0     1     2     0     0     0     2     0     0     0     0     0     1     0     0     0     0     0     1     0     0      8        2   25.00%

                 "growl" [13]     0     0     0     0     0     0     0     0     1     0     0     0     0     3     0     0     0     0     1     0     0     0     0     0     0     0     0      5        3   60.00%

                 "grunt" [14]     0     0     0     0     0     1     0     1     0     0     0     0     0     2    21     8     0     0     2     0     0     0     0     1     0     0     0     36       21   58.33%

                "grunts" [15]     2     0     0     0     0     0     0     0     0     0     0     0     0     0     0    11     1     0     0     0     0     0     0     0     0     0     0     14       11   78.57%

                  "moan" [16]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     1     0     0     0     0     0     0     0     0     0     0      1        1  100.00%

         "modulated_cry" [17]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     5     0     0     0     0     0     0     0     0     0      5        5  100.00%

        "modulated_moan" [18]     0     0     2     0     0     0     0     0     5     3     0     0     1     0     1     0     0     0     9     0     0     0     0     0     1     0     0     22        9   40.91%

                 "pulse" [19]     0     0     1     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     1     1     0     0     0     0     0     0      3        1   33.33%

                  "purr" [20]     0     0     0     0     0     0     0     1     0     0     0     0     0     0     0     0     0     0     0     0     6     0     2     0     1     0     0     10        6   60.00%

                "scream" [21]     0     0     0     1     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     3     0     0     0     0     0      4        3   75.00%

                  "sigh" [22]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     2     0     0     0     0      2        2  100.00%

                  "tone" [23]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     1     0     2     0      3        1   33.33%

                 "trill" [24]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     1     0     0     0     3     0     0     0     0     0     6     0     0     10        6   60.00%

               "upsweep" [25]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     2     0      2        2  100.00%

                  "wops" [26]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     1     0     0     0     0     0     0     0     0     0     0     0     1      2        1   50.00%

     ------------------------  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  -----  -------  -------
                                  3     0     5     2     0     4     1     5    18     6     3     0     1     3     4    11     1     2    12     0     3     0     3     1    14     2     1    256      151   58.98%

