A classification exercise using the 10 labelled song files.

Summary:

- Signal analysis and quantization:
    - 756 sound unit files extracted and analysed
    - corresponding to 19 different classes
    - 7/3 used as approx train/test split ratio (-split 0.7),
      but not taking more than 80  units in total per class,
      resulting in:
        - 529 units for training
        - 227 units for testing
    - however, all of the 756 units used for:
        - codebook generation (vq.learn)
        - quantization (vq.quantize)

- HMM:
    - N = 10
    - M = 256
    - Took about 1 hour on my laptop to train the 19 models
      (3-4 concurrently trained at a time)

    - Classification performance:
        - On training units: 77.46%
        - On test units:     56.83%

Possible future variations:
- use M = 512, 1024, ...
- play with different N values (both less than and greater than 10)
  NOTE also that no left-to-right HMM models have been trying yet
  (which may help with better recognition performance)
- set a maximum T of symbols to be considered from each sequence as
  a rough mechanism to deal with possibly too redundant signals (that is,
  having too extended continuation of the same type of unit)


## Base configuration

Captured in `config/src/ecoz/config/Config.scala`.

## Extraction of selections

The extracted selections get created under `data/signals/`.

    for s in `ls data/songs/selections`; do
        name=`basename $s .txt`
        echo "name=$name"
        ecoz sig.xtor data/songs/HBSe_$name.wav data/songs/selections/$name.txt
    done

See output in signals.md


## Linear predictor generation

Apply LPC on all classes having at least 10 signals, processing at
most 80 (randomly chosen) per class, and splitting the generated
predictors into training and test subsets (approx 7/3 ratio):

    $ ecoz lpc -minpc 10 -take 80 -split 0.7 -classes data/signals/*

See output in predictors.md

**NOTE** look out for occasional 'non positive prediction error' warnings.
The corresponding predictor files are not generated.

Training predictors get saved under
`data/predictors/TRAIN/P36/<className>/`
and testing predictors under
`data/predictors/TEST/P36/<className>/`.

`ls -l data/predictors/*/*/*/* | wc -l` -> 756

Note: the basic directory scheme is to put the files under a parent
directory named with the corresponding class.
This applies to predictors as well as to sequences (see below).

## Codebook generation

Done using both training and test predictors:

    $ ecoz vq.learn -e 0.0005 data/predictors/TRAIN/P36/*/*.prd data/predictors/TEST/P36/*/*.prd

See codebooks.md.

## Quantization

Generate training sequences:

    $ ecoz vq.quantize -cb data/codebooks/eps_0.0005__0256.cbook data/predictors/TRAIN/P36/*/*.prd \
        data/predictors/TEST/P36/*/*.prd

See sequences.md

NOTE: manually deleted:

    data/sequences/TRAIN/M256/groan/from_HBSe_20151207T070326__177.84088_178.81975.seq

which corresponds to the apparently mislabelled "groan" unit discussed.

The sequences get generated under `data/sequences/TRAIN/`
and `data/sequences/TEST/`, respectively.

`ls -l data/sequences/*/*/*/* | wc -l` -> 755

## HMM training

With training sequences, corresponding to 19 classes:

    $ ls data/sequences/TRAIN/M256
    ascending_moan    cry               groan_+_purr      gurgle            purr
    ascending_shriek  descending_moan   growl             long_grunt        scream
    bellow            descending_shriek grunt             modulated_cry     trill
    croak             groan             grunts            modulated_moan

    $ for class in ascending_moan    cry               groan_+_purr      gurgle            purr;   do ecoz hmm.learn -N 10 -a 0.005  data/sequences/TRAIN/M256/$class/*; done
    $ for class in ascending_shriek  descending_moan   growl             long_grunt        scream; do ecoz hmm.learn -N 10 -a 0.005  data/sequences/TRAIN/M256/$class/*; done
    $ for class in bellow            descending_shriek grunt             modulated_cry     trill;  do ecoz hmm.learn -N 10 -a 0.005  data/sequences/TRAIN/M256/$class/*; done
    $ for class in croak             groan             grunts            modulated_moan;           do ecoz hmm.learn -N 10 -a 0.005  data/sequences/TRAIN/M256/$class/*; done

hmms.md captures the output for some of the trainings.

Note that except for purr.hmm and modulated_moan.hmm
(which I manually terminated as they were taking a lot of iterations
with no significant improvement in product probability),
all trainings completed automatically per the given -a value.


## HMM based sequence classification

On training sequences:

    $ ecoz hmm.classify -hmm  data/hmms/N10__M256/*.hmm -seq data/sequences/TRAIN/M256/*/* -sr 999

    hmms      : 19: "trill", "scream", "purr", "modulated_moan", "modulated_cry", "long_grunt", "gurgle", "grunts", "grunt", "growl", "groan_+_purr", "groan", "descending_shriek", "descending_moan", "cry", "croak", "bellow", "ascending_shriek", "ascending_moan"
    sequences : 528

    110101121000040000001101000023x20000000001001000001020000000000000000000000000000000000000000000000000000000000000000000001000001000011031233322122222060001100100010110011111020300014350010000000000000000000000000000000020000101021310010000000000000000003115030300000000000100000000000000000000000000000000000000000000000000520100000002230100000000010004230310021120000127220010000000000000000000000000000000001211221000000000000010001000000000002000100000001001010101000020114010001000000000000000010000210000000000000000000000

hmms-train-misclass.txt has the model rankings for the misidentified sequences.

    Confusion matrix:
           actual \ predicted  [00]  [01]  [02]  [03]  [04]  [05]  [06]  [07]  [08]  [09]  [10]  [11]  [12]  [13]  [14]  [15]  [16]  [17]  [18]  tests  correct  percent
     ------------------------  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  -----  -------  -------
        "ascending_moan" [00]    17     1     0     0     4     0     0     0     0     0     0     0     1     0     0     3     1     0     1     28       17   60.71%

      "ascending_shriek" [01]     1    48     0     1     1     1     1     0     0     0     0     0     2     0     1     0     0     0     0     56       48   85.71%

                "bellow" [02]     0     0     8     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0      8        8  100.00%

                 "croak" [03]     0     0     0     8     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0      8        8  100.00%

                   "cry" [04]     1     0     0     0    32     0     0     0     0     0     0     1     0     0     0     2     0     0     0     36       32   88.89%

       "descending_moan" [05]     5     1     5     0     4    23     0     0     0     0     0     2     1     0     2     3     3     0     7     56       23   41.07%

     "descending_shriek" [06]     0     0     0     0     0     0    18     0     0     0     0     0     0     0     0     0     0     0     0     18       18  100.00%

                 "groan" [07]     0     0     1     1     1     0     0    18     2     0     0     0     0     1     0     0     1     0     1     26       18   69.23%

          "groan_+_purr" [08]     0     0     0     0     0     0     0     0     8     0     0     0     0     0     0     0     0     0     0      8        8  100.00%

                 "growl" [09]     0     0     0     0     0     0     0     0     0     8     0     0     0     0     0     0     0     0     0      8        8  100.00%

                 "grunt" [10]     0     0     2     1     0     0     0     1     0     0    48     1     0     1     0     0     1     0     0     55       48   87.27%

                "grunts" [11]     0     0     0     0     0     0     0     0     0     0     0    16     0     0     0     0     0     0     0     16       16  100.00%

                "gurgle" [12]     1     0     0     0     4     2     0     1     3     0     0     2    33     1     0     3     2     0     4     56       33   58.93%

            "long_grunt" [13]     0     0     0     0     0     0     0     0     0     0     0     0     0    11     0     0     0     0     0     11       11  100.00%

         "modulated_cry" [14]     0     0     0     0     0     0     0     0     0     0     0     0     0     0    20     0     0     0     0     20       20  100.00%

        "modulated_moan" [15]     1     0     1     0     8     0     0     0     0     0     2     2     0     0     0    41     0     0     1     56       41   73.21%

                  "purr" [16]     0     0     1     0     0     0     0     0     2     0     0     0     0     1     0     1    18     0     2     25       18   72.00%

                "scream" [17]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     8     0      8        8  100.00%

                 "trill" [18]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     1     1     1     0    26     29       26   89.66%

     ------------------------  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  -----  -------  -------
                                  9     2    10     3    22     3     1     2     7     0     2     8     4     4     4    13     9     0    16    528      409   77.46%


On test sequences:  (note `-sr` option)

    $ ecoz hmm.classify -hmm  data/hmms/N10__M256/*.hmm -seq data/sequences/TEST/M256/*/* -sr 999

    hmms      : 19: "trill", "scream", "purr", "modulated_moan", "modulated_cry", "long_grunt", "gurgle", "grunts", "grunt", "growl", "groan_+_purr", "groan", "descending_shriek", "descending_moan", "cry", "croak", "bellow", "ascending_shriek", "ascending_moan"
    sequences : 227

    11010001xxx079x220243010000000000000000000700000402224103222382x200011100001011040104000000000331x11001100000023214020000000000000100000000014510000084537138xx000113000000000000xxx1221320010x100102001560101000000004102010000051

hmms-test-misclass.txt has the model rankings for the misidentified sequences.

    Confusion matrix:
           actual \ predicted  [00]  [01]  [02]  [03]  [04]  [05]  [06]  [07]  [08]  [09]  [10]  [11]  [12]  [13]  [14]  [15]  [16]  [17]  [18]  tests  correct  percent
     ------------------------  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  -----  -------  -------
        "ascending_moan" [00]     5     3     0     0     3     0     0     0     0     0     0     1     0     0     0     0     0     0     0     12        5   41.67%

      "ascending_shriek" [01]     2    15     0     1     0     0     0     0     0     0     0     0     3     0     3     0     0     0     0     24       15   62.50%

                "bellow" [02]     0     0     3     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0      3        3  100.00%

                 "croak" [03]     0     0     0     3     0     0     0     0     0     0     0     0     0     0     0     0     0     0     1      4        3   75.00%

                   "cry" [04]     1     2     0     0     7     0     0     0     0     0     0     2     2     0     0     1     1     0     0     16        7   43.75%

       "descending_moan" [05]     4     0     1     0     2    10     0     0     0     0     0     0     0     0     1     3     1     0     2     24       10   41.67%

     "descending_shriek" [06]     0     1     0     0     0     0     7     0     0     0     0     0     0     0     0     0     0     0     0      8        7   87.50%

                 "groan" [07]     0     0     0     0     3     0     1     5     2     0     0     0     0     0     0     1     0     0     0     12        5   41.67%

          "groan_+_purr" [08]     0     0     0     0     0     0     0     1     2     0     0     0     0     0     0     0     0     0     0      3        2   66.67%

                 "growl" [09]     0     0     0     0     0     0     0     0     0     4     0     0     0     0     0     0     0     0     0      4        4  100.00%

                 "grunt" [10]     1     1     1     1     0     0     0     0     0     1    17     0     1     0     0     0     0     0     1     24       17   70.83%

                "grunts" [11]     0     0     0     0     0     0     0     0     0     0     1     6     0     0     0     0     0     0     0      7        6   85.71%

                "gurgle" [12]     1     4     0     0     3     1     0     0     0     0     0     1     8     1     1     1     2     0     1     24        8   33.33%

            "long_grunt" [13]     0     0     0     0     0     0     0     0     0     0     0     0     0     4     0     0     0     0     0      4        4  100.00%

         "modulated_cry" [14]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     8     0     0     0     0      8        8  100.00%

        "modulated_moan" [15]     2     0     0     0     6     0     0     3     0     0     1     1     0     0     1     8     1     0     1     24        8   33.33%

                  "purr" [16]     0     0     0     0     1     0     0     0     0     0     1     0     0     1     0     0     7     0     0     10        7   70.00%

                "scream" [17]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     3     0      3        3  100.00%

                 "trill" [18]     0     0     0     0     0     0     0     1     0     0     1     1     0     1     0     2     0     0     7     13        7   53.85%

     ------------------------  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  -----  -------  -------
                                 11    11     2     2    18     1     1     5     2     1     4     6     6     3     6     8     5     0     6    227      129   56.83%
