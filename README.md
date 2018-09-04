This scala project uses [Mill](http://www.lihaoyi.com/mill/) as build tool.


## base configuration

Captured in `config/src/ecoz/config/Config.scala`.

## extraction of selections

The selection extractions get created under `../data/signals/`.

    $ mill selXtor.run ../data/HBSe_20151207T070326.wav ../data/20151207T070326.txt --all

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

## linear predictor generation

    $ for class in bark groan grunts gurgle purr trill; do mill ecoz.run vpl -signals ../data/signals/$class/*wav; done

## codebook generation

    $ mill ecoz.run vq.learn -e 0.0005  ../data/predictors/*/*.prd

## quantization

    $ mill ecoz.run vq.quantize -cb ../data/codebooks/eps_0.0005__1024.cbook ../data/predictors/*/*.prd

## hmm training

    $ for class in trill purr gurgle grunts groan bark; do mill ecoz.run hmm.learn -N 10 -a 0.005  ../data/sequences/$class/*; done

## hmm based classification

    $ mill ecoz.run hmm.classify -hmm  ../data/hmms/N10__M1024/*.hmm -seq ../data/sequences/*/*
    hmms      : 6: "trill", "purr", "gurgle", "grunts", "groan", "bark"
    sequences : 48

    ................................................
    Confusion matrix:
     actual \ predicted    "bark"   "groan"  "grunts"  "gurgle"    "purr"   "trill"     tests   correct   percent
     ------------------  ========  ========  ========  ========  ========  ========  --------  --------  --------
                 "bark"         3         0         0         0         0         0         3         3   100.00%

                "groan"         0        10         0         0         0         0        10        10   100.00%

               "grunts"         0         0         3         0         0         0         3         3   100.00%

               "gurgle"         0         0         0        24         0         0        24        24   100.00%

                 "purr"         0         0         0         0         6         0         6         6   100.00%

                "trill"         0         0         0         0         0         2         2         2   100.00%

     ------------------  ========  ========  ========  ========  ========  ========  --------  --------  --------
                                0         0         0         0         0         0        48        48   100.00%
