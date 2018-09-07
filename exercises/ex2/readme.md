A classification exercise involving 10 song files.

Summary:

- Signal analysis and quantization:
    - 853 sound unit files extracted and analysed
    - corresponding to 24 different classes
    - 7/3 used as approx train/test split ratio (-split 0.7),
      resulting in:
        - 596 units for training
        - 257 units for testing
    - however, all of the 853 units used for:
        - codebook generation (vq.learn)
        - quantization (vq.quantize)

- HMM:
    - N = 10
    - M = 256
    - Took a couple of hours on my laptop to train the 24 models
      (3-4 concurrently trained at a time)
    - Did manual termination of the refinement process to modulated_moan.hmm
      (all others automatically completed per -a parameter)

    - Classification performance:
        - On training units: 78.02%
          This suggests insufficient training and/or appropriate parameters
        - On test units: 53.7%
          Ranking of the models for the incorrectly classified sequences
          show close approximation in several cases

Possible future variations:
- use M = 512 ...
- play with different N values (both less than and greate than 10)
  NOTE also that no left-to-right models ha been trying yet (which may
  help with better recognition performance)
- set a maximum T of symbols to be considered from each sequence as
  a rough mechanism to deal with possibly too redundant signals (that is,
  long signals that just have a continuation of the same type of unit)


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

Apply LPC on all classes having at least 10 signals, processing at
most 100 (randomly chosen) pre class, and splitting the generated
predictors into training and test subsets (approx 7/3 ratio):

    $ ecoz lpc -minpc 10 -take 100 -split 0.7 -classes data/signals/*

**NOTE** look out for occasional 'non positive prediction error' warnings.
The corresponding predictor files are not generated.

Training predictors get saved under
`data/predictors/TRAIN/P36/<className>/`
and testing predictors under
`data/predictors/TEST/P36/<className>/`.

`ls -l data/predictors/*/*/*/* | wc -l` -> 853

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

The sequences get generated under `data/sequences/TRAIN/`
and `data/sequences/TEST/`, respectively.

`ls -l data/sequences/*/*/*/* | wc -l` -> 853

## HMM training

With training sequences, corresponding to 24 classes:

    $ ls data/sequences/TRAIN/M256
    BB                      cry                     grunt                   modulated_cry           tone_w_slight_downsweep
    ascending_moan          descending_moan         grunts                  modulated_moan          trill
    ascending_shriek        descending_shriek       gurgle                  purr                    upsweep
    bellow                  groan                   long_grunt              scream
    croak                   growl                   moan                    tone


for each of those:

    $ ecoz hmm.learn -N 10 -a 0.005  data/sequences/TRAIN/M256/$CLASS/*

hmms.md captures the output for some of the trainings.

Note that except for modulated_moan.hmm (which I terminated after its
refinement step 122), all trainings completed automatically per the
given -a value.


## HMM based sequence classification

On training sequences:

    $ ecoz hmm.classify -hmm  data/hmms/N10__M256/*.hmm -seq data/sequences/TRAIN/M256/*/*
    hmms      : 23: "upsweep", "trill", "tone_w_slight_downsweep", "tone", "scream", "purr", "modulated_moan", "modulated_cry", "moan", "long_grunt", "gurgle", "grunts", "grunt", "growl", "groan", "descending_shriek", "descending_moan", "cry", "croak", "bellow", "ascending_shriek", "ascending_moan", "BB"
    sequences : 596

    0000000000000000000101068010000010000000271316000004200000404000210000001000000000000000000000000000000000000000000000000000000000000000000000200000000001242212331233111140000105000010000100000000011121021000100000113112102100000000000000000000000011001240000000000000100137420145062400000000000000000000000000000001000010020000010010001100000000000000000040320000020000000001011010200200000020020100000100000221001000000022000000000000000000000000000000226545656564001000000000000000000000010000010048x002101040000000100000000000000000000000000000000000000000011110010000000000000000000000000000
    Confusion matrix:
                 actual \ predicted  [00]  [01]  [02]  [03]  [04]  [05]  [06]  [07]  [08]  [09]  [10]  [11]  [12]  [13]  [14]  [15]  [16]  [17]  [18]  [19]  [20]  [21]  [22]  tests  correct  percent
     ------------------------------  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  -----  -------  -------
                          "BB" [00]    15     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     15       15  100.00%

              "ascending_moan" [01]     0    19     1     0     0     2     1     0     0     0     0     0     0     0     0     0     1     0     0     0     0     1     0     25       19   76.00%

            "ascending_shriek" [02]     0     1    50     0     0     1     1     0     1     0     0     0     5     1     0     1     0     0     0     0     0     2     0     63       50   79.37%

                      "bellow" [03]     0     0     0     8     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0      8        8  100.00%

                       "croak" [04]     0     0     0     0     8     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0      8        8  100.00%

                         "cry" [05]     0     1     0     0     0    33     0     0     0     1     0     1     0     0     0     0     0     0     0     0     0     0     0     36       33   91.67%

             "descending_moan" [06]     0     2     1     2     2     7    33     0     0     4     0     2     0     1     0     0    10     1     0     0     0     5     0     70       33   47.14%

           "descending_shriek" [07]     0     0     0     0     0     0     0    14     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     14       14  100.00%

                       "groan" [08]     0     0     0     0     1     2     0     0    15     0     0     0     0     1     0     0     0     0     0     0     0     1     0     20       15   75.00%

                       "growl" [09]     0     0     0     0     0     0     0     0     0     8     0     0     0     0     0     0     0     0     0     0     0     0     0      8        8  100.00%

                       "grunt" [10]     0     0     0     1     3     0     1     0     0     2    52     3     1     2     1     0     0     1     0     1     0     1     0     69       52   75.36%

                      "grunts" [11]     0     0     0     0     0     0     0     0     0     0     0    16     0     0     1     0     0     0     0     0     1     0     0     18       16   88.89%

                      "gurgle" [12]     0     1     0     2     0     5     0     0     0     0     1     0    50     2     0     1     2     4     0     0     0     2     0     70       50   71.43%

                  "long_grunt" [13]     0     0     0     0     0     0     0     0     0     0     0     0     0    11     0     0     0     0     0     0     0     0     0     11       11  100.00%

                        "moan" [14]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     8     0     0     0     0     0     0     0     0      8        8  100.00%

               "modulated_cry" [15]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0    11     0     0     0     0     0     0     0     11       11  100.00%

              "modulated_moan" [16]     0     1     0     1     0     7     0     0     4     2     2     2     0     0     0     0    35     1     0     0     0     2     0     57       35   61.40%

                        "purr" [17]     0     0     0     1     0     0     0     0     0     0     0     0     0     0     0     0     0    19     0     0     0     0     0     20       19   95.00%

                      "scream" [18]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     8     0     0     0     0      8        8  100.00%

                        "tone" [19]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0    14     0     0     0     14       14  100.00%

     "tone_w_slight_downsweep" [20]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     7     0     0      7        7  100.00%

                       "trill" [21]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     5     0     0     0     0    23     0     28       23   82.14%

                     "upsweep" [22]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     8      8        8  100.00%

     ------------------------------  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  -----  -------  -------
                                        0     6     2     7     6    24     3     0     5     9     3     8     6     7     2     2    18     7     0     1     1    14     0    596      465   78.02%


On test sequences:  (note `-sr` option)

    $ ecoz hmm.classify -hmm  data/hmms/N10__M256/*.hmm -seq data/sequences/TEST/M256/*/* -sr 999
    hmms      : 23: "upsweep", "trill", "tone_w_slight_downsweep", "tone", "scream", "purr", "modulated_moan", "modulated_cry", "moan", "long_grunt", "gurgle", "grunts", "grunt", "growl", "groan", "descending_shriek", "descending_moan", "cry", "croak", "bellow", "ascending_shriek", "ascending_moan", "BB"
    sequences : 257

    100000011x91116170812150201x70000000000000101000000x00010080541021203432000001000001003221120100204110000010401x2000238102000000000001000002020001033000000022120032470340123013383110113010000000000255500100001810100001312631101063029001100000100100000216108

      "ascending_moan"         sequence (T=175):
        [00] "modulated_moan"         p = 2.512946578818801366277007954165157897641650085E-153
        [01] "gurgle"                 p = 1.6312168008785226038325421712732213227935800753974369E-253
        [02] "purr"                   p = 2.878553070663749387790120959989626283110688696715659550794648784701493897203917730E-274
        [03] "descending_moan"        p = 1.52974980004628176403514767321118120613797263E-328
        [04] "groan"                  p = 1.6565439211573114360920649691754771577575230507164054E-363
        [05] "cry"                    p = 5.19241277529341586755684326818003931638058524841E-408
        [06] "descending_shriek"      p = 3.17488641092964950577000388354624888004053462E-486
      * [07] "ascending_moan"         p = 2.326573499283471952322199026918697557818737106E-526
        [08] "grunt"                  p = 3.1751941444014603762414924117929634019185E-530
        [09] "trill"                  p = 1.522103350915421845917348638050539919749286E-546
        [10] "grunts"                 p = 2.466136700426827618991127554372268005880726E-565
        [11] "ascending_shriek"       p = 1.28011812794698207294454336112957666511066E-594
        [12] "growl"                  p = 4.770207047906723316962526727085718384251788970E-620
        [13] "tone_w_slight_downsweep" p = 7.4750088720562192291114769379743818E-631
        [14] "modulated_cry"          p = 4.31094721974123905893278899554002282652754391E-633
        [15] "long_grunt"             p = 3.03553240262254905777590696820276339034054E-676
        [16] "tone"                   p = 1.22306462553873754533744325761574784E-684
        [17] "upsweep"                p = 4.1725023850674693142952617188950212E-690
        [18] "bellow"                 p = 1.309023321337654805777417222777315875370000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000312708906307486137183472594182557176E-711
        [19] "scream"                 p = 1.122839953841969669782604975118721136898075E-741
        [20] "BB"                     p = 4.74083404023106027703000013293934935E-832
        [21] "croak"                  p = 6.9450087743804885019464641374402661E-873
        [22] "moan"                   p = 1.08131658538490555478852209004441445E-873


      "ascending_moan"         sequence (T=76):
        [00] "ascending_shriek"       p = 3.815618280187065781568515400134767562149159E-122
      * [01] "ascending_moan"         p = 1.09182019772355838155111737100470515486648939E-132
        [02] "gurgle"                 p = 3.98256329351544480565833656563858649201189494207181E-141
        [03] "purr"                   p = 9.55514463889491121412563131361695415984892347236615293588037251864708175408628E-157
        [04] "cry"                    p = 8.6568890980584174479295629001872142988297051140E-161
        [05] "descending_moan"        p = 2.11020559923231020957287708736110761988089167E-168
        [06] "grunt"                  p = 2.791491553023073703636429264456707604171868511520E-170
        [07] "trill"                  p = 3.11385307767419113737658727546539523005164877E-181
        [08] "modulated_moan"         p = 6.422278543037754442312021424454896429343702E-187
        [09] "groan"                  p = 2.046662883767386706564026614317533754380575334010E-276
        [10] "descending_shriek"      p = 9.892407332214821079908369822330952888396950E-280
        [11] "growl"                  p = 7.3378534807551674876486363927650576525815702328E-282
        [12] "upsweep"                p = 1.9911219950390141345093334881074948E-283
        [13] "grunts"                 p = 2.81763728384408942421812824732300039878710270E-288
        [14] "modulated_cry"          p = 1.9783392189260380755926272233774592295564581E-302
        [15] "bellow"                 p = 5.625552067014399105260415373999954168294677E-348
        [16] "long_grunt"             p = 4.20512974645735580160798657545882721410E-352
        [17] "scream"                 p = 6.5215932913485033007217426110521320552E-356
        [18] "BB"                     p = 2.705464204838232283130407554112572108E-366
        [19] "tone_w_slight_downsweep" p = 7.0523620883405518276294188249795326E-373
        [20] "croak"                  p = 7.7413156146333106109349442426781627E-379
        [21] "tone"                   p = 1.00000000000000000000000000000000119E-380
        [22] "moan"                   p = 9.9999999999999999999999999999999897E-381


      "ascending_moan"         sequence (T=122):
        [00] "modulated_moan"         p = 2.2704605924354920505786319947505412847299556887E-128
        [01] "gurgle"                 p = 9.443760379492119636531987058329805828290823778201656E-133
        [02] "groan"                  p = 9.221805064372061227795951450122375948264702914985464E-138
        [03] "purr"                   p = 3.09372402001326490111531172321170362790596599749757787803377472794930188580656130E-150
        [04] "grunt"                  p = 2.017448902420894729190575586703693458918E-185
        [05] "descending_moan"        p = 1.5589926800519644697457759929925169062918E-196
      * [06] "ascending_moan"         p = 4.5552713651546018472928576378237927494010882746E-211
        [07] "descending_shriek"      p = 6.27074527051256527734266075154408912040532520E-227
        [08] "ascending_shriek"       p = 3.22412966682116615549440194714187707976676E-235
        [09] "grunts"                 p = 4.141354113633736972389528415451468960569622E-258
        [10] "cry"                    p = 1.966630893767379853614346184153968341848554020E-308
        [11] "trill"                  p = 4.374490198265762227296061962731960503771251E-339
        [12] "growl"                  p = 1.437309102359044212259220405754681147955474E-363
        [13] "modulated_cry"          p = 2.27032069021456151731765129766338823526352302272196E-377
        [14] "long_grunt"             p = 1.131090016270498716223293239183096696512527E-417
        [15] "bellow"                 p = 4.548280640883346871984603020761003080650000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000036877986060881829378233844506804975711E-456
        [16] "scream"                 p = 1.439098129421202422665583522852560374315880643E-477
        [17] "BB"                     p = 1.275545197403760496288170095584668989E-488
        [18] "tone"                   p = 3.647062348076868538022849936640405393E-558
        [19] "tone_w_slight_downsweep" p = 5.70473510915351558149349732707401415E-573
        [20] "moan"                   p = 2.87558287407611805938675025474020405E-581
        [21] "upsweep"                p = 6.08426739907997327122360914293409702E-586
        [22] "croak"                  p = 7.7860740762597945355438689743550477E-609


      "ascending_moan"         sequence (T=166):
        [00] "descending_moan"        p = 4.2308344734424347550775679168168591838679553357E-145
      * [01] "ascending_moan"         p = 4.4651360419622159957995248497849252025049329148056821E-174
        [02] "modulated_moan"         p = 6.84956584394434743346715832141503733917816571E-191
        [03] "gurgle"                 p = 1.694557478611117781347027068075137091512676074023E-261
        [04] "cry"                    p = 4.229999457803623989331930059916565436741900914170E-303
        [05] "purr"                   p = 9.147588388385088361025656984043877827704502012934646762342806688765678510612244764E-324
        [06] "trill"                  p = 9.3340123210338806113575427911437649934829257E-462
        [07] "grunt"                  p = 1.079790401306125662444904988272513635094402820699180E-466
        [08] "grunts"                 p = 2.32534423935753241655115135026076622726124173E-568
        [09] "growl"                  p = 4.7438095107324528464595231825788365454408411841368E-577
        [10] "ascending_shriek"       p = 2.90227627556512924607339507645434969594794199E-585
        [11] "groan"                  p = 1.23225840731985238412105620512097811760821812541E-624
        [12] "descending_shriek"      p = 8.58601494189325788436147636846887984E-695
        [13] "tone_w_slight_downsweep" p = 4.0808004497290119826042760424924035E-718
        [14] "BB"                     p = 3.212538330332177392322193182782985376E-760
        [15] "long_grunt"             p = 6.3826069577172453320931385141754717E-776
        [16] "modulated_cry"          p = 6.902141243330680696886320319941261436E-783
        [17] "bellow"                 p = 1.166107340394644649817599237225464827E-798
        [18] "scream"                 p = 2.42801176773629578538072601772271383E-817
        [19] "upsweep"                p = 4.9332387346761741310535494756724603E-822
        [20] "moan"                   p = 2.36480458207975824091384683448997426E-825
        [21] "tone"                   p = 1.110829739432481858070625957878754499E-826
        [22] "croak"                  p = 9.9999999999999999999999999999999732E-831


      "ascending_moan"         sequence (T=214):
        [00] "descending_moan"        p = 1.38333807791738100713105638230444835065389113E-186
      * [01] "ascending_moan"         p = 2.933708048810345071704766984423752649922996490867E-196
        [02] "gurgle"                 p = 3.941361398250333002892338041008245901193050311136321E-302
        [03] "modulated_moan"         p = 1.8487284344648775384514074640347847033004115E-346
        [04] "grunt"                  p = 1.48879254873496972816198714032280001736285525881E-377
        [05] "ascending_shriek"       p = 1.282473414559573476267583076073158580716930E-438
        [06] "purr"                   p = 6.5057706418886618117900978181737359214341862086748476323924153739630952103552E-467
        [07] "cry"                    p = 4.07709228073420811233491291510314430437164865E-468
        [08] "growl"                  p = 2.571212569896574703582156984274297683541189023126E-513
        [09] "grunts"                 p = 4.890475090318299687332045589815360462197E-581
        [10] "trill"                  p = 4.616838800406408983342986352572028528231098E-614
        [11] "groan"                  p = 7.674709646653451027757431822518125195712913598E-636
        [12] "descending_shriek"      p = 1.869091969060617630063513475531806409943E-753
        [13] "long_grunt"             p = 9.44980853559695791355376923549397707668E-846
        [14] "modulated_cry"          p = 6.10752372809597207956287734467167648942404E-921
        [15] "BB"                     p = 2.95840278531802160640571083830602458987E-933
        [16] "scream"                 p = 1.258217709226206003359940858246695608433E-969
        [17] "bellow"                 p = 4.0730088061304161013420961335411181122E-993
        [18] "tone_w_slight_downsweep" p = 1.53145310857829588370595926364252505E-1035
        [19] "upsweep"                p = 1.12394590970881660279748231104727103E-1058
        [20] "tone"                   p = 3.95682280960792338518817996536844939E-1059
        [21] "moan"                   p = 1.25965471712156862461889750733257717E-1068
        [22] "croak"                  p = 9.9999999999999999999999999999999687E-1071


      "ascending_moan"         sequence (T=190):
        [00] "descending_moan"        p = 3.8394865362669058944915714419472139605268930391E-180
      * [01] "ascending_moan"         p = 9.748622431199327783166381186130150180701372122029023E-215
        [02] "gurgle"                 p = 1.342954937938161489207001596915270595860458854335E-278
        [03] "modulated_moan"         p = 2.9620955460293181248974947007385243985160419E-291
        [04] "grunt"                  p = 5.25739125229203710240586677140423070869759601152713E-347
        [05] "cry"                    p = 8.02420674896293557373218954230194327399321017023E-373
        [06] "purr"                   p = 2.560580367977588736315521445467345198578613026660891146756165770505536448729523E-424
        [07] "ascending_shriek"       p = 3.7014398301743796223880123522478244209848223E-444
        [08] "growl"                  p = 1.49068936721278540734845196420145247649106441893633E-497
        [09] "grunts"                 p = 6.44999209372914102571988790612610554911911E-508
        [10] "trill"                  p = 9.001173191489205208666307512719813627803E-560
        [11] "groan"                  p = 5.3102558731810335585787678473844836275006527E-575
        [12] "descending_shriek"      p = 2.8592959206115693588593585759362294191E-631
        [13] "modulated_cry"          p = 8.171690841979212022702917967287355177371E-764
        [14] "long_grunt"             p = 5.41287384412278380249515752729675755635E-796
        [15] "BB"                     p = 3.9006934204782832028482760724173138403E-824
        [16] "bellow"                 p = 1.3114405246551542809115068850148652248579996932E-831
        [17] "tone_w_slight_downsweep" p = 4.2863753482560215102647364996708588E-842
        [18] "scream"                 p = 3.9235391621499254424763821923698819761E-899
        [19] "tone"                   p = 1.68304874576185436467410844834572256E-943
        [20] "upsweep"                p = 1.9166339609233993846339776747616562E-944
        [21] "moan"                   p = 9.9999999999999999999999999999999784E-951
        [22] "croak"                  p = 9.9999999999999999999999999999999703E-951


      "ascending_moan"         sequence (T=81):
        [00] "gurgle"                 p = 4.247399319062555825662463295179031922651280083E-88
        [01] "ascending_shriek"       p = 6.24697791378475069741391994165794282664961149E-107
        [02] "modulated_moan"         p = 1.22424599974157554934573782358838097381778415269697E-107
        [03] "cry"                    p = 7.198884129114284486026795373883412906671230766106E-113
        [04] "descending_moan"        p = 2.644761634315882767452545759625599606405991229E-119
        [05] "modulated_cry"          p = 3.0401371808716274414113089641822319205745668912E-122
        [06] "grunts"                 p = 3.108602317439999742155778061174088300253628343258231834143E-128
        [07] "trill"                  p = 7.2581608655352386467037267444028731646314780139046E-131
        [08] "growl"                  p = 2.745891079943533130197960471894907891637197262545256802E-132
      * [09] "ascending_moan"         p = 4.03634465930842828015534283170033058640073615793E-136
        [10] "grunt"                  p = 2.8040465141794238490241836279964542684044434587578E-138
        [11] "bellow"                 p = 5.29795406556883420818703262003620741104570000000000000000000000000000000000000000000000000000000000000000000227615402911984255140626153764727914E-198
        [12] "descending_shriek"      p = 5.88112885897516375170614932245641930689444E-264
        [13] "croak"                  p = 3.199270518326150559818101803180223755338E-281
        [14] "purr"                   p = 4.44036973341188778026751327535065846531064590852102834336396E-321
        [15] "groan"                  p = 3.7637245504633683785716399626169695253046E-333
        [16] "long_grunt"             p = 1.27170546902884339989405778605679854E-342
        [17] "scream"                 p = 1.00000000000000000000000000000000700E-405
        [18] "upsweep"                p = 1.00000000000000000000000000000000291E-405
        [19] "tone"                   p = 1.00000000000000000000000000000000157E-405
        [20] "BB"                     p = 9.9999999999999999999999999999999919E-406
        [21] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999897E-406
        [22] "moan"                   p = 9.9999999999999999999999999999999872E-406


      "ascending_moan"         sequence (T=90):
        [00] "modulated_moan"         p = 1.025036434122036586973616383302846858431328753208E-87
        [01] "trill"                  p = 8.9404149154804561898778786626345434315574475206232E-93
        [02] "grunts"                 p = 7.177304839178157681373801953564344476001230756447915752E-101
        [03] "descending_moan"        p = 4.9471618873402816210926694096303049030066600E-108
        [04] "growl"                  p = 1.47720065395455464046560240871769932052897223850874E-111
        [05] "grunt"                  p = 1.583226474928243474032718644629626348405212440909E-115
        [06] "gurgle"                 p = 2.1245589635988777211077947585353395269495662E-122
        [07] "modulated_cry"          p = 2.45688094282694344969071743909297538359100922E-133
        [08] "ascending_shriek"       p = 1.20398851486271446436398538413821208853401329E-135
        [09] "bellow"                 p = 5.4372338957746300887757543840869739124900000000000000000000000000000000000000000000000000000000000000000000002676691039632622016054492920495430874889617E-137
        [10] "cry"                    p = 2.5811761620044716311822552272368289275764190862907E-151
      * [11] "ascending_moan"         p = 1.272622315115136327318496564614114771329826955E-152
        [12] "descending_shriek"      p = 3.2461759106871904713948065773369564950643E-260
        [13] "purr"                   p = 2.7174098342327188660331664125275941534321803875139038879713081876143843E-294
        [14] "croak"                  p = 6.009815860837218471142657110381031862251364E-328
        [15] "long_grunt"             p = 8.13690605585852566909411694356501009754E-337
        [16] "groan"                  p = 3.4261146572182072204747943444052238488105E-342
        [17] "BB"                     p = 4.1763345311307968614997854761317088E-448
        [18] "scream"                 p = 7.6103636512873369902946550567708179E-449
        [19] "upsweep"                p = 1.00000000000000000000000000000000319E-450
        [20] "tone"                   p = 1.00000000000000000000000000000000189E-450
        [21] "moan"                   p = 9.9999999999999999999999999999999906E-451
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999881E-451


      "ascending_moan"         sequence (T=87):
        [00] "cry"                    p = 5.857510884988438903955097605899734480256965208E-83
      * [01] "ascending_moan"         p = 4.63453143180927875950991238185584251999528719E-95
        [02] "gurgle"                 p = 3.6739659650387841487715361149527261850687335967E-97
        [03] "descending_moan"        p = 1.9264452728535193826680234257938863248667913252E-100
        [04] "groan"                  p = 7.2756970693964814529777840301913620962293903E-108
        [05] "modulated_moan"         p = 3.56979492504285516396855927221649655849642748085601120066E-123
        [06] "purr"                   p = 1.8247362308520326816869287782453346791952414625729582273044387862973189105658449462784E-178
        [07] "ascending_shriek"       p = 2.745500035023079083678237528828854141013507E-222
        [08] "trill"                  p = 2.4468313987379706023354512894745721786964E-252
        [09] "grunt"                  p = 7.3019274666066893618067602829366577609475E-258
        [10] "long_grunt"             p = 2.55128847273618608168518963738282536386E-267
        [11] "bellow"                 p = 1.4278815408784924223137379451436795601248000000000000000000000000000000000000000000000000071303095945362141077577319374674038366085942E-308
        [12] "grunts"                 p = 1.14326313590552473725502415716997744919285967E-354
        [13] "tone"                   p = 1.5523071454546969722114644046402964383E-366
        [14] "scream"                 p = 6.07495491090296263327443515482743123851E-379
        [15] "descending_shriek"      p = 1.55341591446243909541290091654769305E-393
        [16] "croak"                  p = 2.3354139188390294019439766051618250099260948E-406
        [17] "growl"                  p = 1.5020463945130843401413353653734198266E-416
        [18] "tone_w_slight_downsweep" p = 1.44884632631985159219595678047329873E-427
        [19] "BB"                     p = 1.06880760162369373873320876290225779E-427
        [20] "upsweep"                p = 2.7999596341122567011219394897730347E-430
        [21] "moan"                   p = 1.05925916535116517481969077347825073E-430
        [22] "modulated_cry"          p = 7.425357898034981042774991474310640376E-434


      "ascending_moan"         sequence (T=203):
        [00] "cry"                    p = 3.3834509826551587042942022331890174527300477979358E-174
      * [01] "ascending_moan"         p = 8.1897479980956717677456188038328703440393675849E-186
        [02] "descending_moan"        p = 4.11368122218135334763488950651987702160402905840E-187
        [03] "groan"                  p = 2.45216000188053938221636926185532284252492857942746E-194
        [04] "modulated_moan"         p = 2.700498007415318346508770280737792545616713467815280520499E-219
        [05] "gurgle"                 p = 1.74025936045232966653179234861808414508366712488561E-221
        [06] "purr"                   p = 1.29516617351182186309223050424744614963774064186594479928821725679720570752731176199291474E-228
        [07] "ascending_shriek"       p = 9.35219194380613663688162825395563929565043E-454
        [08] "grunt"                  p = 6.849773261609034114085970365584748606E-590
        [09] "bellow"                 p = 9.0173819397713308196797669205152217000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000002770613608349765489892282220191214167E-645
        [10] "trill"                  p = 2.5203000774538148955238608319246146805E-701
        [11] "grunts"                 p = 3.0675293446321195057722728001649237595070E-749
        [12] "long_grunt"             p = 1.47698144385726096638848074236747899E-771
        [13] "tone"                   p = 8.345970868090257211509976858159609040503132E-775
        [14] "scream"                 p = 1.9932358243808294694161300092343344523876E-817
        [15] "croak"                  p = 1.623596346507762909415942557584263820E-894
        [16] "descending_shriek"      p = 1.118247348751225441013532772011880777E-903
        [17] "growl"                  p = 2.36980727690244010966555384271684377E-974
        [18] "BB"                     p = 9.38255110951947621282897756576196607E-999
        [19] "modulated_cry"          p = 1.06967831127100636506432486627106764E-1006
        [20] "upsweep"                p = 1.55326650419953887607330081322953760E-1007
        [21] "tone_w_slight_downsweep" p = 1.31242198040694664877418319347274781E-1007
        [22] "moan"                   p = 7.3410406696737971950065765029938196E-1009


      "descending_shriek"      sequence (T=66):
        [00] "ascending_shriek"       p = 6.8178245989344762725699694545513018636574481E-93
      * [01] "descending_shriek"      p = 2.0873148092227211403671860439235411987140466E-98
        [02] "gurgle"                 p = 3.5644483388505426388744189855169559169643479067204E-134
        [03] "croak"                  p = 6.6316891540145679505294338781001442443344135E-140
        [04] "long_grunt"             p = 4.585621488186612264875251560949732494853465113E-155
        [05] "grunt"                  p = 7.345341810139776640752070488411270529943727E-170
        [06] "trill"                  p = 1.80999463109125937959314008420608166666892214E-191
        [07] "groan"                  p = 1.10588544315692795420356874870656954008720851425431E-195
        [08] "bellow"                 p = 6.4940948241020835749673880976715613237088800000000000000000000000000000000000000000000000000000000000000000000031287495123160462148127978141903296E-215
        [09] "descending_moan"        p = 3.306250381958268110507760172034911257889524653E-216
        [10] "purr"                   p = 1.9030357850872829279747519958602521751626409012813374317925541222636568343E-219
        [11] "ascending_moan"         p = 8.26141049022226014577723314882393939E-245
        [12] "cry"                    p = 1.8184866662640075956605678051155750092405E-251
        [13] "modulated_moan"         p = 1.2882867599013004580284005394715262836788E-287
        [14] "BB"                     p = 5.9375995041048539038215688715993422E-304
        [15] "modulated_cry"          p = 1.31680696811293038308378699811661789E-308
        [16] "grunts"                 p = 4.64577424807615539656697697970526155E-326
        [17] "scream"                 p = 1.92624548960347252607971165229329929E-328
        [18] "growl"                  p = 1.84499140437082234110478895826663633E-328
        [19] "upsweep"                p = 1.00000000000000000000000000000000245E-330
        [20] "tone"                   p = 1.00000000000000000000000000000000094E-330
        [21] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999972E-331
        [22] "moan"                   p = 9.9999999999999999999999999999999914E-331


      "descending_shriek"      sequence (T=52):
        [00] "ascending_shriek"       p = 8.67436606200749722003297958060881264097812540E-68
      * [01] "descending_shriek"      p = 4.1726460291920237707671439365349591617027768E-68
        [02] "gurgle"                 p = 2.5432865121973698791567199432446677839583387372725E-96
        [03] "croak"                  p = 2.0051217035654643148457199110395586483307114E-97
        [04] "grunt"                  p = 5.374346608174609857182068368850560467698735598779E-122
        [05] "trill"                  p = 9.64196890800812972755656920421129469129754234E-135
        [06] "descending_moan"        p = 1.1794693866889143573620059642858796386044039550E-135
        [07] "long_grunt"             p = 3.445750836464980432756215197600864417352E-137
        [08] "groan"                  p = 8.31316082857301642094947568515976959969951939248953E-138
        [09] "purr"                   p = 7.732620450706431320936216237395580732176062081333998448560740697952658555037E-139
        [10] "bellow"                 p = 9.11843258610804137585975872880304011777900000000000000000000000000000000000000000000000000000000000000000000000000000000049999999999999999999999999999999830E-140
        [11] "ascending_moan"         p = 4.2774072636141999603311140861327760846965909E-175
        [12] "cry"                    p = 1.7092221162862616594139100674103470594E-184
        [13] "modulated_moan"         p = 1.0349641078210749337754134294050104288325E-244
        [14] "grunts"                 p = 5.517958436641729611824099922594272449E-253
        [15] "scream"                 p = 4.809159001723301496353291722455491677E-256
        [16] "upsweep"                p = 1.00000000000000000000000000000000272E-260
        [17] "growl"                  p = 1.00000000000000000000000000000000143E-260
        [18] "tone"                   p = 1.00000000000000000000000000000000053E-260
        [19] "tone_w_slight_downsweep" p = 1.00000000000000000000000000000000027E-260
        [20] "BB"                     p = 9.9999999999999999999999999999999994E-261
        [21] "moan"                   p = 9.9999999999999999999999999999999933E-261
        [22] "modulated_cry"          p = 9.9999999999999999999999999999999627E-261


      "descending_shriek"      sequence (T=61):
        [00] "ascending_shriek"       p = 2.8408584623336933376072045533973594287616E-98
        [01] "gurgle"                 p = 6.667012568649505262397928241849977294203401E-127
        [02] "grunt"                  p = 1.282320143624680340731293016816286243382521E-131
        [03] "croak"                  p = 2.1910413475728241383181886256032754196048E-134
      * [04] "descending_shriek"      p = 4.13353947866684950792474854371624130205066E-163
        [05] "long_grunt"             p = 2.0475020967790361909815453524497868677732E-169
        [06] "bellow"                 p = 9.018306310205856024563419853136963504610000000000000000000000000000000000000000000000000000000000000000000000000000015181690789813151293053368900422979E-180
        [07] "trill"                  p = 2.1665514174526791098550174805774978031352572E-180
        [08] "descending_moan"        p = 9.61721612411200698393545711071552760696602E-189
        [09] "groan"                  p = 3.60668240243394346464433767546896633615005429304441E-195
        [10] "purr"                   p = 1.0846237987015825721639117968685567781685934061719641502827888416913027E-203
        [11] "ascending_moan"         p = 3.33249375662144124894542827036293373223E-220
        [12] "cry"                    p = 5.8503241726533928420935142701276200527582E-231
        [13] "modulated_moan"         p = 1.32184916076798076545537072489999031378324096967E-283
        [14] "grunts"                 p = 1.446648173060965343716775525118444162E-295
        [15] "modulated_cry"          p = 1.30814661887972835005897399492581909E-297
        [16] "growl"                  p = 1.080558817291047841304949584318078430E-300
        [17] "BB"                     p = 9.8849129707263683983358298865049650E-303
        [18] "scream"                 p = 6.38932165666041993035134865279855074E-303
        [19] "upsweep"                p = 1.00000000000000000000000000000000259E-305
        [20] "tone"                   p = 1.00000000000000000000000000000000083E-305
        [21] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999984E-306
        [22] "moan"                   p = 9.9999999999999999999999999999999929E-306


      "cry"                    sequence (T=112):
        [00] "ascending_moan"         p = 1.72262548703417842071334844271677401476967829E-116
        [01] "gurgle"                 p = 1.615616962990807754347818812286774779870076071E-137
      * [02] "cry"                    p = 4.608801550693293328980073838718928814403772907644372E-143
        [03] "modulated_cry"          p = 2.0722139061005484655275999526367998666577157E-167
        [04] "ascending_shriek"       p = 1.92966830678660927887655675377967720130634898E-188
        [05] "grunts"                 p = 1.30161364852174833499779791660214501001215952463353629392478E-199
        [06] "growl"                  p = 4.4153136283814755832892388127549786022725016570968E-200
        [07] "descending_moan"        p = 1.633267968020053239978992204697116525923817939E-201
        [08] "modulated_moan"         p = 1.877496114395870167872175166793089906790990838029E-221
        [09] "grunt"                  p = 7.66818609324586987653663781290789747781551050285461E-265
        [10] "trill"                  p = 4.182999818813197251340432419273785639880071972683978E-294
        [11] "bellow"                 p = 1.91463093390728596877553829303629200866000000000000000000000000000000000000786616022354948567957804191572055988E-450
        [12] "descending_shriek"      p = 7.78258448184302307970211646585697065886E-463
        [13] "croak"                  p = 2.042552043124100974459258508832777561250781E-494
        [14] "purr"                   p = 2.8461850801809442309017465335900241512797044429E-516
        [15] "groan"                  p = 2.0016883206129110280349393760292814949E-522
        [16] "long_grunt"             p = 1.56286708171451262201121984670953657E-544
        [17] "upsweep"                p = 8.2246755684498988984195206401603258E-556
        [18] "scream"                 p = 1.00000000000000000000000000000000906E-560
        [19] "tone"                   p = 1.00000000000000000000000000000000261E-560
        [20] "moan"                   p = 9.9999999999999999999999999999999939E-561
        [21] "BB"                     p = 9.9999999999999999999999999999999856E-561
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999761E-561


      "cry"                    sequence (T=99):
        [00] "gurgle"                 p = 3.32586286941735166649370418406251777122545170E-103
      * [01] "cry"                    p = 1.957352840735512653357644425818113142484982357008147E-121
        [02] "growl"                  p = 1.306903100053179890204504096847706739454737218008678790262E-133
        [03] "ascending_moan"         p = 2.2709700433119279869791771857881395050850359804327E-134
        [04] "ascending_shriek"       p = 1.302251925009656955892831926723565342543884166E-135
        [05] "descending_moan"        p = 2.3415657321558246778785459396298480888315808665E-146
        [06] "modulated_moan"         p = 9.988980169730187771817611636800725215962414756992068E-200
        [07] "trill"                  p = 2.43889391238475080028426874985647574510403160453403E-210
        [08] "grunt"                  p = 9.925295514523041486452806360112221924412547589766036E-250
        [09] "grunts"                 p = 1.140034636838379916417158017322876210794342487004410209655620E-278
        [10] "modulated_cry"          p = 4.83238490909127199198855764170712073848274416397E-282
        [11] "croak"                  p = 2.86275031357144091768644552528575225610449105E-330
        [12] "bellow"                 p = 6.2494606695679795841462656461602876835819277200000000000000000000725070625526790325057846230460619811131727E-396
        [13] "descending_shriek"      p = 5.17438923814992162847334031186125107E-415
        [14] "purr"                   p = 1.9124708516858583300534504966797138869685952686E-443
        [15] "groan"                  p = 4.113470083037760675121323200879777958E-454
        [16] "long_grunt"             p = 3.32973886145809921116333626080634202E-488
        [17] "scream"                 p = 1.00000000000000000000000000000000837E-495
        [18] "upsweep"                p = 1.00000000000000000000000000000000330E-495
        [19] "tone"                   p = 1.00000000000000000000000000000000197E-495
        [20] "moan"                   p = 9.9999999999999999999999999999999941E-496
        [21] "BB"                     p = 9.9999999999999999999999999999999856E-496
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999831E-496


      "cry"                    sequence (T=141):
        [00] "descending_moan"        p = 1.184660578661398591782878616340802529313977748E-168
        [01] "modulated_moan"         p = 3.63728469331757204272906512381455351949959372501E-173
      * [02] "cry"                    p = 6.94263064573496483690275517952794402878420143615171E-178
        [03] "ascending_moan"         p = 4.300135071508916040621211638937510129209382503E-208
        [04] "gurgle"                 p = 1.417086473627603041603167874429399365376346889E-250
        [05] "trill"                  p = 6.597887788066831201935853254028329421183011010668760E-275
        [06] "grunts"                 p = 6.10868504725742964935044291197762735968782920250033337E-312
        [07] "growl"                  p = 5.4780172626592808214492890879501228994586609383188945985E-313
        [08] "grunt"                  p = 9.80237991165191077075132760575741521860640751028E-318
        [09] "purr"                   p = 6.8068891051280968956168720780994372409444813882307705956E-374
        [10] "ascending_shriek"       p = 2.93747805736820444892995222815468722409653986E-485
        [11] "modulated_cry"          p = 3.962153631310447161982392849557531824091433081932E-501
        [12] "groan"                  p = 4.805344229087478864975883818966836250637241E-562
        [13] "bellow"                 p = 9.58093114113637653382895747079531485813379874098741981979321625641948980962590430E-568
        [14] "tone_w_slight_downsweep" p = 8.5569267820494743067975194132611773E-570
        [15] "descending_shriek"      p = 8.94637517682793708888494332509360508E-578
        [16] "croak"                  p = 3.473609866947226739618530183158236048E-652
        [17] "long_grunt"             p = 5.94669351468635867192247994229961122E-662
        [18] "BB"                     p = 7.5409981156102725832606207308379888E-689
        [19] "scream"                 p = 1.00000000000000000000000000000001172E-705
        [20] "tone"                   p = 1.00000000000000000000000000000000393E-705
        [21] "upsweep"                p = 1.00000000000000000000000000000000302E-705
        [22] "moan"                   p = 9.9999999999999999999999999999999844E-706


      "cry"                    sequence (T=267):
        [00] "grunts"                 p = 7.837193464713595526692679862894952494244140496702E-413
      * [01] "cry"                    p = 2.2282378084549612390484233673875500910328944413975352270E-421
        [02] "descending_moan"        p = 8.722592240267048048911386961353765839643249606E-424
        [03] "ascending_shriek"       p = 4.95793620588359687154500092671463838835552503E-436
        [04] "grunt"                  p = 3.06468748563178053864643286191090336773428171576454E-454
        [05] "trill"                  p = 3.5943119753334002779260692873508137789545913649E-464
        [06] "purr"                   p = 7.334319329156543856316837613722789446601632398333885559784690467152064E-472
        [07] "ascending_moan"         p = 2.378602197416250166905778996150181835950485459E-522
        [08] "gurgle"                 p = 1.15495413351188037284282462298637908126694814073680E-548
        [09] "modulated_moan"         p = 2.972055846684411891419293821583360338280591168483388E-612
        [10] "scream"                 p = 2.02686570539996572644755482860951054999378951E-800
        [11] "groan"                  p = 2.1490634542233923848173447552664228613407348624E-807
        [12] "tone_w_slight_downsweep" p = 1.19565977382327552688972237105993479874121846E-984
        [13] "BB"                     p = 2.69572650840213575195132425239884602355E-1071
        [14] "tone"                   p = 8.622373027404958577445504420442791979687E-1092
        [15] "upsweep"                p = 3.165182602162376808256111032109088948E-1120
        [16] "long_grunt"             p = 7.3448207558897082205498048976121828195556E-1170
        [17] "descending_shriek"      p = 2.8404079547783674021385728777529460516E-1199
        [18] "modulated_cry"          p = 3.7929937079214301240939893767680118604E-1215
        [19] "croak"                  p = 7.206344288432929552434630927041611536883E-1282
        [20] "bellow"                 p = 1.40627338281321793388821252319692974500000000049999999999999999999999999999999942E-1290
        [21] "growl"                  p = 2.33167561826979636469039270278941983E-1324
        [22] "moan"                   p = 9.9999999999999999999999999999999722E-1336


      "cry"                    sequence (T=78):
        [00] "grunts"                 p = 1.8027561265501722190424159488150678490128393005773E-93
        [01] "grunt"                  p = 6.51162362178527576300381731673934526077656539273922E-101
        [02] "ascending_shriek"       p = 7.31750756245693292782489417798541463093746702E-106
        [03] "descending_moan"        p = 1.324613576316518528398648256435193612601488853E-108
      * [04] "cry"                    p = 1.32725641128496965799332280490314664081876622383675974315E-128
        [05] "trill"                  p = 6.788635063333404569975657747146454504621067771E-129
        [06] "ascending_moan"         p = 2.256453899708338264901575347362807751147174353E-130
        [07] "purr"                   p = 5.0158003788101380161767235451678228799761358257491716009600621621933148E-145
        [08] "tone"                   p = 1.03027535194187969468977819283229911396133E-160
        [09] "tone_w_slight_downsweep" p = 1.4530238151937955977900002526794699192965E-164
        [10] "modulated_moan"         p = 3.0778441953045799118874654631055120855462326557044406497E-173
        [11] "gurgle"                 p = 2.17348055000246920333535074774406472652539049916133E-175
        [12] "upsweep"                p = 5.7287143430483922357865444829999668E-223
        [13] "groan"                  p = 1.95689670582712859662136012055003309936429E-242
        [14] "scream"                 p = 2.318746935171585854674043258851738605994239914E-283
        [15] "BB"                     p = 1.3364793513291052357797450584785945106E-296
        [16] "long_grunt"             p = 3.98691888888203201219083131376589036639E-343
        [17] "croak"                  p = 2.915188279282920842500567811941712360802E-358
        [18] "moan"                   p = 1.89187999698911974463309324481144814E-368
        [19] "modulated_cry"          p = 2.3072376369515655897700772225128270271E-383
        [20] "descending_shriek"      p = 1.00000000000000000000000000000000314E-390
        [21] "growl"                  p = 1.00000000000000000000000000000000291E-390
        [22] "bellow"                 p = 9.9999999999999999999999999999999914E-391


      "cry"                    sequence (T=122):
        [00] "trill"                  p = 1.8167577401948595613804037927816897534573110313E-150
        [01] "ascending_shriek"       p = 2.893933523252057675788367641047938930578818682E-180
        [02] "grunt"                  p = 1.436115627901374905982494504914701671246314357709455E-206
        [03] "descending_moan"        p = 4.913799784189687677507106005025432081486929360E-231
        [04] "modulated_moan"         p = 3.43949622404889426049309348672415029108856560198147112699E-236
      * [05] "cry"                    p = 5.767893656927216837848213529135578498438350710075559539387E-254
        [06] "gurgle"                 p = 5.13831111474757642792923290677452779565264466505053E-260
        [07] "purr"                   p = 4.889473860812579649261171680811099665692121507755505281174681623534491223E-263
        [08] "grunts"                 p = 4.757564844076906332912497415329059296194151145E-288
        [09] "ascending_moan"         p = 2.781057724743404571717421852742208726113839157E-313
        [10] "groan"                  p = 1.52160712133259361939230563104285615306204174350E-325
        [11] "tone_w_slight_downsweep" p = 3.23077890460745096000295610203997434818470E-403
        [12] "upsweep"                p = 6.25563544578333409243833024782906566E-421
        [13] "BB"                     p = 1.759360005949008313879691636711790689381026E-429
        [14] "tone"                   p = 3.00063341536481934712481688374526760E-471
        [15] "scream"                 p = 6.682791900035804559420408609298562664205200213E-477
        [16] "modulated_cry"          p = 9.510184360325253034915124186053080149170223658E-573
        [17] "descending_shriek"      p = 6.59227379072068073568692537455578006E-576
        [18] "long_grunt"             p = 1.91838466240219683460857511613114705E-590
        [19] "growl"                  p = 6.1054608627942458521540528783367794E-608
        [20] "moan"                   p = 9.9999999999999999999999999999999943E-611
        [21] "bellow"                 p = 9.9999999999999999999999999999999904E-611
        [22] "croak"                  p = 9.9999999999999999999999999999999824E-611


      "cry"                    sequence (T=119):
        [00] "grunt"                  p = 3.1026563106166027570614700948711493197941793824825E-185
        [01] "trill"                  p = 4.85965702508586514061636741620613779113458327E-195
        [02] "purr"                   p = 3.3773585675954084541766978104958372708525419256839337089057222039885059E-210
        [03] "descending_moan"        p = 2.624524315090086556153827953529079680676650600E-233
        [04] "grunts"                 p = 1.2511168578251703958401443646152895445481184444E-233
        [05] "ascending_shriek"       p = 1.08704415010041022626422085408116522178311479E-236
        [06] "modulated_moan"         p = 2.7368022745833423488853644269298855536671371316353486E-242
        [07] "gurgle"                 p = 1.497316502097997429294867284908809953468816470590E-259
      * [08] "cry"                    p = 6.087336890452623567202539535710673677103282776559928940E-260
        [09] "groan"                  p = 4.7954069474360974917343125333889354669252E-284
        [10] "ascending_moan"         p = 3.24518876581145981407873049369765775828214133E-317
        [11] "BB"                     p = 2.06421969573192753419092186088787747E-326
        [12] "tone_w_slight_downsweep" p = 5.07383800642297727535127067838973952252E-336
        [13] "upsweep"                p = 2.0890686588682851344969046537556074124410E-343
        [14] "scream"                 p = 1.52290613162607533991194759801432371285142E-448
        [15] "tone"                   p = 6.991377082029222509544780116892176859588E-472
        [16] "moan"                   p = 9.6033036398259900699155722164620295E-541
        [17] "descending_shriek"      p = 1.36022632863020737736803864218907894E-542
        [18] "modulated_cry"          p = 4.05780705305186712272427856532177173E-553
        [19] "long_grunt"             p = 1.16833352156197017079345913597931397E-558
        [20] "bellow"                 p = 6.19672897799518818816811490062281339999999999999999835E-577
        [21] "croak"                  p = 2.10634723056196696072930985619759281E-588
        [22] "growl"                  p = 1.66977872220670253257896831797269993E-590


      "cry"                    sequence (T=106):
        [00] "ascending_moan"         p = 8.1138511482776308511400901729887141975339701686E-105
      * [01] "cry"                    p = 6.50147108352989942867460983408319521688870833559E-106
        [02] "gurgle"                 p = 3.507215083189427062931035941194485795050393342496E-129
        [03] "groan"                  p = 3.1103402738867558596287501071748507850593501582E-147
        [04] "descending_moan"        p = 7.3476322219844614351265555473519692888760794458E-152
        [05] "modulated_moan"         p = 1.982628798945641184672322507964933662402536967979033894986E-171
        [06] "trill"                  p = 4.914836979709991053430507347152976083427870E-247
        [07] "long_grunt"             p = 4.841926447424868798849586717984081553323E-263
        [08] "grunt"                  p = 2.8526498564890784935285573567417314284898498224E-286
        [09] "ascending_shriek"       p = 2.2656605532447531192460866949888740666551845E-292
        [10] "purr"                   p = 1.3202774651811209135733223058681864567235286978552171000131149541894714144711175653E-340
        [11] "grunts"                 p = 7.395472752584872929161111312653391733415932E-415
        [12] "bellow"                 p = 7.299836929715342145532368326454082966651420000007760078339259818438028856913499840309844301E-425
        [13] "scream"                 p = 5.48055386313799212218833804420820448862E-469
        [14] "croak"                  p = 1.486838501042286609808311659582899420460951604E-471
        [15] "growl"                  p = 1.13265964680492668972981374250067433441E-491
        [16] "descending_shriek"      p = 1.62385362108382831662014332286831138E-497
        [17] "tone"                   p = 7.13878253284567960394562812487675331909E-512
        [18] "tone_w_slight_downsweep" p = 8.0891697476677628057459767345267714E-518
        [19] "modulated_cry"          p = 7.391375061395986396573790081488382160E-529
        [20] "upsweep"                p = 1.00000000000000000000000000000000305E-530
        [21] "moan"                   p = 9.9999999999999999999999999999999918E-531
        [22] "BB"                     p = 9.9999999999999999999999999999999847E-531


      "croak"                  sequence (T=106):
        [00] "grunt"                  p = 8.73956220886167054983129107180819714383992E-157
        [01] "grunts"                 p = 2.11593502525611447916913957220517269518276E-173
        [02] "ascending_shriek"       p = 2.7448427361236864623574887123610349760934E-184
        [03] "cry"                    p = 1.34040713462272714445254010042711399929406001E-190
        [04] "trill"                  p = 1.24400689381679520770514231219595803111367E-192
        [05] "purr"                   p = 1.275983107529004913672055591784098158071618126020065239166045E-198
        [06] "descending_moan"        p = 5.7952748872628733701329152583688385726280497E-201
        [07] "ascending_moan"         p = 1.1407133368125916943020094199988543435392320E-203
        [08] "modulated_moan"         p = 8.31339512309220132074595917135102701919420293E-211
        [09] "gurgle"                 p = 6.202765127808186933523642563340374817950129634225E-240
        [10] "scream"                 p = 1.1888878239064109075908582702136561914223E-241
        [11] "groan"                  p = 8.44603386941644234077031662713979321909748285419E-280
        [12] "long_grunt"             p = 3.334987529251627307925493991052315520554E-337
        [13] "descending_shriek"      p = 7.75809954909916701134058794998909981635561790E-378
        [14] "BB"                     p = 5.72692645762607556441856588837342525E-389
        [15] "tone_w_slight_downsweep" p = 3.50072768620780520357477323051935211E-397
        [16] "tone"                   p = 5.79933892176428949283007016437175853E-430
      * [17] "croak"                  p = 1.17529626070680761912093262557008421E-436
        [18] "modulated_cry"          p = 6.1655315731378038654096575420521432854442492E-445
        [19] "bellow"                 p = 4.384355420172473520692939935659608295290000000000000000000000019779609646039727549691838592062140549E-460
        [20] "growl"                  p = 3.2842933239706238583811752943608457552608376E-461
        [21] "upsweep"                p = 3.1811874149862106398955572148106739E-497
        [22] "moan"                   p = 9.9999999999999999999999999999999918E-531


      "scream"                 sequence (T=237):
        [00] "ascending_shriek"       p = 1.261625475329203412015740744447489914683546272E-379
        [01] "grunts"                 p = 2.334491380250659675918575056237368826379451965562E-400
        [02] "descending_moan"        p = 5.235062168570203199738471142874359033332484047E-439
        [03] "purr"                   p = 7.1920990209979252326557285727734148310079077477095420431057785898917E-450
        [04] "grunt"                  p = 4.58206042757376329050625770488319754377516223994633E-456
        [05] "cry"                    p = 1.5894432423937036614679414922736833434108729366693634259E-474
        [06] "trill"                  p = 1.3136658096167029548459334332486261372834238316E-479
        [07] "ascending_moan"         p = 1.1129294417393277844136266230795245054583188082E-529
        [08] "modulated_moan"         p = 1.945678348727445400602776555087433175723273631475596832E-565
      * [09] "scream"                 p = 1.872041619135777334611145965406823794880663050E-567
        [10] "groan"                  p = 9.99701328073558150538705098233035406919323956E-609
        [11] "gurgle"                 p = 1.786331269756837884107272368020283787101910518319E-643
        [12] "BB"                     p = 2.73940591682043353106809593228198011839E-705
        [13] "descending_shriek"      p = 3.53327148214366580604707799705706417E-779
        [14] "upsweep"                p = 3.6501154119978491083444994823543110E-793
        [15] "tone_w_slight_downsweep" p = 1.7145460950948874040243386140967316779904E-916
        [16] "tone"                   p = 2.6715057247815357422549276595327441400E-1047
        [17] "modulated_cry"          p = 6.065400226486325662762092227195691927729981E-1066
        [18] "long_grunt"             p = 3.439791611907718677289413141798572435E-1068
        [19] "bellow"                 p = 4.20016394882088902861846806816262130000000013713973145031874526722760614370983025E-1136
        [20] "growl"                  p = 2.7002992554293703344323837359847054093E-1136
        [21] "croak"                  p = 4.2090195246167202452420755997878975352E-1145
        [22] "moan"                   p = 9.9999999999999999999999999999999687E-1186


      "scream"                 sequence (T=218):
        [00] "ascending_shriek"       p = 1.19273919596991090920184729359177961701157622E-556
        [01] "grunts"                 p = 1.3299760622029319853066719810020473707836026404235E-599
      * [02] "scream"                 p = 7.62930556668885320394079148464233848460551E-628
        [03] "BB"                     p = 1.356810462097234015356088378900789062E-675
        [04] "grunt"                  p = 2.6767278930403542107434733489172124737459106255279E-764
        [05] "cry"                    p = 3.78165009036331740448061477933419971555465346383875378E-770
        [06] "descending_moan"        p = 1.473306963665123593843424584773516127031360736E-770
        [07] "ascending_moan"         p = 1.29464643809647099178684132943681834001910981E-777
        [08] "purr"                   p = 9.1371392683236885322160962458718535449046923517633934277893305223948750E-793
        [09] "gurgle"                 p = 5.16695411189507662366617287971812871366530980453664E-801
        [10] "groan"                  p = 2.9806805093339638377202652639936649800980088E-828
        [11] "modulated_moan"         p = 3.0356717072078564300980774371191765782635958897214566E-832
        [12] "trill"                  p = 1.567562602559753332549983309576100904483552306E-838
        [13] "descending_shriek"      p = 1.80446522381761404689355116101288378173E-845
        [14] "tone_w_slight_downsweep" p = 2.257954677665143326189411118901976886E-1012
        [15] "modulated_cry"          p = 1.6328373462436996519082219912832451994E-1027
        [16] "long_grunt"             p = 7.4793562863747035029171919563842885E-1028
        [17] "upsweep"                p = 7.860655620655166169298520722044416983E-1042
        [18] "growl"                  p = 4.85289875419838548825687146323848547E-1065
        [19] "tone"                   p = 1.0144828799019363353037871772612072837E-1071
        [20] "bellow"                 p = 1.2180080648102480244676541614614133032122057670E-1078
        [21] "croak"                  p = 1.5102326878357460641234986398386097990E-1079
        [22] "moan"                   p = 2.12697557427270765076161664498549598E-1082


      "grunts"                 sequence (T=107):
        [00] "BB"                     p = 9.56562857132287383605513367143353200817149E-191
        [01] "upsweep"                p = 4.49269935613363574406101897013013106745240277E-255
        [02] "grunt"                  p = 1.3411614949845443204235231458192087304621441981E-286
      * [03] "grunts"                 p = 4.5179239705677337395989806555080530776325016037E-290
        [04] "tone_w_slight_downsweep" p = 8.265743580813830850656219556621878225571934E-303
        [05] "moan"                   p = 8.60642679689928843818105378287635054719183757E-304
        [06] "tone"                   p = 1.86991097392613694408989741858334404091595668E-305
        [07] "trill"                  p = 2.8643211809123094459904542529751123029E-410
        [08] "ascending_shriek"       p = 1.3834930448291897806431500336370377533E-416
        [09] "gurgle"                 p = 4.06170425582720007628265898493299006972466E-432
        [10] "descending_moan"        p = 1.95745389050466450006043474499403804586065E-446
        [11] "groan"                  p = 7.507553857912398749870133912170305144E-488
        [12] "ascending_moan"         p = 1.49431445906249374215413895731141336E-496
        [13] "modulated_moan"         p = 9.97688656793110299512668633800705363E-507
        [14] "purr"                   p = 2.4151224675744668196817865752724740774778097641907185004E-508
        [15] "long_grunt"             p = 4.74655037605204381963766216545354676E-518
        [16] "cry"                    p = 2.42444359644804571194219264240981220910962344E-520
        [17] "croak"                  p = 8.657420105593648230357740081654035753E-525
        [18] "descending_shriek"      p = 1.71465636972813795972996952224184882E-530
        [19] "growl"                  p = 4.7494726121071029553599888711598479E-534
        [20] "scream"                 p = 1.00000000000000000000000000000000872E-535
        [21] "bellow"                 p = 9.9999999999999999999999999999999918E-536
        [22] "modulated_cry"          p = 9.9999999999999999999999999999999382E-536


      "grunts"                 sequence (T=111):
        [00] "BB"                     p = 1.660724527627250590196181689972413237239712398863E-123
        [01] "upsweep"                p = 1.1137813682383538333525041347649533717356346528E-158
        [02] "grunt"                  p = 1.564898402769225316392610410693029167118296835E-225
      * [03] "grunts"                 p = 7.9474170769285105988852309424874291280602911981599E-260
        [04] "trill"                  p = 1.02496248091380926488654504905222577763460849E-292
        [05] "tone_w_slight_downsweep" p = 1.33972328027533823448997427456347815788482305E-417
        [06] "moan"                   p = 8.19787120051195845193398661478403012831951E-418
        [07] "tone"                   p = 8.632560172121872596010342418957097508250607E-424
        [08] "gurgle"                 p = 3.188162044842169153887606198884937282930775873376E-468
        [09] "ascending_shriek"       p = 5.478431463696906301778332783547802051842E-503
        [10] "descending_moan"        p = 2.47754905125399846682404945331527613174637E-503
        [11] "ascending_moan"         p = 9.1559784932000797517038632890512118741549E-509
        [12] "groan"                  p = 2.1531967144126865081469595560655300025437407603E-513
        [13] "modulated_moan"         p = 1.24423816378065315368588326002798491946767E-525
        [14] "purr"                   p = 3.968654850099126575760833745841579490846586386097638E-534
        [15] "cry"                    p = 4.939242850489057661763283784942901327E-550
        [16] "croak"                  p = 7.12200657516503042357379568377149938E-554
        [17] "scream"                 p = 1.00000000000000000000000000000000889E-555
        [18] "growl"                  p = 1.00000000000000000000000000000000464E-555
        [19] "descending_shriek"      p = 1.00000000000000000000000000000000400E-555
        [20] "bellow"                 p = 9.9999999999999999999999999999999903E-556
        [21] "long_grunt"             p = 9.9999999999999999999999999999999677E-556
        [22] "modulated_cry"          p = 9.9999999999999999999999999999999377E-556


      "groan"                  sequence (T=169):
        [00] "modulated_moan"         p = 5.9536836233739939734584086747136258648386602604E-193
        [01] "gurgle"                 p = 5.0251924314449362804092978257056886291543953378151679E-237
      * [02] "groan"                  p = 7.480807110822168458436840625076728330532195200095534E-325
        [03] "descending_moan"        p = 2.8819064097050301591957452264493700311133817E-332
        [04] "purr"                   p = 5.969068372842880974045021163247810221534579071341792794383125027122437205839596759E-334
        [05] "cry"                    p = 9.78138843512603829614355193536247727034613893605E-390
        [06] "ascending_moan"         p = 6.4978770572334578959119536241425634817067849E-432
        [07] "grunt"                  p = 4.651321003157973937800734199103336716405E-442
        [08] "descending_shriek"      p = 5.0645693902412185681984319125201047277899372E-466
        [09] "grunts"                 p = 9.09369061055641364070453141331045292203308E-497
        [10] "ascending_shriek"       p = 2.1528655598673310073779744886376522293062E-529
        [11] "trill"                  p = 7.584418397411403167427480159429068311529015E-558
        [12] "tone"                   p = 3.9493398432146547834387130585693886503E-627
        [13] "BB"                     p = 3.0127002777585026367307756823307955186E-642
        [14] "tone_w_slight_downsweep" p = 1.84274685145281236615427268971114405E-646
        [15] "upsweep"                p = 2.7713368252774699827575395518686050E-652
        [16] "growl"                  p = 1.318981039936205946339716753753570829620074330723E-652
        [17] "long_grunt"             p = 3.599248597022622939407327569536689440143E-656
        [18] "modulated_cry"          p = 2.253402847755475525086849977960512254278309430E-689
        [19] "bellow"                 p = 6.9917158113674599321524041648738507000000000000000000000000000000000000000000000001871352415268994817258300865278915221E-748
        [20] "scream"                 p = 2.7792576282177841728497126894687436953760853E-766
        [21] "moan"                   p = 2.28375384463523976765895517425222714E-803
        [22] "croak"                  p = 9.9999999999999999999999999999999714E-846


      "groan"                  sequence (T=242):
        [00] "modulated_moan"         p = 1.94908239675245961969773298775207763237162765583E-282
        [01] "trill"                  p = 1.73150354320623271167489568343122655147723944888221E-308
        [02] "grunts"                 p = 1.424870330534815239191237425657051689880675488959430190139E-312
        [03] "descending_moan"        p = 9.9299481350157065907692596978192016110307899E-326
        [04] "grunt"                  p = 8.818236475009740955512310057215576804703098808E-327
        [05] "gurgle"                 p = 1.9824159418492194038954020050638407626714092E-388
        [06] "growl"                  p = 4.2524429296257259828973536105329749180790627862498555E-413
        [07] "cry"                    p = 4.905983811466649780482208131760636145151279565E-417
        [08] "ascending_moan"         p = 7.332540557193241718970715076218260746909445740E-448
        [09] "bellow"                 p = 1.315700457318273039451597420012452074935641575000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001275742658729107369779190887039953445399131E-468
        [10] "ascending_shriek"       p = 8.21366718919277257685470457335276389762519985E-500
        [11] "modulated_cry"          p = 1.4053416234356104539486944562375335765695984876391E-532
      * [12] "groan"                  p = 6.686859102798066248952465494403778963736E-697
        [13] "purr"                   p = 4.5416291498049691764296416407512428563174092183883961963938442430020E-710
        [14] "descending_shriek"      p = 1.47954891960777948589600925007759244874E-735
        [15] "long_grunt"             p = 1.5402064500396245043707055966430772694E-921
        [16] "croak"                  p = 1.3538213927225059785223019980921648679210E-1066
        [17] "BB"                     p = 6.0507933719048695252116285559967411E-1174
        [18] "tone_w_slight_downsweep" p = 1.59010769664904286484874835628422630E-1199
        [19] "upsweep"                p = 3.2860731940469997797287958160272028E-1205
        [20] "moan"                   p = 3.04227129097788493100479391310449759E-1205
        [21] "scream"                 p = 5.7927945017039371982654769777335044E-1207
        [22] "tone"                   p = 8.6730007548231504523734629099840822E-1209


      "groan"                  sequence (T=322):
        [00] "cry"                    p = 4.1699446354566426235174957690154934090398347090900E-269
      * [01] "groan"                  p = 9.8119553225579892499583113120569518993712941430702E-275
        [02] "gurgle"                 p = 1.364008603318527630282773192222360693232927870042227E-280
        [03] "ascending_moan"         p = 3.22723715700166120693060272787936973048206319503E-321
        [04] "purr"                   p = 1.585677333168838384591247460154678423013430395929178036857392537888835451788430982333122260E-338
        [05] "descending_moan"        p = 1.07457638917783722213165682385025502290004956108E-345
        [06] "modulated_moan"         p = 8.258933813655086130696393033019895990789398964354301262312E-350
        [07] "ascending_shriek"       p = 5.970605012807974827806967306459546664736474E-665
        [08] "grunt"                  p = 4.594608779678663117586211701573065975853666E-910
        [09] "grunts"                 p = 1.14632396666364944339718283837798424116177813284E-923
        [10] "long_grunt"             p = 1.3787682378544539505428643830052870061725E-1008
        [11] "bellow"                 p = 4.4558278222913166234018399770457741160700000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000856258955628381355154458164525785359280704E-1096
        [12] "scream"                 p = 3.29300684784278525126956749537529368126809921079E-1170
        [13] "descending_shriek"      p = 1.5461391559422626149231755049115173964780637E-1210
        [14] "trill"                  p = 1.899628456762962566510793196358172753E-1381
        [15] "tone"                   p = 2.66145867244071492440931973443064091547E-1442
        [16] "croak"                  p = 8.05916266683940869315891525473355418E-1465
        [17] "growl"                  p = 5.09097881512257860112730670647913660E-1536
        [18] "modulated_cry"          p = 2.90846015087480193255851810503249725E-1555
        [19] "BB"                     p = 1.69618889323970920731511785922637841E-1601
        [20] "upsweep"                p = 1.00000000000000000000000000000000583E-1610
        [21] "moan"                   p = 9.9999999999999999999999999999999694E-1611
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999998764E-1611


      "groan"                  sequence (T=189):
        [00] "cry"                    p = 1.141099246636166583097894324213209449870058974709E-178
        [01] "ascending_moan"         p = 1.102107662254041678770652987764336287254145191009E-198
        [02] "gurgle"                 p = 1.9664541510517221218242239288455249758610643428399E-206
        [03] "descending_moan"        p = 1.90979048653957970074955747497744866159223461646E-208
      * [04] "groan"                  p = 1.45418735565970804582507445135003559904419547947E-213
        [05] "modulated_moan"         p = 2.035588807276005515785756376180938006655708798346223230E-226
        [06] "purr"                   p = 8.610496542297291728678087635163409456027127123014333462854788185194321526070126508334E-274
        [07] "ascending_shriek"       p = 2.13135625785344283236947698098405846162732E-426
        [08] "grunt"                  p = 2.840233678520151377746512514616467918933021E-501
        [09] "grunts"                 p = 1.9033863668504276970396693003604476566051382E-604
        [10] "trill"                  p = 9.032688740590169565506538115261574094100E-615
        [11] "bellow"                 p = 1.07827886274107443633665084107524319544000000000000000000000000000000000000000000000000000000000000000000000000000001705162757266636319915403652677019336E-640
        [12] "long_grunt"             p = 3.983224441593204011254472226535537098606E-648
        [13] "scream"                 p = 6.8448092129187660319050213473388169639506193E-762
        [14] "croak"                  p = 2.498905600346538558888893203183374376E-800
        [15] "tone"                   p = 3.19303438041190333160434121999190138E-813
        [16] "descending_shriek"      p = 5.62379672023146400161102690570095997545090E-840
        [17] "growl"                  p = 3.768870651389220578589854944732460502E-870
        [18] "BB"                     p = 5.0620256507082670584068161309765864468E-923
        [19] "modulated_cry"          p = 2.745011919792429445937255293465766593E-930
        [20] "tone_w_slight_downsweep" p = 3.7530073682243549295461615976173708E-943
        [21] "upsweep"                p = 2.4461650387315807662063961894691465E-943
        [22] "moan"                   p = 1.25998712835536531532176890413755870E-943


      "groan"                  sequence (T=63):
        [00] "gurgle"                 p = 9.37660382042653938980815147875219697558062044203E-99
      * [01] "groan"                  p = 1.266390168278378163579074321136404129923842475540216E-103
        [02] "descending_shriek"      p = 5.290433208103817643114576160469475018131E-116
        [03] "trill"                  p = 1.15418754857091281030570001325309584111094397E-122
        [04] "ascending_shriek"       p = 1.8481863154352112944148055905053047557E-154
        [05] "croak"                  p = 3.053177650273005764348030387771386520E-168
        [06] "bellow"                 p = 3.7211555265487655479077911584863724319000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000049999999999999999999999999999999827E-184
        [07] "purr"                   p = 1.37656613883358804248440549488570062519815144975644597110121144280076024E-186
        [08] "long_grunt"             p = 2.92186935629930963562307934744508574041E-214
        [09] "modulated_moan"         p = 5.248583437177279124236768725432771810128822528158E-224
        [10] "descending_moan"        p = 1.95990275844078134518786871930039990532830927E-224
        [11] "grunt"                  p = 7.2313557188309451631786460753507165722405E-234
        [12] "grunts"                 p = 3.45160890620083728587725433562212179291040E-266
        [13] "cry"                    p = 5.8275099511147110900759254450385446071893E-270
        [14] "scream"                 p = 6.3480805762362623216706390525444314E-313
        [15] "ascending_moan"         p = 2.47242754667239656560354331923539697E-313
        [16] "upsweep"                p = 1.00000000000000000000000000000000257E-315
        [17] "growl"                  p = 1.00000000000000000000000000000000223E-315
        [18] "tone"                   p = 1.00000000000000000000000000000000094E-315
        [19] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999991E-316
        [20] "BB"                     p = 9.9999999999999999999999999999999945E-316
        [21] "moan"                   p = 9.9999999999999999999999999999999925E-316
        [22] "modulated_cry"          p = 9.9999999999999999999999999999999580E-316


      "tone"                   sequence (T=63):
        [00] "grunt"                  p = 9.008207054717140838532583105809898191293183273E-80
      * [01] "tone"                   p = 1.237856309071861436512417636809371280918032055E-80
        [02] "upsweep"                p = 1.6904989823391410084563016295919206203350174639E-82
        [03] "BB"                     p = 7.706711290846080451903564015516879924919067982E-101
        [04] "moan"                   p = 1.39794377714692266649878075452450744801302734346E-121
        [05] "tone_w_slight_downsweep" p = 2.69333185040287394678813475825504757750425833561E-139
        [06] "grunts"                 p = 1.3497589826893166602407806515743555061113381562691845E-152
        [07] "gurgle"                 p = 1.18009381110323733107050391887842758583185070182E-267
        [08] "trill"                  p = 1.5935015501370072672414093538867054015742878E-283
        [09] "purr"                   p = 6.07057350740147986438022412290249429125006691848600E-287
        [10] "modulated_moan"         p = 1.4477340420216311139743860285147035360939919680E-287
        [11] "ascending_shriek"       p = 4.86982188845256262632737628595813353E-296
        [12] "descending_moan"        p = 1.754166348458475029651701205656500509E-296
        [13] "ascending_moan"         p = 5.714722562519107465085253911347502876E-299
        [14] "cry"                    p = 1.799886435962487957346285727587973091977E-310
        [15] "scream"                 p = 9.56465700521381576866307467021183917E-313
        [16] "groan"                  p = 2.647904208259094465709880046760157671E-313
        [17] "descending_shriek"      p = 1.61865452539897130479985773884362076E-313
        [18] "long_grunt"             p = 9.2387474718106061332740190167676399E-314
        [19] "growl"                  p = 1.00000000000000000000000000000000223E-315
        [20] "croak"                  p = 9.9999999999999999999999999999999990E-316
        [21] "bellow"                 p = 9.9999999999999999999999999999999886E-316
        [22] "modulated_cry"          p = 9.9999999999999999999999999999999580E-316


      "tone"                   sequence (T=103):
        [00] "tone_w_slight_downsweep" p = 9.46084719564156052363219874008339083723270E-147
      * [01] "tone"                   p = 1.882798387680650343032738260225063872885732694205E-169
        [02] "BB"                     p = 8.7967316310590600233755401806632397009473483853E-175
        [03] "moan"                   p = 9.299123378633355656811363530500315549207881E-241
        [04] "grunt"                  p = 3.06617834250061843254435454119595569672298100601E-245
        [05] "upsweep"                p = 9.906500774392946608830442518174054934002952E-265
        [06] "grunts"                 p = 1.956305409171999058739690350702296857152559254580E-290
        [07] "gurgle"                 p = 4.996350387408770452597744393663070803804955980E-463
        [08] "ascending_shriek"       p = 2.111673080743637305813735468594435298890E-487
        [09] "ascending_moan"         p = 6.9391237878966603023674590255439175116780E-490
        [10] "descending_moan"        p = 2.95392576452192073344178401908143838675E-491
        [11] "purr"                   p = 1.448624985611562007607202280844264544595594267126405968534E-493
        [12] "trill"                  p = 7.1062097419811397086244502797967824273534E-500
        [13] "modulated_moan"         p = 3.50413523593252791297736268834742979605E-510
        [14] "groan"                  p = 4.095862581074554617980409101034791390018E-511
        [15] "croak"                  p = 1.71064332498038040065635829890950153E-513
        [16] "long_grunt"             p = 1.12394351391254284745073924545370790E-513
        [17] "scream"                 p = 1.00000000000000000000000000000000870E-515
        [18] "cry"                    p = 1.00000000000000000000000000000000746E-515
        [19] "growl"                  p = 1.00000000000000000000000000000000393E-515
        [20] "descending_shriek"      p = 1.00000000000000000000000000000000390E-515
        [21] "bellow"                 p = 9.9999999999999999999999999999999926E-516
        [22] "modulated_cry"          p = 9.9999999999999999999999999999999405E-516


      "long_grunt"             sequence (T=152):
        [00] "trill"                  p = 3.136298472137674042360286111691066434490691E-251
      * [01] "long_grunt"             p = 4.4024304971302590874085244820109169133213E-257
        [02] "bellow"                 p = 5.42064663829504488544227635571024950137300000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000033089195976719438088607681407169287034490E-262
        [03] "gurgle"                 p = 1.085671868192765018053147949724589722179658980428E-263
        [04] "groan"                  p = 3.461725903982507552414642392748358702922894528802276E-266
        [05] "croak"                  p = 2.2078730503130550448897757406402152225487E-295
        [06] "purr"                   p = 6.57998718105173026512944355327458217820369823519999772620283902244519758483881E-327
        [07] "descending_moan"        p = 2.068250087013826141528278233554846105792419283E-330
        [08] "ascending_shriek"       p = 1.29294537093561212902010088805299078304E-350
        [09] "grunt"                  p = 8.518594672298117144141876096877671960742041534E-359
        [10] "descending_shriek"      p = 3.71233976869181568226299161189659543630E-370
        [11] "ascending_moan"         p = 9.347740166953200494548226346205951307E-499
        [12] "modulated_moan"         p = 4.51121619069713644522636347696768913747410E-508
        [13] "cry"                    p = 5.706502557167843892487971493103865587807030430401830E-599
        [14] "grunts"                 p = 2.0090384395793302625436474207790012286E-710
        [15] "scream"                 p = 1.01183832614108611344242320024962676E-717
        [16] "BB"                     p = 5.9108066021976633259470185411733104E-737
        [17] "growl"                  p = 9.99247765370865421183636254311453527E-757
        [18] "modulated_cry"          p = 3.534590824935084101451019755505598912E-757
        [19] "tone_w_slight_downsweep" p = 2.23662713878947553464915014160557993E-758
        [20] "tone"                   p = 1.00000000000000000000000000000000431E-760
        [21] "upsweep"                p = 1.00000000000000000000000000000000304E-760
        [22] "moan"                   p = 9.9999999999999999999999999999999821E-761


      "trill"                  sequence (T=152):
        [00] "ascending_shriek"       p = 2.365706562078087719517792779100361832110851752E-218
        [01] "grunts"                 p = 6.600637061103250509210268185079717016940804592605E-219
        [02] "cry"                    p = 9.08895673381384941623979760862197246518101120462394E-220
        [03] "purr"                   p = 6.406456367032021569101208394689496611708813865176792851341686654E-222
        [04] "grunt"                  p = 1.401236489919141681036538963827196903620581538E-224
        [05] "descending_moan"        p = 7.019824576002340137416623275390914711865211772E-226
      * [06] "trill"                  p = 3.720567517578305964969168970300143359149349753E-247
        [07] "ascending_moan"         p = 8.582636206899748445164768006144097213498865717E-266
        [08] "gurgle"                 p = 4.8683509421122654140357978571764998240356772418E-293
        [09] "modulated_moan"         p = 3.07364498197134087828813779742390799856874602429E-304
        [10] "scream"                 p = 7.54313047983428103842158879525538839159750918E-398
        [11] "groan"                  p = 2.308896017639290170088402956499588676904823975E-401
        [12] "tone_w_slight_downsweep" p = 1.40334911271055228570479757000080207719359359E-522
        [13] "long_grunt"             p = 3.34292121075327205414837005289094398148904E-569
        [14] "upsweep"                p = 1.64296262194474051073953172190193817E-604
        [15] "BB"                     p = 2.75581375777425980372794245494527006629176E-631
        [16] "tone"                   p = 5.295611603620655687936271228132137545080097E-637
        [17] "descending_shriek"      p = 9.02162250652536699363035991460234004E-662
        [18] "modulated_cry"          p = 5.6729794070403342293021978971742859168E-671
        [19] "croak"                  p = 5.994979232755650914823836746843213548694587E-672
        [20] "bellow"                 p = 4.379004124598503577156622407091009200000000086324532460686310658154744768749083167E-704
        [21] "growl"                  p = 2.58025415990488958559554362830933045521191E-710
        [22] "moan"                   p = 1.02631041394318615250957868731446455E-756


      "trill"                  sequence (T=166):
        [00] "ascending_shriek"       p = 3.04144230671411774492378901532597363450785530E-217
      * [01] "trill"                  p = 2.3735035788737890346996693467600738113054320107E-222
        [02] "descending_moan"        p = 3.388492638645958039689352256080141046541218886E-230
        [03] "grunt"                  p = 3.00127597845179222813230659387860742912060459279228E-235
        [04] "cry"                    p = 3.05939855148556813335721802254258008665750977764032382359E-236
        [05] "grunts"                 p = 1.9280381006908536025217409069192139748256125110E-242
        [06] "purr"                   p = 1.554666419882274708583524525022075547597048380413984645097728647233988E-256
        [07] "ascending_moan"         p = 1.108058588529779580259071546448321299038449117E-265
        [08] "modulated_moan"         p = 6.1602070638963828874767104166380099526002190286909659457E-304
        [09] "gurgle"                 p = 1.987720070309202649892674554224225175839033483914479E-318
        [10] "groan"                  p = 9.421007011657778452543005540450094319247959E-463
        [11] "scream"                 p = 1.74605911333413251796397837624556404979799208E-480
        [12] "tone_w_slight_downsweep" p = 1.098076950566629049441546918906637930E-552
        [13] "tone"                   p = 4.21429327680803095471773459275261540E-603
        [14] "upsweep"                p = 1.61364345531669783648603108208602472E-633
        [15] "BB"                     p = 1.38459860995487280227270834337008513E-685
        [16] "long_grunt"             p = 1.16339943876290954409009894495849454E-736
        [17] "croak"                  p = 4.4265958146681219757690864302761364E-779
        [18] "modulated_cry"          p = 5.6009637727863382963037958882705484273E-789
        [19] "growl"                  p = 3.5676455527717191747882633950506708E-817
        [20] "descending_shriek"      p = 2.06371557599559771439248043471857987E-818
        [21] "bellow"                 p = 9.9999999999999999999999999999999996E-831
        [22] "moan"                   p = 9.9999999999999999999999999999999825E-831


      "trill"                  sequence (T=167):
        [00] "ascending_shriek"       p = 3.723413898032201760130214810127634888225741430E-288
        [01] "grunt"                  p = 1.7481148399873568792209036124982880905808824747826E-302
      * [02] "trill"                  p = 5.774210684256431299545265006241163196287080863E-341
        [03] "ascending_moan"         p = 2.176407941193770632594385978395257080006963335E-357
        [04] "cry"                    p = 3.5018300735598945786321092247605685747428691047278810E-372
        [05] "descending_moan"        p = 1.541300425269228359522578898230761832548253760E-376
        [06] "gurgle"                 p = 1.45652226432515744570240027995110191794060776876E-395
        [07] "modulated_moan"         p = 1.3388540191088132612078952051365024623432081549354255E-400
        [08] "purr"                   p = 2.0053396878791813009255867684961324336346232957267700419123603985176E-442
        [09] "grunts"                 p = 2.97460585742507738879483257917416843500507353202E-471
        [10] "groan"                  p = 6.444936189448617722554486285993215573225719E-509
        [11] "scream"                 p = 3.127061854846421719884447308029391387473950910E-571
        [12] "BB"                     p = 1.754720208662763100352179828364285109675E-579
        [13] "tone_w_slight_downsweep" p = 1.174496583800376325609929531559761153702E-635
        [14] "upsweep"                p = 2.9101304822643207250000618298582464E-690
        [15] "long_grunt"             p = 3.950263456037918772871120684572604337E-701
        [16] "descending_shriek"      p = 3.39631522761327846535010193440528978E-722
        [17] "tone"                   p = 2.116529598485389016791796834596104901E-735
        [18] "croak"                  p = 3.372716592337463770805654195111392767E-752
        [19] "modulated_cry"          p = 1.80098224546309990612882117888120369009753E-780
        [20] "bellow"                 p = 4.825693668862064319816006606142645000000000000067209431637297553712729679283550591E-786
        [21] "growl"                  p = 4.6159709507000217559441920025097530E-818
        [22] "moan"                   p = 8.6949968019890110780845952933851105E-833


      "trill"                  sequence (T=75):
        [00] "croak"                  p = 1.49137852876209203454247986064754603492715513E-72
      * [01] "trill"                  p = 2.85036518943036619225956179024019292562559927E-83
        [02] "purr"                   p = 6.60579935566970727337183995598402426377495391112474083035444876728515182E-92
        [03] "gurgle"                 p = 1.9833412902967199689350319433613303459000042462605E-98
        [04] "descending_moan"        p = 1.207427566267246907644868773818345212029703711E-106
        [05] "grunt"                  p = 2.283793529925673065121433006311837496625298E-114
        [06] "groan"                  p = 2.19373703877209035367419070873947786448932513315306E-121
        [07] "bellow"                 p = 3.46282961458317524056072817618258145463666360000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000050000000000000000000000000202516290E-123
        [08] "ascending_shriek"       p = 6.7420126136515981191797779197315517493136046E-126
        [09] "long_grunt"             p = 1.67762435179386994013341686953302665850116E-126
        [10] "descending_shriek"      p = 1.32685045749636394261369527695854039049362167771E-135
        [11] "ascending_moan"         p = 2.659782897687356516858341857981779147681E-163
        [12] "cry"                    p = 4.037000707838633952936424067337546785858E-199
        [13] "modulated_moan"         p = 8.42651514957418499704680178497281582636014906E-328
        [14] "grunts"                 p = 9.935959810126661531569760085202312834E-374
        [15] "scream"                 p = 1.00000000000000000000000000000000657E-375
        [16] "growl"                  p = 1.00000000000000000000000000000000283E-375
        [17] "upsweep"                p = 1.00000000000000000000000000000000276E-375
        [18] "tone"                   p = 1.00000000000000000000000000000000108E-375
        [19] "BB"                     p = 9.9999999999999999999999999999999930E-376
        [20] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999924E-376
        [21] "moan"                   p = 9.9999999999999999999999999999999892E-376
        [22] "modulated_cry"          p = 9.9999999999999999999999999999999552E-376


      "trill"                  sequence (T=86):
        [00] "modulated_moan"         p = 1.379994972146845917796244091208273810713919515188E-83
      * [01] "trill"                  p = 3.7759364722261866867860395632853514824757677745201E-87
        [02] "growl"                  p = 4.321930121048336189791937039485429477535903087168E-104
        [03] "cry"                    p = 1.69805310025430208555187642093896243686560432495626E-104
        [04] "grunts"                 p = 1.0449862034426619919344641455518203422076444722323689620306E-106
        [05] "descending_moan"        p = 3.071946382670256313658392384998439389288092064E-112
        [06] "gurgle"                 p = 1.680370631710345837046230387117941910051335004E-157
        [07] "grunt"                  p = 8.3559900853013843082157773043931215056519608528E-159
        [08] "ascending_moan"         p = 1.2996710409912533524247464934355692961460065583E-170
        [09] "ascending_shriek"       p = 8.388372808238527045415054660036309911906226340E-203
        [10] "groan"                  p = 2.23886854906368162990382478411264700401E-264
        [11] "bellow"                 p = 6.35383707478036817959077109913696138045204000000000000000000000000000000000000000000002887865965051714333967012320582947471047045E-278
        [12] "purr"                   p = 3.9276922114358280960708045039146321505585912739461823300850526003514571E-280
        [13] "modulated_cry"          p = 2.628424705023236267437812245565043325284521568334E-291
        [14] "long_grunt"             p = 1.447089809648969492563558841493898790562503E-359
        [15] "descending_shriek"      p = 5.645581954936638980128183046871393977345422E-368
        [16] "croak"                  p = 2.2419775357710015290508292606888016374594160337E-415
        [17] "BB"                     p = 3.14817896224620091138357958750353806E-418
        [18] "tone_w_slight_downsweep" p = 1.44652664179604788963395643762933763E-422
        [19] "upsweep"                p = 1.67605669364980609850569099968624428E-427
        [20] "scream"                 p = 2.9071691642940265427644585061398738E-428
        [21] "tone"                   p = 1.00000000000000000000000000000000186E-430
        [22] "moan"                   p = 9.9999999999999999999999999999999897E-431


      "ascending_shriek"       sequence (T=150):
        [00] "scream"                 p = 1.222194352900469717184425946681943736547743207E-336
      * [01] "ascending_shriek"       p = 1.242676867641429662658831071214269036438229379E-354
        [02] "ascending_moan"         p = 2.587940722809319559993735148155005537795376379E-380
        [03] "grunts"                 p = 1.01938400824568963609602581446091598059911477635E-397
        [04] "BB"                     p = 2.7743122925106391822453954433762263106E-398
        [05] "descending_shriek"      p = 9.0522428874710072278725819877260544E-401
        [06] "grunt"                  p = 3.39486333121316705896774050147630625536642466549039E-574
        [07] "purr"                   p = 1.21092182632910216729960126381113192447236937721331984507525527694293083E-585
        [08] "trill"                  p = 1.8965502928587816131431824286243134094960750900E-609
        [09] "descending_moan"        p = 6.425813662286178618724053058301793160538887947E-613
        [10] "cry"                    p = 6.10782062347314487520694271983114314667723141350574561826E-617
        [11] "gurgle"                 p = 5.17295313612254444077034626095963723336830062048079E-617
        [12] "modulated_moan"         p = 1.0083242922355703843165922747875742495578961386370434926E-622
        [13] "groan"                  p = 6.91976084365499763869195555900495510548487393E-649
        [14] "modulated_cry"          p = 6.767648258293471925089770921403132306014E-672
        [15] "tone_w_slight_downsweep" p = 3.923135946274762451855296227822153083874E-689
        [16] "tone"                   p = 1.9501471993808003050328634858359222701E-706
        [17] "long_grunt"             p = 1.148106914607525717614372935271042904E-708
        [18] "bellow"                 p = 5.385013379469107288388060448622125999999999999999999999999999916E-722
        [19] "croak"                  p = 1.5246299507237453747664469660072645090E-731
        [20] "moan"                   p = 1.94880418868674810263687272948072774E-734
        [21] "growl"                  p = 1.92197091370032446016488298762777411E-744
        [22] "upsweep"                p = 9.0053450279280761528048005922739551E-747


      "ascending_shriek"       sequence (T=127):
        [00] "grunts"                 p = 1.451066614392622036432901368017590973977781E-273
      * [01] "ascending_shriek"       p = 2.302480461150369952992712190529544573E-281
        [02] "descending_moan"        p = 3.45481888842979017348330390581649121903E-345
        [03] "ascending_moan"         p = 3.62152781449855520674740429610970699E-379
        [04] "gurgle"                 p = 9.532198182538132081444001161042306817E-382
        [05] "scream"                 p = 7.637145395458752774797370312368756299120E-382
        [06] "grunt"                  p = 8.06718164605387733699719438776764212E-394
        [07] "purr"                   p = 1.43948238833872776413181927300711377709785992763485033512319E-400
        [08] "BB"                     p = 5.258225783331785708874340480858402834373E-417
        [09] "modulated_moan"         p = 1.6380788389869553030551447676118195046E-418
        [10] "descending_shriek"      p = 9.3702515050277054287780972050312719634E-423
        [11] "cry"                    p = 5.1686107248865071249354966886035361559E-423
        [12] "groan"                  p = 1.4410523825619071937227354212447483440E-432
        [13] "trill"                  p = 1.43967937940546154727769840511636645E-465
        [14] "upsweep"                p = 2.6601390679220864291618374071943082E-552
        [15] "growl"                  p = 6.8725147455963710961810288137363168E-572
        [16] "long_grunt"             p = 5.5243351926733283482652889896914033E-572
        [17] "tone"                   p = 3.91759466187558237419324678619865133E-588
        [18] "modulated_cry"          p = 4.2855597079129886622141040493386825523E-592
        [19] "tone_w_slight_downsweep" p = 2.01626455828744867036861918131737562E-599
        [20] "croak"                  p = 1.22819229651507735279557770356979298E-616
        [21] "bellow"                 p = 1.32035607374337800207028130043034024072367403915E-621
        [22] "moan"                   p = 9.9999999999999999999999999999999905E-636


      "ascending_shriek"       sequence (T=75):
        [00] "descending_shriek"      p = 1.9819495035120024711233044419382779637848672E-84
        [01] "modulated_cry"          p = 1.651564086358094195165984427350413828277556180564E-106
        [02] "groan"                  p = 2.0480610053318415206943128762825408230526234462875E-142
        [03] "gurgle"                 p = 2.364458342407926207303011970875812254009423875460074E-150
        [04] "descending_moan"        p = 8.7889205529890753802071051730327208653857871E-172
        [05] "scream"                 p = 1.9603080475598322346750709839225445025207E-176
        [06] "grunts"                 p = 3.48466526021504917805399568973569947613E-191
      * [07] "ascending_shriek"       p = 7.9411265707346947486789409123537099328110E-201
        [08] "BB"                     p = 1.85824458650304088411837764194818537E-228
        [09] "modulated_moan"         p = 9.3798697797101280601295799738699945844878105E-267
        [10] "purr"                   p = 5.174216640227744375035352968016493797706717410623058583110198850488354E-274
        [11] "ascending_moan"         p = 3.138544143305377073343610072592393558799797829E-281
        [12] "grunt"                  p = 1.990883082768474842177765734683249923469829E-283
        [13] "growl"                  p = 1.5352930186530112106173810125473014667125332575107E-290
        [14] "cry"                    p = 4.3892837962724414130526101038037398364133795940E-295
        [15] "trill"                  p = 9.24047322075528324534614407254454052466390E-300
        [16] "long_grunt"             p = 8.22689743626064972815807246560130572090E-318
        [17] "bellow"                 p = 2.2433892927474434490936842143939421129000009936123800408783641841119701413569565E-324
        [18] "tone"                   p = 6.73059619605331619204699066172882282E-370
        [19] "croak"                  p = 1.56871942397226111870854307492275386E-373
        [20] "upsweep"                p = 1.00000000000000000000000000000000276E-375
        [21] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999924E-376
        [22] "moan"                   p = 9.9999999999999999999999999999999892E-376


      "ascending_shriek"       sequence (T=89):
        [00] "modulated_moan"         p = 1.8838933753683695956272420043584894187374567288E-130
        [01] "gurgle"                 p = 8.8040318008763563885686230866898155485583174730488E-136
        [02] "descending_shriek"      p = 3.324053316784306847810179460547400452460784E-158
        [03] "groan"                  p = 8.56238786478471902886508458110750931253315390748E-162
        [04] "ascending_moan"         p = 2.790533532905674529277360838442511863842302E-162
        [05] "grunt"                  p = 1.6666609974589818521044762180223309509877E-166
        [06] "descending_moan"        p = 5.6298818853750713374590936610214191313151499E-169
        [07] "grunts"                 p = 4.0733861705587893775181337268369361264629799E-173
        [08] "purr"                   p = 8.170891849140790605780848938594918382920903638243847157412911333067546709854065E-186
        [09] "cry"                    p = 3.09903448749286322070387872858935863536679015E-188
        [10] "trill"                  p = 3.2717064417734019699777150311207299703352647E-218
      * [11] "ascending_shriek"       p = 2.351928139837340703062354680925018676261E-267
        [12] "growl"                  p = 6.64429841980931553767879226436997799868223E-275
        [13] "long_grunt"             p = 4.51047191720684762000974858031831804571E-284
        [14] "scream"                 p = 3.4570845575505968440071838199191277584801E-337
        [15] "modulated_cry"          p = 5.0610843249450448164094950659959788552173E-340
        [16] "bellow"                 p = 2.761429105298174237797363344172763074500000000000000000000000000000000000090089061683208113213955094896013515E-370
        [17] "BB"                     p = 1.31252168344424507116551096047687103E-392
        [18] "tone"                   p = 3.470811776052368730257112059309547728E-410
        [19] "tone_w_slight_downsweep" p = 5.3723262146852313069236225901978869E-435
        [20] "croak"                  p = 5.1583387231103061803446282415923602E-443
        [21] "upsweep"                p = 1.00000000000000000000000000000000315E-445
        [22] "moan"                   p = 9.9999999999999999999999999999999905E-446


      "ascending_shriek"       sequence (T=201):
        [00] "ascending_moan"         p = 1.2189240597993967913276005494224297060071886606774E-210
      * [01] "ascending_shriek"       p = 1.1558402413259024118862120872797327193861247032E-233
        [02] "grunt"                  p = 1.543819301855157819207178968909198495114965548564866E-235
        [03] "descending_moan"        p = 6.27008367326112938634323169212243189808621022493E-245
        [04] "gurgle"                 p = 8.570870315472146530291500045529069234137925872584E-289
        [05] "growl"                  p = 2.1192817946005167661220953994290837564449676251893E-368
        [06] "modulated_moan"         p = 8.5762620312041486050903171925800710220399477E-402
        [07] "grunts"                 p = 1.011005868370881255364085519658067990533376E-467
        [08] "groan"                  p = 2.3301399994121010167964144596491282269140449E-495
        [09] "purr"                   p = 1.0195227928549643783250405632247638892658136371853764393726525727607509434002E-501
        [10] "cry"                    p = 9.36720097362710003186176262403010278699333872E-553
        [11] "descending_shriek"      p = 7.279629868538382523117007977174517437884E-554
        [12] "trill"                  p = 2.746968532988318606378154092689535469768561284E-558
        [13] "modulated_cry"          p = 9.223347064675071432639546933444198497383E-689
        [14] "long_grunt"             p = 5.03013652500479665304338554188869239E-707
        [15] "BB"                     p = 1.196233086520264922405446351611975666299E-841
        [16] "bellow"                 p = 3.79122708176197811636678806452498046360000000000000000000000044398191153100713082512836942584097E-849
        [17] "scream"                 p = 7.172069429286276323392246655376654019941E-850
        [18] "croak"                  p = 5.77404367570543543549540809265028431E-1001
        [19] "tone"                   p = 1.23732309645743327558784267375814494E-1001
        [20] "upsweep"                p = 1.00000000000000000000000000000000408E-1005
        [21] "moan"                   p = 9.9999999999999999999999999999999752E-1006
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999328E-1006


      "ascending_shriek"       sequence (T=143):
        [00] "descending_shriek"      p = 1.196940156933457738003313091634317131760096E-272
        [01] "gurgle"                 p = 6.33543929231334406956265213235354589891975377973E-309
      * [02] "ascending_shriek"       p = 6.6382633442999301037220807893489686658911E-330
        [03] "groan"                  p = 2.4766353752382861426818238950665395506969975404186E-335
        [04] "long_grunt"             p = 2.40954289800844146492889309399205966562E-349
        [05] "bellow"                 p = 6.274185576152919640941492629117140734900000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000002603759493289881221334610216979783033080277926E-355
        [06] "trill"                  p = 5.0797925709928281712455381164707945158958E-355
        [07] "purr"                   p = 2.39262786213189128383096383741088190318590362510526120069802115835164732455E-372
        [08] "grunt"                  p = 1.15590891084412548166223467868251945387096084696E-377
        [09] "croak"                  p = 2.78855719167079337890850812904405195112654877E-403
        [10] "descending_moan"        p = 1.395896625490733216550583607410013543591E-409
        [11] "modulated_moan"         p = 7.246926682176127538059548917033436929374470E-501
        [12] "ascending_moan"         p = 7.354634445066180366889160682066821016E-574
        [13] "cry"                    p = 4.6915862445873141036656918741792245712E-578
        [14] "grunts"                 p = 1.838652587885758431021686982419460954E-629
        [15] "scream"                 p = 4.683860070555998998501842744933168845E-636
        [16] "BB"                     p = 2.56295778118198264326417565379179190E-660
        [17] "modulated_cry"          p = 2.35131343200444308608619215223344915E-663
        [18] "growl"                  p = 1.00000000000000000000000000000000607E-715
        [19] "tone"                   p = 1.00000000000000000000000000000000398E-715
        [20] "upsweep"                p = 1.00000000000000000000000000000000298E-715
        [21] "moan"                   p = 9.9999999999999999999999999999999830E-716
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999604E-716


      "ascending_shriek"       sequence (T=75):
        [00] "gurgle"                 p = 9.28032295569669575164993443796353163804111777708E-101
        [01] "croak"                  p = 1.067016110980705571984052826952019955192106E-125
        [02] "descending_moan"        p = 1.31940325901667660505624033513345418936726283100E-143
        [03] "grunt"                  p = 1.069861596682642110040891376843638857989137195532469E-157
        [04] "trill"                  p = 5.05990890655610303249455462679726501716874507E-159
      * [05] "ascending_shriek"       p = 3.621740919110325793103999910073547625337677E-162
        [06] "ascending_moan"         p = 1.09191937758475739165029305971580644950808700E-165
        [07] "descending_shriek"      p = 1.203331635850703171270492569810721959407752113E-196
        [08] "bellow"                 p = 4.460513059932827852052339392907440239324884200000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000049999999999999999999999999999999825E-221
        [09] "long_grunt"             p = 6.2562193035546713316302708873953052125185115E-223
        [10] "purr"                   p = 3.25240463684612638313031716489651491719647391996154024021757860188045325319E-225
        [11] "groan"                  p = 1.82940003411708277223170472840563602633510484790090E-230
        [12] "modulated_moan"         p = 1.574195635212165002155523245676357062E-237
        [13] "cry"                    p = 1.95391427603812886106199366663477273259727753907430E-251
        [14] "growl"                  p = 6.9412277837293809492167736889112393E-291
        [15] "modulated_cry"          p = 4.93962612951053294098422534667098877E-339
        [16] "grunts"                 p = 7.6453830296359203297667849159028237682E-360
        [17] "scream"                 p = 4.9020469721560418704883360823661990E-372
        [18] "upsweep"                p = 1.00000000000000000000000000000000276E-375
        [19] "tone"                   p = 1.00000000000000000000000000000000108E-375
        [20] "BB"                     p = 9.9999999999999999999999999999999930E-376
        [21] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999924E-376
        [22] "moan"                   p = 9.9999999999999999999999999999999892E-376


      "ascending_shriek"       sequence (T=61):
        [00] "croak"                  p = 4.412938794693995079861109803481326489624252095E-69
      * [01] "ascending_shriek"       p = 1.906076994955594856760014453122611523191469E-99
        [02] "gurgle"                 p = 6.5119449731134467464075993389365332643612890149999E-105
        [03] "bellow"                 p = 1.3308979905575292767135071153006235105720000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000030270714859516493659273543560312231E-110
        [04] "trill"                  p = 6.12778610848600288521226803260708282555980E-111
        [05] "long_grunt"             p = 1.471370552786267504624512757576785683969849E-112
        [06] "grunt"                  p = 3.36079441233517765997391922547607998077886641904E-121
        [07] "descending_shriek"      p = 1.1276878725798369738774991420496208353654195E-124
        [08] "descending_moan"        p = 3.731427444068614182023354711121829131205581118E-136
        [09] "purr"                   p = 5.344521880591123350421537759057979172005419442335867306373926066721327630986E-142
        [10] "groan"                  p = 8.79395503618377749916242891339850861163404145939469E-143
        [11] "ascending_moan"         p = 1.6059850722959503975887987057953935911103E-146
        [12] "modulated_moan"         p = 8.62533874033864247754036871821647562077777845391E-216
        [13] "cry"                    p = 9.358950106142368973643953213757002633896E-265
        [14] "grunts"                 p = 7.51469775474690262826471840496976189555E-297
        [15] "modulated_cry"          p = 3.70391923972931093293599208875540003E-297
        [16] "scream"                 p = 1.026825356156278816518794358874635806905E-298
        [17] "upsweep"                p = 1.00000000000000000000000000000000259E-305
        [18] "growl"                  p = 1.00000000000000000000000000000000202E-305
        [19] "tone"                   p = 1.00000000000000000000000000000000083E-305
        [20] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999984E-306
        [21] "BB"                     p = 9.9999999999999999999999999999999955E-306
        [22] "moan"                   p = 9.9999999999999999999999999999999929E-306


      "ascending_shriek"       sequence (T=34):
        [00] "croak"                  p = 6.79612472766515829419989265355776517099034862E-19
        [01] "trill"                  p = 1.4553467377194465806909357840044632052007653E-43
      * [02] "ascending_shriek"       p = 1.0637617758267735673796508338161700661983E-51
        [03] "groan"                  p = 1.1475329592822346743915878093630686264288632749297876E-57
        [04] "descending_moan"        p = 9.905281364958835327761765862397664928439866E-58
        [05] "grunt"                  p = 1.147966710810134369326136540022473974031223E-59
        [06] "gurgle"                 p = 2.56690649027968516471201500756875121925141226E-61
        [07] "purr"                   p = 4.13671100056547144498369492642327236736761098700184942526635269244E-73
        [08] "bellow"                 p = 7.66867994373081554063919380564919050000000000000000000000000000000000000000000000049999999999999999999999999999999848E-89
        [09] "ascending_moan"         p = 2.090122126676303792591589421778425474E-89
        [10] "long_grunt"             p = 4.4257917733809660422598246077311481E-91
        [11] "descending_shriek"      p = 3.20710120886444963314036296492100600E-93
        [12] "cry"                    p = 2.1507403088326281338365766932764495086360E-104
        [13] "scream"                 p = 1.00000000000000000000000000000000444E-170
        [14] "upsweep"                p = 1.00000000000000000000000000000000324E-170
        [15] "tone_w_slight_downsweep" p = 1.00000000000000000000000000000000149E-170
        [16] "grunts"                 p = 1.00000000000000000000000000000000075E-170
        [17] "growl"                  p = 1.00000000000000000000000000000000068E-170
        [18] "BB"                     p = 1.00000000000000000000000000000000060E-170
        [19] "moan"                   p = 9.9999999999999999999999999999999989E-171
        [20] "tone"                   p = 9.9999999999999999999999999999999967E-171
        [21] "modulated_moan"         p = 9.9999999999999999999999999999999781E-171
        [22] "modulated_cry"          p = 9.99999999999999999999999999999996744E-171


      "ascending_shriek"       sequence (T=70):
        [00] "descending_shriek"      p = 1.5165019633071765874368636365111765152206305E-151
      * [01] "ascending_shriek"       p = 9.3357723907727988455939795789670662153532972E-166
        [02] "long_grunt"             p = 5.00732329024060739789820849057752984106839261E-186
        [03] "gurgle"                 p = 3.9623358519897223006072875132700446713098290547247E-190
        [04] "grunt"                  p = 2.6749902283972689068479188826506468059203367E-190
        [05] "croak"                  p = 1.3602510551363917479107353853476635511526104377E-195
        [06] "trill"                  p = 5.79643006497793733321013389672793836169309302E-205
        [07] "bellow"                 p = 1.36145997217543278812425677844050316989380640000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000049999999999999999999999999999999835E-206
        [08] "groan"                  p = 3.4671285168973933317591582520399948822378220986714440E-207
        [09] "purr"                   p = 6.8249974691657387323719320481444917088825605357000530779237812897997474767379E-217
        [10] "descending_moan"        p = 1.795237616828843377713875292000190550677626971E-226
        [11] "ascending_moan"         p = 4.63133091194067586522563644241889879100563833E-270
        [12] "scream"                 p = 1.42404403730798144167776345040551707E-274
        [13] "modulated_moan"         p = 2.3306066051683552368661166337499473696566572346E-285
        [14] "BB"                     p = 1.34564683675635211557146983176674316E-291
        [15] "cry"                    p = 7.030889249577522108566015172745236941346472970E-293
        [16] "modulated_cry"          p = 1.82460208714649825525004559391249371E-316
        [17] "grunts"                 p = 2.42641509383282991863228162384690343E-326
        [18] "growl"                  p = 2.8688127261691612741226754500606121E-348
        [19] "upsweep"                p = 1.00000000000000000000000000000000266E-350
        [20] "tone"                   p = 1.00000000000000000000000000000000103E-350
        [21] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999939E-351
        [22] "moan"                   p = 9.9999999999999999999999999999999897E-351


      "ascending_shriek"       sequence (T=66):
        [00] "descending_shriek"      p = 1.13891021032871165615878901328098696830994192E-115
        [01] "croak"                  p = 1.095155023701682287033958751778369182878328883E-140
        [02] "gurgle"                 p = 1.49968009938872731406124993817208436506896125987859E-144
        [03] "grunt"                  p = 1.841618485720961362712583904420499092753275042242852E-146
        [04] "trill"                  p = 5.749885257475392910428169527136986416828251E-147
        [05] "long_grunt"             p = 1.88925713136458987487299945195306251391E-152
        [06] "purr"                   p = 1.71419893171036023374979979413023346424057358696449074978292504837899310279104465042E-155
        [07] "bellow"                 p = 1.294108361750267631304445466740425332753786500000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000025717117581410259976757374252753873E-159
      * [08] "ascending_shriek"       p = 6.08717188529615895651624477666963514698657744E-160
        [09] "groan"                  p = 1.08499245929332956191459936059250561239259666602837890627E-162
        [10] "descending_moan"        p = 3.8257990121609638004290199782328539929215667393E-164
        [11] "ascending_moan"         p = 1.25174497409314688718139822284423621884877050E-224
        [12] "modulated_moan"         p = 9.77934159885249652906741105770402771671E-258
        [13] "cry"                    p = 1.34540389093408886177821713145441222205426228341560550E-261
        [14] "grunts"                 p = 1.51693053695393156462916206319071074889E-303
        [15] "BB"                     p = 4.05642153751514670873279188041918536E-313
        [16] "scream"                 p = 2.98870069557290684029083850183593351E-318
        [17] "modulated_cry"          p = 3.12020495276113321455597298445651344E-327
        [18] "growl"                  p = 1.76770542504598557857772380168475804E-328
        [19] "upsweep"                p = 1.00000000000000000000000000000000245E-330
        [20] "tone"                   p = 1.00000000000000000000000000000000094E-330
        [21] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999972E-331
        [22] "moan"                   p = 9.9999999999999999999999999999999914E-331


      "modulated_moan"         sequence (T=157):
        [00] "cry"                    p = 5.5279864418517464971154014098789537533799342383E-183
        [01] "descending_moan"        p = 1.36812906454169467095240218748677687313412252E-209
      * [02] "modulated_moan"         p = 1.892621016979460477642133574850370440002922809854E-210
        [03] "ascending_moan"         p = 1.619159971124129787281593255108527369963699030990E-267
        [04] "gurgle"                 p = 1.14659476084188048244625424278492802378873319E-269
        [05] "growl"                  p = 4.115413800119206785723031093040250814201006649860497394E-293
        [06] "trill"                  p = 2.096712507383515014872919965276597025567471185059670E-309
        [07] "grunt"                  p = 2.6619825917080986078092448297959336376514766826668E-318
        [08] "ascending_shriek"       p = 1.545494844041459423174254479655966210600762E-364
        [09] "grunts"                 p = 7.0350970410392393938314879683317417487336102671389234068E-405
        [10] "purr"                   p = 2.487224314356484253708298787660824376517573752088046190549466329483960215481312E-444
        [11] "modulated_cry"          p = 5.94264280062410352438061799130284966329599860167992E-494
        [12] "groan"                  p = 1.3291691327014413636673312730276351388524176E-544
        [13] "descending_shriek"      p = 3.260378431089968543694703610207729399562E-561
        [14] "bellow"                 p = 1.31486127836896127300501754515272920376304364490000021559094631891521319676279213722972838E-602
        [15] "tone_w_slight_downsweep" p = 1.23308676058483931405109776571297201E-605
        [16] "long_grunt"             p = 8.9152641360989367557471977030655630253E-652
        [17] "BB"                     p = 6.7379764752417247848172103702865763E-686
        [18] "croak"                  p = 4.00228817496949371462239937519104395E-764
        [19] "scream"                 p = 1.00000000000000000000000000000001289E-785
        [20] "tone"                   p = 1.00000000000000000000000000000000459E-785
        [21] "upsweep"                p = 1.00000000000000000000000000000000306E-785
        [22] "moan"                   p = 9.9999999999999999999999999999999810E-786


      "modulated_moan"         sequence (T=195):
        [00] "grunt"                  p = 9.2434195666149570430002813390116351184151609884849E-203
      * [01] "modulated_moan"         p = 6.406521249367553374210998920674571470073743279639E-235
        [02] "grunts"                 p = 8.94490360952710498835516451176993816564412651849581024806E-252
        [03] "descending_moan"        p = 4.091494580474105008987523623561302043266146428E-267
        [04] "gurgle"                 p = 3.14978889290678387805656407284797250344945701E-272
        [05] "growl"                  p = 6.1895880775731915437649716716035151777561488374547266412E-280
        [06] "cry"                    p = 2.209769550745641336039315708436231560845347780361489E-281
        [07] "trill"                  p = 2.07651804408572519937859905356282780103358970113998E-299
        [08] "ascending_moan"         p = 2.86115449176000062459733545040382136665927088E-327
        [09] "ascending_shriek"       p = 4.99686532250449073219474467090272971330671510E-332
        [10] "modulated_cry"          p = 2.4745518247540239998083450262691208187884738724E-373
        [11] "groan"                  p = 1.159659201894591906526145396261885400932E-506
        [12] "purr"                   p = 9.13380850699086503385474642981731719205560005390716724E-550
        [13] "bellow"                 p = 2.1841081826289784752708456411403192373000000000000000000000000171376566415096092592837313288699727968457E-584
        [14] "descending_shriek"      p = 5.6139427723329399320456841764667986138E-629
        [15] "BB"                     p = 3.41569097239953649528014435049102816E-875
        [16] "long_grunt"             p = 3.0898145914230168121686755121758768486E-875
        [17] "croak"                  p = 2.2998077655502675430590057636746930017081607E-892
        [18] "tone"                   p = 1.04515397743940077688675631854627166E-968
        [19] "scream"                 p = 7.8733489883090480021711824070013968E-972
        [20] "upsweep"                p = 1.00000000000000000000000000000000385E-975
        [21] "moan"                   p = 9.9999999999999999999999999999999758E-976
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999357E-976


      "modulated_moan"         sequence (T=109):
        [00] "cry"                    p = 2.970713855720674941630389236947978119495983807567223E-133
        [01] "descending_moan"        p = 2.2491308750344513458736875429965825762562751395E-136
        [02] "ascending_moan"         p = 8.67547388898139072536373087265991255994769E-150
      * [03] "modulated_moan"         p = 4.048576029854666361315490837538588473984806979349081E-151
        [04] "grunts"                 p = 9.38943258430520403356829618851246764859701698547690168E-177
        [05] "grunt"                  p = 1.2042613193254923057497659746911505010884622883493885E-184
        [06] "trill"                  p = 3.7122876463048424279484854730987621812388821746035E-185
        [07] "gurgle"                 p = 1.88879969163425059304762032156674620660636010E-185
        [08] "growl"                  p = 5.516762050221618436307142888238427290020473180146491309E-190
        [09] "purr"                   p = 7.366484976799463752102815078158902903736068081350384125203124948865E-270
        [10] "groan"                  p = 1.8890312891502903764793305940492435687E-351
        [11] "ascending_shriek"       p = 1.24218141967655078936220718326152522036012331E-387
        [12] "modulated_cry"          p = 6.93949013196467371539636067104250061910487718829E-398
        [13] "descending_shriek"      p = 2.48564213281427111990721125400752640E-400
        [14] "bellow"                 p = 3.4004797850411619536540398046576909429866027883921355160E-430
        [15] "tone_w_slight_downsweep" p = 2.6765246293176675397077326104532137E-443
        [16] "long_grunt"             p = 7.2632535711306505477274927764565093E-460
        [17] "croak"                  p = 2.9441522720912744855899926490043591527659977E-524
        [18] "BB"                     p = 1.60225034672912932218134470795469872E-529
        [19] "upsweep"                p = 4.6650783746801602681857565288602334E-543
        [20] "moan"                   p = 9.5725047161147563362133612356307680E-544
        [21] "scream"                 p = 1.00000000000000000000000000000000877E-545
        [22] "tone"                   p = 1.00000000000000000000000000000000248E-545


      "modulated_moan"         sequence (T=267):
        [00] "grunt"                  p = 9.856065538899686699967154133308436710007814832242384E-304
      * [01] "modulated_moan"         p = 4.017696625711933740105582493472773965252092507174996E-362
        [02] "grunts"                 p = 1.065543004880932723708080007650887403815974516302985220487639E-384
        [03] "descending_moan"        p = 9.093342940523518825216509803655839950360396512E-386
        [04] "gurgle"                 p = 1.645146812886239167395700818068144548842205547E-386
        [05] "ascending_moan"         p = 1.1264704903448624140482481572401114590835239429606E-430
        [06] "cry"                    p = 7.393306268187598641397214613696464009520613741611649E-477
        [07] "growl"                  p = 3.5221901506125337341785168998099788399182210694301790948E-527
        [08] "trill"                  p = 2.04638753654204310697706004833711238816143211453792E-550
        [09] "modulated_cry"          p = 1.55459528811643011064265514115150678797935440E-648
        [10] "ascending_shriek"       p = 8.84917972329024443510372291788329361179938024E-650
        [11] "groan"                  p = 1.76478319013251982050880486270717838E-712
        [12] "bellow"                 p = 2.4618905383184708075580069832020028988993000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000530492934400791887308318672673135921672E-734
        [13] "descending_shriek"      p = 1.44752198734267556757291245324870172813E-745
        [14] "purr"                   p = 1.34289062589536998559607900671301775709880380926276E-789
        [15] "long_grunt"             p = 3.1550842098519622201356219942624824E-1145
        [16] "BB"                     p = 2.42376083850712152865760293416165375E-1151
        [17] "tone_w_slight_downsweep" p = 1.12280659041518042976921704100693093E-1184
        [18] "moan"                   p = 6.7197915795971126645476671681026686E-1195
        [19] "upsweep"                p = 1.16347236849422191400838348901127534E-1201
        [20] "croak"                  p = 5.8953444816215869638250116437627754641453E-1226
        [21] "scream"                 p = 2.4044323808184442548759996634614724E-1318
        [22] "tone"                   p = 7.0910993584587818061743531441006441E-1334


      "modulated_moan"         sequence (T=285):
        [00] "growl"                  p = 1.32175399553329676345737684087627673742657935382031075E-298
      * [01] "modulated_moan"         p = 6.2807414002750769539873095170412975648296800076E-313
        [02] "cry"                    p = 1.2260893603598020085251829008452064258723258818E-336
        [03] "grunts"                 p = 6.578727398493096362460313958220199397953755852683812275394469E-397
        [04] "trill"                  p = 2.04013339143263689466194003760409443853572372634E-400
        [05] "descending_moan"        p = 8.67927403319087511472582525347572688273573795E-402
        [06] "gurgle"                 p = 1.367975477506634382358415347951301362276157079135E-437
        [07] "grunt"                  p = 7.9270311282430655316846176997989094344322814031E-461
        [08] "ascending_moan"         p = 4.5678581317381389293406596390043293361420473E-486
        [09] "ascending_shriek"       p = 5.34199872467186750833721331936192529173595962E-526
        [10] "modulated_cry"          p = 9.155499775740253546899055068046894542342581E-800
        [11] "purr"                   p = 1.185934358879929824682160857217746191185811207065829807598923564506663824030465449E-880
        [12] "groan"                  p = 3.88351452381690646542403058127276173328462E-884
        [13] "bellow"                 p = 8.066177894387809286371941878720787711182366000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000168565545274671700838195810016960011645E-941
        [14] "descending_shriek"      p = 7.9545221072342229288655651288306656714416165E-989
        [15] "long_grunt"             p = 1.259394341513742608577836810180189241620E-1279
        [16] "croak"                  p = 1.415255778418132453947685172580195340E-1287
        [17] "BB"                     p = 1.14712350767498107189185153506971960E-1360
        [18] "upsweep"                p = 6.0049006137353393703520091720129124E-1405
        [19] "scream"                 p = 1.07893304203361747237816455411164554E-1423
        [20] "tone"                   p = 1.00000000000000000000000000000000841E-1425
        [21] "moan"                   p = 9.9999999999999999999999999999999678E-1426
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999998890E-1426


      "modulated_moan"         sequence (T=216):
        [00] "cry"                    p = 2.47866831396496834346300510564834847875468698534818435754E-330
      * [01] "modulated_moan"         p = 1.3274803899234063691075498892253282248583248318543878614E-367
        [02] "descending_moan"        p = 2.311562074030651631094498106671788781448330267E-371
        [03] "grunt"                  p = 4.01844433437431943851904912822320840932918778502343E-391
        [04] "ascending_moan"         p = 9.526407347192908947078545330694370230828902173E-393
        [05] "trill"                  p = 3.3942116109405530432283735228361040518071750278E-406
        [06] "ascending_shriek"       p = 3.613966705431634502518177745680867240104607247E-427
        [07] "gurgle"                 p = 1.16513766533911229604375383575983434251861575350427E-457
        [08] "purr"                   p = 8.5959500444402721724796665887576556236563953577197219412112843055637756E-511
        [09] "grunts"                 p = 9.146957091251502841945793664678971665534527253172E-519
        [10] "scream"                 p = 3.332667324096523601257057497326280126465028732E-626
        [11] "groan"                  p = 8.20719677532725257294133075061185720829737E-663
        [12] "tone_w_slight_downsweep" p = 2.00955226923449773441684229702588685584755E-758
        [13] "long_grunt"             p = 3.67702249652147230516545358063586134483E-857
        [14] "BB"                     p = 1.35834808628398727998629927246923051129E-869
        [15] "growl"                  p = 1.84251409977981715933890469026888724E-938
        [16] "tone"                   p = 1.0920370575969996939352020546756125311558E-950
        [17] "croak"                  p = 3.848800263545736337796958361767060562790E-979
        [18] "upsweep"                p = 1.37980320029165887430529603749282769E-990
        [19] "descending_shriek"      p = 3.03092099613591788841950643445998046E-1003
        [20] "modulated_cry"          p = 8.155119701626209570977051366196682987E-1023
        [21] "bellow"                 p = 5.04314475413599816254554743070173702068296176148387602047347794E-1046
        [22] "moan"                   p = 9.9999999999999999999999999999999702E-1081


      "modulated_moan"         sequence (T=111):
        [00] "grunts"                 p = 8.9438272713214927824663767447374794786570485921E-137
        [01] "descending_moan"        p = 7.395125526179589005280691718547525187621144577E-140
        [02] "cry"                    p = 1.634877927501875311562994965811959588756050884565284905347E-144
        [03] "trill"                  p = 9.284689258290509996644715661608222453075557383E-147
        [04] "purr"                   p = 1.12334481335027560114063939153428601373997998490771903382462905371444962E-148
        [05] "grunt"                  p = 6.3555023269124106666979577636781913501803864E-160
        [06] "ascending_shriek"       p = 1.24788122950877817841774270839859100029680239E-166
        [07] "ascending_moan"         p = 1.61663373149115303294181755048380118374002985E-168
      * [08] "modulated_moan"         p = 6.17437006095892844314534551897075241097387156363577E-196
        [09] "gurgle"                 p = 5.2924249242734021804211994687110174768843833428E-203
        [10] "scream"                 p = 3.94342650171378085761690999334817024912343E-271
        [11] "groan"                  p = 8.45916255172653359025837160973361218420702394042E-294
        [12] "tone_w_slight_downsweep" p = 3.72320810336664666332319988556482566079E-413
        [13] "BB"                     p = 1.42273811150859218055843262721852484519E-459
        [14] "descending_shriek"      p = 8.8809884441059540122268941838629047E-474
        [15] "long_grunt"             p = 2.073013992320147196842420612191903012E-475
        [16] "tone"                   p = 7.83272463231302308413374336415893642E-482
        [17] "modulated_cry"          p = 7.803945659878002810265969576207611541564E-488
        [18] "upsweep"                p = 1.350639319248454260694018034448619638547E-491
        [19] "bellow"                 p = 3.1909390686897926750101547049336813999999999999999999999999999825E-526
        [20] "croak"                  p = 8.0752748269441056105578956757287951E-551
        [21] "growl"                  p = 3.06743876634725455455813189425913475E-553
        [22] "moan"                   p = 9.9999999999999999999999999999999936E-556


      "modulated_moan"         sequence (T=78):
        [00] "bellow"                 p = 2.15606380439775721045805001150193883322500000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000183602664101865628938294499627515359065E-57
      * [01] "modulated_moan"         p = 3.66029074566008039932493247062444373533868409117854622E-73
        [02] "descending_moan"        p = 3.276213675348917422078142339224865753487612819E-82
        [03] "trill"                  p = 3.42052292440602687958340908952862161783892604423840E-111
        [04] "grunts"                 p = 3.6410537694352288834397634575683804610617732979444502286E-119
        [05] "gurgle"                 p = 4.82990481084255501220919830683012037822722066E-124
        [06] "ascending_shriek"       p = 8.4140980269039414893373252985555927678779E-140
        [07] "grunt"                  p = 1.324207248162200458746945878949474042261217258E-153
        [08] "ascending_moan"         p = 8.92626695351374147991805311030105885721181443984E-171
        [09] "cry"                    p = 1.054601574229143649593400485152365932459085142923E-184
        [10] "descending_shriek"      p = 4.18905129445131130054250058278497514494480E-194
        [11] "modulated_cry"          p = 7.8319325825479176353374203694274665434676432E-199
        [12] "long_grunt"             p = 7.346785591185821223282596181945107043849E-206
        [13] "growl"                  p = 1.1691682682938307215403509128730001070085180445629387727E-207
        [14] "purr"                   p = 2.8008078210575733817510306971136228752768037192739122059698341940E-250
        [15] "croak"                  p = 1.1032005911906610886791372235770352184234E-272
        [16] "groan"                  p = 1.4863029960915399069995964018585002376E-294
        [17] "BB"                     p = 1.27382563041100126198232383230890490E-387
        [18] "scream"                 p = 8.8345698600693930511888020949382007E-389
        [19] "upsweep"                p = 1.00000000000000000000000000000000280E-390
        [20] "tone"                   p = 1.00000000000000000000000000000000134E-390
        [21] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999917E-391
        [22] "moan"                   p = 9.9999999999999999999999999999999879E-391


      "modulated_moan"         sequence (T=139):
        [00] "trill"                  p = 8.8541916761782362888937240811701189038707329918825E-137
      * [01] "modulated_moan"         p = 3.79982132161897677304312205400606563143151281913E-138
        [02] "grunts"                 p = 2.91579500817590352982696242614835518840913037394868980E-160
        [03] "descending_moan"        p = 3.215221777369387182560008043539019323187080405E-162
        [04] "growl"                  p = 3.445093490325298910993153587113275733658172701E-181
        [05] "cry"                    p = 5.119027429068207170981534787654339024047617892E-198
        [06] "gurgle"                 p = 1.249995096504862444380813956835806028869034507E-207
        [07] "grunt"                  p = 1.01739062013111986335494536436192167995756053108E-210
        [08] "ascending_moan"         p = 1.4216541141139552170219304467847061057190613971E-247
        [09] "ascending_shriek"       p = 5.439933974362827919518814555559505000467366257E-271
        [10] "bellow"                 p = 3.1508815888539594578441821091549124206657170000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000717194162392247812279864572622923819988677E-318
        [11] "modulated_cry"          p = 2.6195844256718123238144871459657140401568345892E-356
        [12] "groan"                  p = 1.0315737719820417839760863160393360875E-407
        [13] "purr"                   p = 1.7777025664515929428487529822218164332481732862397579825558105282994178E-488
        [14] "long_grunt"             p = 3.15848293833700076841294292138880532270930E-518
        [15] "descending_shriek"      p = 5.59268302829892981681586316141731409829445E-527
        [16] "croak"                  p = 1.4545933145953000905855503036310911056438511E-610
        [17] "BB"                     p = 9.6632244304575175611792862804826565E-684
        [18] "scream"                 p = 1.00000000000000000000000000000001163E-695
        [19] "tone"                   p = 1.00000000000000000000000000000000370E-695
        [20] "upsweep"                p = 1.00000000000000000000000000000000300E-695
        [21] "moan"                   p = 9.9999999999999999999999999999999843E-696
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999627E-696


      "modulated_moan"         sequence (T=241):
        [00] "cry"                    p = 1.51768785288235528515911655699967630394632763093494E-200
        [01] "groan"                  p = 1.54936041706729448789295788816166306608928123602265E-204
        [02] "gurgle"                 p = 1.51091549603122244312467887918694754875955502604251E-226
        [03] "descending_moan"        p = 1.03555257362391860876259270966891373759639849130E-228
        [04] "ascending_moan"         p = 1.46366056936022262806226442562899941769464582540E-235
      * [05] "modulated_moan"         p = 6.590885104177964883944835439117235134020224153275165214829E-248
        [06] "purr"                   p = 3.165788521124884542759785526624023975730891849213490630604526525317698033055337003651997E-248
        [07] "ascending_shriek"       p = 1.225167501365157368887283480081534122873048E-508
        [08] "grunt"                  p = 1.3423880764260183817018826356152513615613E-734
        [09] "grunts"                 p = 5.800233214891512703811086790489199527834E-811
        [10] "bellow"                 p = 1.63844256478094640170491111650153367190000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000122658726313090890249345708875248612E-824
        [11] "long_grunt"             p = 1.19240871729702937959859547459772056252E-851
        [12] "trill"                  p = 5.3384706964654055567420817218161281E-926
        [13] "scream"                 p = 4.049663220631961028827312094140675098591E-948
        [14] "tone"                   p = 3.7904335218424810743853167280885451607076515E-987
        [15] "descending_shriek"      p = 6.3496061391515241739603128340777458793E-1038
        [16] "croak"                  p = 1.88801324640066960739199927227156435E-1064
        [17] "growl"                  p = 3.66192012897332686254232523721791245E-1162
        [18] "modulated_cry"          p = 9.2440362196533734820454890283255983E-1192
        [19] "upsweep"                p = 1.00000000000000000000000000000000459E-1205
        [20] "moan"                   p = 9.9999999999999999999999999999999693E-1206
        [21] "BB"                     p = 9.9999999999999999999999999999999536E-1206
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999105E-1206


      "modulated_moan"         sequence (T=129):
        [00] "cry"                    p = 4.13641134596777445236096483776366970075580818E-113
        [01] "ascending_moan"         p = 2.117278475236902880160974012582556013388992E-123
        [02] "gurgle"                 p = 1.79113612010963286820457859328482716381625279E-138
        [03] "groan"                  p = 1.04231994078080042150226769985092477950271077E-138
        [04] "descending_moan"        p = 2.765471367928144378212180503010023529545993E-142
      * [05] "modulated_moan"         p = 3.1418575516122997261590382759175817230569531515868E-157
        [06] "purr"                   p = 1.3988845717173700416890376343002275522753040924194215931828694772882247059264596424E-174
        [07] "ascending_shriek"       p = 1.239378561970034915450141556356473008203086E-260
        [08] "grunt"                  p = 1.796342992900582275306736602065234988839825E-341
        [09] "trill"                  p = 1.24933493009090532486813972147586456667974E-349
        [10] "long_grunt"             p = 8.38929461534381802563791349912413769330647E-377
        [11] "bellow"                 p = 1.870982283578350816271755529749501147972500000460249981520581610630154607015668576562978984E-377
        [12] "grunts"                 p = 7.2740225886391899306553800551572777248183E-391
        [13] "scream"                 p = 6.74933023781987885053178609725474834347E-478
        [14] "croak"                  p = 8.2582540433041024485428366437524373895406E-481
        [15] "tone"                   p = 1.2879461978344703224286772297032750787E-587
        [16] "growl"                  p = 1.59159568790468103944133895966537090606117E-593
        [17] "descending_shriek"      p = 2.5103013005845338076426176702169793403E-608
        [18] "modulated_cry"          p = 1.553802311718158937929542540373360221772E-615
        [19] "BB"                     p = 1.23517592800447696134133329106818146E-643
        [20] "upsweep"                p = 1.00000000000000000000000000000000331E-645
        [21] "moan"                   p = 9.9999999999999999999999999999999907E-646
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999668E-646


      "modulated_moan"         sequence (T=101):
        [00] "cry"                    p = 5.02253524193656895872670273495112667919086621875E-78
        [01] "ascending_moan"         p = 1.248113757239570923226605182387037540416215244E-81
        [02] "groan"                  p = 1.206848125569876037639179702409737021471588335E-94
        [03] "gurgle"                 p = 3.82872510577415919700893860338791141182163950447E-107
        [04] "descending_moan"        p = 2.7842976730624544070020543356898798482069476029E-115
      * [05] "modulated_moan"         p = 7.66416914197841844365516530664493688964705658927967312E-144
        [06] "trill"                  p = 2.90635881357223719941294184257853372051E-229
        [07] "ascending_shriek"       p = 4.486367854387624639043612779801897441597737E-255
        [08] "long_grunt"             p = 1.356026904926450454549313390213067228479E-264
        [09] "grunt"                  p = 1.691622197961343612338164890678929879097E-266
        [10] "purr"                   p = 2.03680314927411233579129144768885797031742955580812155784941180470387174251235199429E-296
        [11] "bellow"                 p = 9.169237228905343674026311221789226843601339511337036145296218295044087871626669880E-387
        [12] "grunts"                 p = 9.3306236078408528845731601534085961314204747E-416
        [13] "scream"                 p = 1.284835759148865853299263091926601333135E-440
        [14] "croak"                  p = 3.040288103091893810111379888490439451396071E-442
        [15] "tone"                   p = 1.557064783251202560736111742014013786611E-466
        [16] "growl"                  p = 9.154048106210397699831103346426210404671E-477
        [17] "descending_shriek"      p = 4.09660896543822406917725458834892808E-480
        [18] "modulated_cry"          p = 1.36444824854257062759483792458484079804E-492
        [19] "BB"                     p = 1.51315805478792922802117707381001133E-503
        [20] "tone_w_slight_downsweep" p = 1.47149411272147650326043444671339381E-503
        [21] "upsweep"                p = 1.00000000000000000000000000000000313E-505
        [22] "moan"                   p = 9.9999999999999999999999999999999937E-506


      "modulated_moan"         sequence (T=86):
        [00] "gurgle"                 p = 1.592733425635229866154091642002280500594959579859207541E-27
        [01] "groan"                  p = 5.6103467427603815476023334834362823103954708239916854790E-30
      * [02] "modulated_moan"         p = 2.95513784362829950720829589684694631430947226747362549E-68
        [03] "trill"                  p = 8.552701121221940164474010114342826222810300963E-79
        [04] "cry"                    p = 2.1560696016104072405807815171817092840074614132387841944E-120
        [05] "bellow"                 p = 6.7175187202108014713533375827550646675820000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000005478718332387283515653120134611604148423946E-125
        [06] "grunt"                  p = 2.327256673233508368056246116268085467357546472614E-149
        [07] "descending_shriek"      p = 3.2315580460964441956391121118598399932388574E-156
        [08] "grunts"                 p = 2.1857305835006566991601735625427851268050805E-177
        [09] "purr"                   p = 4.46074204609661127370810698213748206265796233789370829227811999310367273E-191
        [10] "descending_moan"        p = 1.315593056830185437126247292026671695682598790E-191
        [11] "ascending_moan"         p = 4.0490104485163327055053242579729346174373852E-225
        [12] "long_grunt"             p = 1.16115374691312179759115416763551728E-421
        [13] "ascending_shriek"       p = 5.35962191457221898587027835279094584E-422
        [14] "croak"                  p = 1.5973698872334635241367596984652323136E-423
        [15] "scream"                 p = 1.00000000000000000000000000000000730E-430
        [16] "growl"                  p = 1.00000000000000000000000000000000320E-430
        [17] "upsweep"                p = 1.00000000000000000000000000000000289E-430
        [18] "tone"                   p = 1.00000000000000000000000000000000186E-430
        [19] "moan"                   p = 9.9999999999999999999999999999999897E-431
        [20] "BB"                     p = 9.9999999999999999999999999999999894E-431
        [21] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999887E-431
        [22] "modulated_cry"          p = 9.9999999999999999999999999999999504E-431


      "purr"                   sequence (T=99):
        [00] "grunt"                  p = 5.26347297500586871203112915219166223613188729046806E-136
        [01] "ascending_shriek"       p = 6.79687926428391174623349629880196799934451367E-138
        [02] "descending_moan"        p = 8.850915892515041247529599462315115017941036874E-140
      * [03] "purr"                   p = 3.87732969431528962646811349864798962149737495476451655121972943304407E-141
        [04] "cry"                    p = 1.73094867197422423651201353793839095712031123019991969555E-145
        [05] "grunts"                 p = 6.0303084291435036975703326166812323662423880983E-146
        [06] "ascending_moan"         p = 1.5341546790643497506307944885694098656877377267E-148
        [07] "trill"                  p = 2.1729942516334648400485606512513883378949688670E-166
        [08] "gurgle"                 p = 4.18088523639508921564943562015254920747041541763651E-170
        [09] "modulated_moan"         p = 1.1162080808692068824599574936436834446850122575462961063E-208
        [10] "scream"                 p = 2.623648422955440784804315726357406710003700917E-223
        [11] "groan"                  p = 1.4289691984221855071610669492479148830573E-316
        [12] "tone_w_slight_downsweep" p = 1.034426903045697884472662201831170741341E-372
        [13] "BB"                     p = 3.698684960124053331486903214407931816E-415
        [14] "tone"                   p = 5.0646413873401157893353395078837256886976E-417
        [15] "upsweep"                p = 6.36075480776817924725793028595501055E-422
        [16] "long_grunt"             p = 1.14720851179347402549354735222739905149E-428
        [17] "descending_shriek"      p = 3.73614619263226464896730998333374400E-451
        [18] "croak"                  p = 4.09406215817079441518676466801443063811E-456
        [19] "modulated_cry"          p = 3.18550330480628010262272639957119761504E-472
        [20] "bellow"                 p = 6.1417018014729459655530770183167321999999847E-487
        [21] "growl"                  p = 8.8968550750059518663297214487027014E-490
        [22] "moan"                   p = 9.9999999999999999999999999999999941E-496


      "purr"                   sequence (T=106):
        [00] "descending_moan"        p = 3.2904161024095283699908211268270081794288573E-194
        [01] "ascending_shriek"       p = 3.546279382232804748091358498841872168138858E-195
        [02] "grunts"                 p = 3.18049510692794276514788216244672247380124E-195
        [03] "cry"                    p = 4.620347580999132768305854985438129467082249552631053E-199
        [04] "grunt"                  p = 1.894349387032890302610440943558548077485248E-202
        [05] "ascending_moan"         p = 1.7277971260221629710108680658597169861942625E-204
      * [06] "purr"                   p = 8.6053317446238995846462707230952385891089015158831327741E-211
        [07] "trill"                  p = 3.51527960860984784907218240656630512112495070E-214
        [08] "gurgle"                 p = 6.89742010803882387695556269998175425518996264E-256
        [09] "modulated_moan"         p = 1.23622769893088724867274336509259705764434653E-290
        [10] "groan"                  p = 1.0060828198170748323071079685936571761303653680E-322
        [11] "scream"                 p = 7.80484094580756975862809484888397319825E-343
        [12] "descending_shriek"      p = 5.5996868756120173471684208178824338404704785E-395
        [13] "tone_w_slight_downsweep" p = 4.96815647766007135471783773384552812E-423
        [14] "growl"                  p = 1.959389803274235514172136832825333919070692460721E-449
        [15] "BB"                     p = 2.239015047493718788501411461748089245E-454
        [16] "long_grunt"             p = 2.7634982197175643069785219745117886762478E-463
        [17] "upsweep"                p = 2.55124782367672411931930846664157952E-463
        [18] "modulated_cry"          p = 1.53195495728253615957017666977688612E-475
        [19] "tone"                   p = 1.050215092947150917186425409014093699E-498
        [20] "croak"                  p = 4.69117246509481656811693470645341541E-507
        [21] "bellow"                 p = 7.13199521729082100486576426632783603700805800E-514
        [22] "moan"                   p = 9.9999999999999999999999999999999918E-531


      "purr"                   sequence (T=117):
        [00] "descending_moan"        p = 1.042174369972212870933031767606369427314232E-182
      * [01] "purr"                   p = 8.436187313546378290409949966562693150267508874609214337459544436686198E-185
        [02] "ascending_shriek"       p = 1.99660232926063606609173952253887792410887E-189
        [03] "grunts"                 p = 5.5369440945030906591549434623085943628358060E-200
        [04] "cry"                    p = 7.4252202262944584826190694520785554343063832797826701E-203
        [05] "grunt"                  p = 2.27388448359662675094160785955628731622666664682E-203
        [06] "gurgle"                 p = 3.917180353722166314271469476032661309990069569409E-225
        [07] "trill"                  p = 1.0111499431758168855916259405824520561020767E-225
        [08] "ascending_moan"         p = 1.70849662314073772925222018588042126888862E-242
        [09] "modulated_moan"         p = 3.953859635357564734185719889469962227417517099345383E-269
        [10] "groan"                  p = 2.24072039412045232105799974571127150655741E-320
        [11] "scream"                 p = 1.51454931231443960934453460083856593430681E-346
        [12] "tone_w_slight_downsweep" p = 5.3298293176924203311077383588632050083595E-464
        [13] "BB"                     p = 8.926260762919937998783164965018693575516486E-491
        [14] "upsweep"                p = 1.92714845524883959972019835357637769203349E-491
        [15] "descending_shriek"      p = 1.265768430990283329885338307360568983E-522
        [16] "long_grunt"             p = 1.01204532835611482651435498179117193E-522
        [17] "modulated_cry"          p = 3.6915081310921546034793500111856708940331E-529
        [18] "growl"                  p = 2.97712192953603614328878790294418754E-535
        [19] "tone"                   p = 1.82634958823778748421323897241425101944E-548
        [20] "croak"                  p = 7.0784112210548354930573227134869010E-569
        [21] "bellow"                 p = 5.705855454104353041364919771704322399999999999999818E-569
        [22] "moan"                   p = 4.53738829403395370079175116175150067268E-582


      "purr"                   sequence (T=140):
        [00] "gurgle"                 p = 8.51619544221080951953589651652942508006895873923157E-146
      * [01] "purr"                   p = 1.634903537828139853968548291864175629135120859750879770986484541514459120463572089433E-175
        [02] "groan"                  p = 1.02491224330076646601071048579936002935206409611632E-183
        [03] "modulated_moan"         p = 1.0653923187379252608025046443930621577867211413E-185
        [04] "grunt"                  p = 3.8814699087165803044022428984756949851412256E-232
        [05] "descending_moan"        p = 8.86217728670032405894004719973486501893617191E-242
        [06] "ascending_moan"         p = 1.88208218275329204241830791564240054124227717E-257
        [07] "grunts"                 p = 8.1029880291883713362503917667751335319719E-272
        [08] "descending_shriek"      p = 2.3921470878571446988514211533304319652184536E-324
        [09] "ascending_shriek"       p = 7.9257082235794769351228845339836027812454968E-336
        [10] "cry"                    p = 1.3238504413181329855563733960396461169742613951E-370
        [11] "trill"                  p = 1.84947477452354743773320154856113422974662373E-387
        [12] "growl"                  p = 1.26617057459867446891446442734604742863177381802385E-436
        [13] "long_grunt"             p = 4.491662153129572651899122759138865327592E-492
        [14] "BB"                     p = 4.7437986497565058166302043677423631E-509
        [15] "modulated_cry"          p = 1.05691637565137085727399898591192215169767E-525
        [16] "bellow"                 p = 5.22326323365485448357346963582088050000000000000000000000000000000000000000000000000000000000000000000000021344893737782673888131937315608134E-580
        [17] "scream"                 p = 1.07404845741135291624684522200190726027782E-605
        [18] "tone"                   p = 7.738414659657187698632408265234399219E-606
        [19] "tone_w_slight_downsweep" p = 1.26500088535374033740126168055425370E-609
        [20] "upsweep"                p = 3.5090229375998540171928596110025371E-625
        [21] "moan"                   p = 7.2362539566112980855824783662565459E-629
        [22] "croak"                  p = 9.9999999999999999999999999999999806E-701


      "purr"                   sequence (T=47):
        [00] "long_grunt"             p = 1.6516803867813405490863066470543204181918447E-69
      * [01] "purr"                   p = 6.0396698892586553377170221367734712130194506549600324799569099517189412429934940730E-73
        [02] "bellow"                 p = 5.71194060055831281965576461632040303086428200000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000101543053008866273636566492888538889552561E-73
        [03] "gurgle"                 p = 3.1602098499591998026382127834268352901141003105E-73
        [04] "groan"                  p = 1.546038773249729077313823583413973513885710319E-75
        [05] "trill"                  p = 1.66597704553405304721430648276929005049776997E-77
        [06] "croak"                  p = 6.9344636917815590293210054313004162611417522065E-83
        [07] "descending_moan"        p = 5.643950501234165822033940845469332118435946E-85
        [08] "grunt"                  p = 2.6586466740779849370196952548635608345351377876E-99
        [09] "ascending_shriek"       p = 2.372561338494736195988473370662619271793707E-109
        [10] "modulated_moan"         p = 6.8712693229056427175835703611499134696823246670023660E-119
        [11] "ascending_moan"         p = 4.23350648649655998648987529516133869039878808E-132
        [12] "cry"                    p = 6.717526371678417648624949361362599732715811887E-135
        [13] "descending_shriek"      p = 1.031676053097944018031848108534184199E-148
        [14] "grunts"                 p = 1.144436232373435505060965894061398169003E-207
        [15] "scream"                 p = 2.4226183375153718911854110292472604973E-212
        [16] "growl"                  p = 2.21562932470108536688701738379682984514E-232
        [17] "modulated_cry"          p = 8.048611758971673685181948185532574325E-234
        [18] "upsweep"                p = 1.00000000000000000000000000000000312E-235
        [19] "tone_w_slight_downsweep" p = 1.00000000000000000000000000000000071E-235
        [20] "tone"                   p = 1.00000000000000000000000000000000040E-235
        [21] "BB"                     p = 1.00000000000000000000000000000000007E-235
        [22] "moan"                   p = 9.9999999999999999999999999999999955E-236


      "purr"                   sequence (T=82):
        [00] "groan"                  p = 1.0505657828812748833000964494321685118791631040E-67
        [01] "cry"                    p = 1.59877484118813793893437692629213690030792006195E-69
        [02] "gurgle"                 p = 1.3432108331079352236054586483675613720709019592926E-70
      * [03] "purr"                   p = 2.1959204251196594922402984753446891375460430553010450762782459253483879567413752227114E-78
        [04] "ascending_moan"         p = 3.39721924092019723970957958811100201294223358891E-80
        [05] "descending_moan"        p = 3.131132609912012058474479512515280578430005199647E-80
        [06] "modulated_moan"         p = 6.367340681193196947849624167558788984367749677372449106067E-83
        [07] "ascending_shriek"       p = 2.952204609571208457947803707337583528296669E-170
        [08] "grunt"                  p = 2.273317007820864446252411175927716680530E-262
        [09] "grunts"                 p = 5.7419658586950201129481683243646422322717225E-276
        [10] "long_grunt"             p = 1.857686998748384492984316037921132746250E-283
        [11] "bellow"                 p = 1.259440469682638751180749639260255538934070000000030170526438154262772722532130352459087624929E-316
        [12] "scream"                 p = 1.138157795427139119183136433576958970811E-344
        [13] "tone"                   p = 6.901585048834917440651840650586789928221E-345
        [14] "trill"                  p = 6.212016880110351943023064462012866412609E-345
        [15] "croak"                  p = 1.5432950386312557489100675554409175494987322E-367
        [16] "descending_shriek"      p = 8.8172186699172621180524387119922693E-371
        [17] "growl"                  p = 9.9998572469046246167336155105118425118E-404
        [18] "modulated_cry"          p = 2.605056346606971666933124184986458021E-406
        [19] "upsweep"                p = 1.00000000000000000000000000000000304E-410
        [20] "BB"                     p = 9.9999999999999999999999999999999919E-411
        [21] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999887E-411
        [22] "moan"                   p = 9.9999999999999999999999999999999875E-411


      "purr"                   sequence (T=301):
        [00] "cry"                    p = 4.0140502533407123937235677389890445714454290460E-272
        [01] "groan"                  p = 1.4968817215699607105074629159345682354098062908E-282
        [02] "gurgle"                 p = 1.145090560238628145031430439940507646925358877138E-315
        [03] "ascending_moan"         p = 4.872759985285937521160523354914144344957778391E-322
        [04] "descending_moan"        p = 3.763285346112933076368714155913891667458414845E-344
        [05] "modulated_moan"         p = 2.25870727228101062444909447480679962770776666856643287E-347
      * [06] "purr"                   p = 3.2557549727237521877208289773192248491516653686572082640175063002974399862128717873117137E-351
        [07] "ascending_shriek"       p = 1.27766571083973166413060184357785541605041E-612
        [08] "grunts"                 p = 3.1374861572950391388673256691790552627377961E-783
        [09] "grunt"                  p = 2.70655267143790461247822057572329142595829E-803
        [10] "long_grunt"             p = 3.44235114481374113624995709656911115E-895
        [11] "bellow"                 p = 2.8372880252677408129444496462036239441620000000000000000000000000000000000000000000000000000000000000000000000000000332350144490753382067618692961496742915018344E-1024
        [12] "trill"                  p = 3.059739389639734126371954286263139582001E-1125
        [13] "scream"                 p = 5.34259374692544143014837587496568619E-1157
        [14] "croak"                  p = 6.585906234810373168171673210133215926E-1271
        [15] "descending_shriek"      p = 1.5396059853389128169798487783979871865214E-1302
        [16] "growl"                  p = 7.560160506292808039420911665207165769893523E-1390
        [17] "tone"                   p = 3.5004160038898119032281925288457864100E-1421
        [18] "modulated_cry"          p = 1.92156852604696344466021186121818677818343E-1443
        [19] "BB"                     p = 1.47770112840751047660577632232545229E-1503
        [20] "upsweep"                p = 1.00000000000000000000000000000000535E-1505
        [21] "moan"                   p = 9.9999999999999999999999999999999692E-1506
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999998844E-1506


      "grunt"                  sequence (T=75):
        [00] "grunts"                 p = 4.017604377885543444770192962448290969825902369695398449219274E-86
      * [01] "grunt"                  p = 2.6361465210957085880376632174898713362166594579909501E-92
        [02] "modulated_moan"         p = 1.325494136940090556190941775916942476186522481200589E-92
        [03] "growl"                  p = 2.56101088628736319223273230882943024719731325743298590153E-100
        [04] "trill"                  p = 1.20287496323125591712237500493719541873708603700488E-110
        [05] "gurgle"                 p = 1.81515422171904306987563284379343058218847799E-120
        [06] "descending_moan"        p = 1.0891787489453892851818098390394804270759282683E-120
        [07] "cry"                    p = 6.935938944458348812464157475731465635773309172028711E-122
        [08] "ascending_shriek"       p = 4.14976210867781816579015584350154674197983512E-133
        [09] "modulated_cry"          p = 1.60740288259459411490712622909551308313597326421E-147
        [10] "ascending_moan"         p = 3.054808498094696056917475326724623460195136445329E-159
        [11] "bellow"                 p = 1.96216198038803495033093233427383451058235341600000000000000000000000000000001426628862780693817714358105281904411244340E-178
        [12] "descending_shriek"      p = 1.30644798927898428722964121104966470E-226
        [13] "groan"                  p = 1.65056346433901841798693299362867867E-228
        [14] "purr"                   p = 1.472749778912815122574495808373015636805301510948070E-249
        [15] "croak"                  p = 1.06140427065872647649404048909802880315575177E-319
        [16] "long_grunt"             p = 9.2972565328674658347365310020579169E-325
        [17] "BB"                     p = 1.87187980499484489308137480511462651E-363
        [18] "scream"                 p = 1.37141227100382790556805913882579345E-373
        [19] "upsweep"                p = 1.00000000000000000000000000000000276E-375
        [20] "tone"                   p = 1.00000000000000000000000000000000108E-375
        [21] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999924E-376
        [22] "moan"                   p = 9.9999999999999999999999999999999892E-376


      "grunt"                  sequence (T=56):
        [00] "grunts"                 p = 8.215472099499200897239044361574123862960881484440908467154557E-50
        [01] "growl"                  p = 7.44489529264245421378845687751146624993957930576603910888E-52
      * [02] "grunt"                  p = 1.1024361309121522437075548805323365485970016452640030E-55
        [03] "modulated_moan"         p = 3.174068698708693627644325454400540723938862501044335E-60
        [04] "modulated_cry"          p = 1.23781013355273168593919590542033708747448513603E-61
        [05] "cry"                    p = 1.404258047808151131466442524186884544502329494609995E-76
        [06] "trill"                  p = 8.7025636306584439128906029091048705402839518774029E-81
        [07] "descending_moan"        p = 2.0594055596472521434829750584848399548189080717E-85
        [08] "gurgle"                 p = 1.06567088492143499389880451401660426737343813E-91
        [09] "ascending_shriek"       p = 7.56456019469670743881699015131290455176228712E-93
        [10] "ascending_moan"         p = 3.752585015966426714555099473252411306187196583210E-98
        [11] "bellow"                 p = 2.026229564954014259230494517681868868835208576000883182099645382910781111170182457799801613E-104
        [12] "purr"                   p = 1.6797454038554732076091847121462579121227253626492921086E-181
        [13] "groan"                  p = 9.26004107407484152777087500561810297E-192
        [14] "descending_shriek"      p = 8.2717139084120422561713924847650292E-213
        [15] "croak"                  p = 6.7678393937635183800725802355400239043415484E-224
        [16] "long_grunt"             p = 3.5047191544492229617194000802203831E-243
        [17] "scream"                 p = 1.00000000000000000000000000000000583E-280
        [18] "upsweep"                p = 1.00000000000000000000000000000000284E-280
        [19] "tone"                   p = 1.00000000000000000000000000000000038E-280
        [20] "tone_w_slight_downsweep" p = 1.00000000000000000000000000000000022E-280
        [21] "BB"                     p = 9.9999999999999999999999999999999977E-281
        [22] "moan"                   p = 9.9999999999999999999999999999999950E-281


      "grunt"                  sequence (T=64):
        [00] "growl"                  p = 4.38955326197020479052612430325582685527046143911705811317E-65
        [01] "grunts"                 p = 2.904743512205058103788510906815637152032014782936484679238011E-66
      * [02] "grunt"                  p = 4.4488368346227326473894343876353576652705021629974265E-67
        [03] "modulated_moan"         p = 5.534202447294370775325925805969439629515635331895034E-73
        [04] "modulated_cry"          p = 3.22464053979589389412387952614896619810382388225E-77
        [05] "cry"                    p = 1.151431490839690464372140681129025514668664786507626E-86
        [06] "gurgle"                 p = 6.62706211337760885126813201177963633648969957E-93
        [07] "descending_moan"        p = 1.3197586705598790407804305020218079466303861888E-95
        [08] "trill"                  p = 1.62017886869196988133142322486337333322478000156118E-97
        [09] "ascending_shriek"       p = 1.305883771839479593989871452148681859093673598E-97
        [10] "ascending_moan"         p = 1.4285525811461868597911295663028375525714832026344E-119
        [11] "bellow"                 p = 2.488912266010503742932205886786687094220481323001089181919073846819772696076722405611987851E-150
        [12] "purr"                   p = 1.3044085180125544543053983593055065182142200294E-210
        [13] "descending_shriek"      p = 2.14421646672974314233558743168030020E-212
        [14] "groan"                  p = 8.82418383083233613369134378377957259E-219
        [15] "croak"                  p = 2.13458430590838435909594038990210284087202941E-261
        [16] "long_grunt"             p = 4.3433653106054423264574172576417907E-289
        [17] "BB"                     p = 1.92893745479103446945395964322735988E-316
        [18] "scream"                 p = 6.2173776046409195661470004867819736E-319
        [19] "upsweep"                p = 1.00000000000000000000000000000000254E-320
        [20] "tone"                   p = 1.00000000000000000000000000000000104E-320
        [21] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999982E-321
        [22] "moan"                   p = 9.9999999999999999999999999999999917E-321


      "grunt"                  sequence (T=83):
        [00] "modulated_moan"         p = 2.031610988409613455965724349144307216796034755312E-93
      * [01] "grunt"                  p = 1.3388529065102243503522725089652476974288497802363131E-93
        [02] "grunts"                 p = 3.90522617637761194053540651180017150512405360434208028E-96
        [03] "growl"                  p = 6.7357996903060137759114694431920666632660157832071376022E-104
        [04] "cry"                    p = 9.209680411428191856956193465104955941844185133365E-110
        [05] "trill"                  p = 9.689237295652851385340582150465414653805885070183862E-111
        [06] "descending_moan"        p = 1.4466528740809794442518056009786557050632375830E-119
        [07] "gurgle"                 p = 2.101092789399328980849463147756425662016198339E-121
        [08] "ascending_shriek"       p = 4.17450544728459694088797594288556722619715335E-149
        [09] "ascending_moan"         p = 5.953178030757523731165905355811575870503938754E-154
        [10] "modulated_cry"          p = 2.156318504896282848323912994182957417385275108005E-157
        [11] "bellow"                 p = 3.508878901265203450493270365845465354931900000076111966402019302169355894939959300E-232
        [12] "groan"                  p = 1.1295173038589774187095999062400907605E-260
        [13] "descending_shriek"      p = 1.507094771957471009086848781272879638976216E-272
        [14] "purr"                   p = 1.3956792363048225946750371144685015498655405765756E-279
        [15] "long_grunt"             p = 5.2233652864457321154351510452052084E-365
        [16] "croak"                  p = 6.162650007808738863335200701618776620289107E-368
        [17] "BB"                     p = 2.03487294615879513551815663271380746E-398
        [18] "upsweep"                p = 3.4452350862361018133676140925535582E-413
        [19] "tone_w_slight_downsweep" p = 2.9705705041784788555437966379663357E-413
        [20] "moan"                   p = 1.07237031168652683254609962201315576E-413
        [21] "tone"                   p = 6.78632820682482256595990898132102777E-414
        [22] "scream"                 p = 1.00000000000000000000000000000000716E-415


      "grunt"                  sequence (T=550):
        [00] "ascending_moan"         p = 1.71696497085101787385387572050379934158900418E-538
        [01] "descending_moan"        p = 2.22655871868849157594655675029513970835699828E-545
      * [02] "grunt"                  p = 4.83618617810320190625650152494341153829825E-600
        [03] "gurgle"                 p = 5.50232419685628848087975048459964189361388225345275E-665
        [04] "ascending_shriek"       p = 1.37754009824083867201841857229023351230E-901
        [05] "modulated_moan"         p = 3.2038202646201232819279688426609644876252541695E-913
        [06] "purr"                   p = 4.83419137227302859675835899256846263711400003731213233160899473206351509022097588126534E-1115
        [07] "grunts"                 p = 2.70599450294662805192169920114834150933355141E-1254
        [08] "growl"                  p = 1.93807394589558377536907958174825608224E-1263
        [09] "groan"                  p = 4.51531055108270282523861437129320328830542E-1298
        [10] "cry"                    p = 1.33290730903185073910981455316636491902521715E-1310
        [11] "descending_shriek"      p = 1.72913907175510312512507944444856270185746E-1557
        [12] "trill"                  p = 9.79851320052619017425976735159224030876E-1622
        [13] "BB"                     p = 8.905379089579476639479894189908299957936E-2098
        [14] "long_grunt"             p = 5.17663217186688383871064457040784871E-2128
        [15] "modulated_cry"          p = 6.2520476047471074483474245804257740E-2222
        [16] "bellow"                 p = 1.65467416349662418122356476069139465664367747240714698580583192E-2439
        [17] "scream"                 p = 4.3132304191546044555469373084087444E-2485
        [18] "tone_w_slight_downsweep" p = 3.1242454162344942948023474083922932917211E-2597
        [19] "moan"                   p = 1.818143350473881483988255698153131142050E-2636
        [20] "upsweep"                p = 1.8188902604640379787568629321324038420816149E-2637
        [21] "tone"                   p = 3.58911367824088159645109453904421123601E-2740
        [22] "croak"                  p = 1.14103958193255414594939858860528312E-2748


      "grunt"                  sequence (T=47):
        [00] "croak"                  p = 1.304771302766300381443467621234892629223323303E-71
      * [01] "grunt"                  p = 6.8404414202677734393480909819080278751432257194E-76
        [02] "descending_shriek"      p = 5.7985928836964104253416351381588244370757827E-79
        [03] "trill"                  p = 5.243796586622448184986625755749216234042035E-79
        [04] "descending_moan"        p = 9.4160827696873767515044994967317136418724050823E-81
        [05] "gurgle"                 p = 3.013227797181286397489171362719495728188071384E-82
        [06] "purr"                   p = 1.766674649521032381650909202646338053559782137518886900997608621712163003E-85
        [07] "bellow"                 p = 7.037079421196799814333074056483465973811674000000000000000000000000000000000000000000000000000000000000000000000000000000000000000195580805737304780032056327850852194E-88
        [08] "ascending_shriek"       p = 2.6094606817399401574733431230901882310264638E-94
        [09] "groan"                  p = 3.0630778887943302364967678371302755051410858216497E-97
        [10] "long_grunt"             p = 1.19924884351652433750936644516517450226015E-102
        [11] "ascending_moan"         p = 5.97237606687028685453496471303223933007393740E-118
        [12] "cry"                    p = 2.65130375798133932239739811382402789591290538479E-132
        [13] "modulated_moan"         p = 2.378509060961342581886742902404548614206E-193
        [14] "grunts"                 p = 4.7912619572860713633023821745445959885E-194
        [15] "modulated_cry"          p = 1.227775239921292048450967681361893112E-220
        [16] "scream"                 p = 5.9367041746710856492088661792303360E-221
        [17] "growl"                  p = 8.731982437106913897601735525141480172E-225
        [18] "BB"                     p = 2.136953001741583506492544615072128905E-232
        [19] "upsweep"                p = 1.00000000000000000000000000000000312E-235
        [20] "tone_w_slight_downsweep" p = 1.00000000000000000000000000000000071E-235
        [21] "tone"                   p = 1.00000000000000000000000000000000040E-235
        [22] "moan"                   p = 9.9999999999999999999999999999999955E-236


      "grunt"                  sequence (T=43):
        [00] "groan"                  p = 5.0703556879422378932010260866251031192637075932643E-71
        [01] "long_grunt"             p = 1.15901582925521257927911211192635142274710E-72
        [02] "trill"                  p = 3.168012587901534761444796742859280974601063E-73
        [03] "gurgle"                 p = 7.65022888672474412834857054671983120808736870871E-75
        [04] "bellow"                 p = 2.101802407262556484347361498916912283617000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000019604094227261851244045403494913261E-75
        [05] "croak"                  p = 2.05696088444198720887653597464956721867012798E-75
        [06] "descending_moan"        p = 2.383143329300426059410380811892126803129080953E-84
        [07] "purr"                   p = 3.6164238729103479065679040162921917618411029704678085545585326370753743E-93
      * [08] "grunt"                  p = 1.02751686476345385967370975469407487230413053938E-95
        [09] "descending_shriek"      p = 5.878586827244700929292115291672787932094E-97
        [10] "ascending_shriek"       p = 1.62499917221355413118175263974046813447543907E-97
        [11] "cry"                    p = 4.972738711449755416893986313186226868465038842601E-127
        [12] "modulated_moan"         p = 9.008852806676656351047230135221626947899201217E-147
        [13] "ascending_moan"         p = 2.96713785022858612550737638824528660E-148
        [14] "grunts"                 p = 1.09162254814293921371448093319065752E-211
        [15] "modulated_cry"          p = 6.95585414130146665464513619264133521E-212
        [16] "growl"                  p = 4.3694734130732483136134434378961610E-212
        [17] "scream"                 p = 1.00000000000000000000000000000000493E-215
        [18] "upsweep"                p = 1.00000000000000000000000000000000316E-215
        [19] "tone_w_slight_downsweep" p = 1.00000000000000000000000000000000107E-215
        [20] "BB"                     p = 1.00000000000000000000000000000000020E-215
        [21] "tone"                   p = 1.00000000000000000000000000000000013E-215
        [22] "moan"                   p = 9.9999999999999999999999999999999972E-216


      "grunt"                  sequence (T=63):
        [00] "tone_w_slight_downsweep" p = 1.156168568533881359649846355991830301E-121
        [01] "BB"                     p = 1.44311739799495781332976247141503197280315560E-136
        [02] "tone"                   p = 5.28803308166560078138517357820936684158E-147
      * [03] "grunt"                  p = 2.71801663477992092479867056072718216578123859E-147
        [04] "moan"                   p = 9.747982226468226127458902104316600259E-166
        [05] "upsweep"                p = 3.12988258061366664902800231865123521321E-167
        [06] "grunts"                 p = 1.1346134957566895609652351865148918952272112836550E-193
        [07] "gurgle"                 p = 4.390853227034575889587887011294225378807077703030E-234
        [08] "trill"                  p = 5.36982301634300192345902571293509612758129E-237
        [09] "descending_moan"        p = 5.99018217523355909028193441193467151229805E-240
        [10] "ascending_shriek"       p = 3.34892451765283616000506446982277409536010E-241
        [11] "cry"                    p = 5.06767643421764059735120860858374343460580823E-259
        [12] "modulated_moan"         p = 1.2782405444069182208317414513586087824305667029E-263
        [13] "long_grunt"             p = 1.4527629265125463721173025381316988633873E-269
        [14] "purr"                   p = 2.99652539936286583570476302119185700847149162693187825681583373066448E-271
        [15] "groan"                  p = 2.54097052663031321802263401205599434866945571511E-274
        [16] "ascending_moan"         p = 1.613959866413231059693056483954591913800E-274
        [17] "descending_shriek"      p = 1.13189977137415202986828303908523151168828E-297
        [18] "growl"                  p = 4.696845578489360434862554677471024288693E-311
        [19] "scream"                 p = 1.00000000000000000000000000000000639E-315
        [20] "croak"                  p = 9.9999999999999999999999999999999990E-316
        [21] "bellow"                 p = 9.9999999999999999999999999999999886E-316
        [22] "modulated_cry"          p = 9.9999999999999999999999999999999580E-316


      "BB"                     sequence (T=48):
        [00] "upsweep"                p = 1.4560788031818068483458471029880260459679128932416E-92
      * [01] "BB"                     p = 3.2614695184274714851039209340227103114118178E-98
        [02] "tone"                   p = 1.06093261940558268625247305366154585888536E-136
        [03] "tone_w_slight_downsweep" p = 2.548204230312134095871519140841232758214E-140
        [04] "grunt"                  p = 2.1904266496600792249742460693698846450059088449E-145
        [05] "grunts"                 p = 1.32112368634028039822853559616083795401274457586E-149
        [06] "moan"                   p = 7.126407491054257423949160494531253676502E-171
        [07] "trill"                  p = 1.1895518471697897117794385814291091901838E-174
        [08] "gurgle"                 p = 7.15619139844898963609498539844675237010760370797E-198
        [09] "ascending_shriek"       p = 6.405797061205362312816226596895805254E-199
        [10] "modulated_moan"         p = 9.320513770099274652312849218278506494049E-205
        [11] "ascending_moan"         p = 3.4073271874537081756052506498401470778E-208
        [12] "purr"                   p = 9.8244996594447694210044652154619002746674965130148477787030130E-211
        [13] "descending_moan"        p = 1.156203946821745196496403971750217902344E-211
        [14] "groan"                  p = 1.6125369372776786658904728279327158958154E-215
        [15] "cry"                    p = 9.0082849001475218686877209338718035594E-224
        [16] "scream"                 p = 1.39997933890734702480248436181355357E-236
        [17] "long_grunt"             p = 1.037962225397411216873371125545941910E-236
        [18] "descending_shriek"      p = 9.6406243190782589908148326919607691E-239
        [19] "growl"                  p = 1.00000000000000000000000000000000120E-240
        [20] "croak"                  p = 1.00000000000000000000000000000000011E-240
        [21] "bellow"                 p = 9.9999999999999999999999999999999894E-241
        [22] "modulated_cry"          p = 9.9999999999999999999999999999999622E-241


      "gurgle"                 sequence (T=24):
        [00] "modulated_cry"          p = 5.5921596906668528687031236595501380258057095358E-22
        [01] "grunts"                 p = 2.02490332863600167627117458983409641281824747015223341472115E-27
        [02] "ascending_moan"         p = 2.5347604271992627501334494216834033330699331779E-28
      * [03] "gurgle"                 p = 1.883560458633006916627765805060957055203435974E-28
        [04] "cry"                    p = 1.766041595281393255231966311486007586681943342623521E-32
        [05] "modulated_moan"         p = 5.465873102088775867638754202147399890988428117501E-33
        [06] "ascending_shriek"       p = 8.27342336406096461884795518006186047429628934E-35
        [07] "growl"                  p = 5.7850635274651749389324505413345942223178994372141995873E-35
        [08] "bellow"                 p = 1.9452050271583037470904372350463938202173044500000814751533224886288342300487186590552230E-35
        [09] "grunt"                  p = 6.00896245693014939753714292380407161753942132700092E-36
        [10] "descending_moan"        p = 1.088828762181134348219574290795257544741799284E-43
        [11] "trill"                  p = 4.47062805409012533963513759394043409000558653190346E-48
        [12] "croak"                  p = 6.65597196067481070543526711709813244252E-68
        [13] "purr"                   p = 3.03502763464306522961568528905905951354220130214810643963529427E-91
        [14] "descending_shriek"      p = 1.060374399342834519720417107965183697E-94
        [15] "long_grunt"             p = 2.29696154822921289735516741885126910E-118
        [16] "groan"                  p = 1.063434472484623189164444118438726557E-118
        [17] "scream"                 p = 1.00000000000000000000000000000000347E-120
        [18] "upsweep"                p = 1.00000000000000000000000000000000301E-120
        [19] "tone_w_slight_downsweep" p = 1.00000000000000000000000000000000237E-120
        [20] "BB"                     p = 1.00000000000000000000000000000000090E-120
        [21] "moan"                   p = 9.9999999999999999999999999999999993E-121
        [22] "tone"                   p = 9.9999999999999999999999999999999931E-121


      "gurgle"                 sequence (T=19):
        [00] "ascending_shriek"       p = 1.58209237816981247845135014111496456056924E-37
      * [01] "gurgle"                 p = 1.782133384808323501228437385980418674316E-41
        [02] "ascending_moan"         p = 7.81155671017108005188679617401081829275E-42
        [03] "descending_moan"        p = 6.5077655780499143115835941734322291305281E-47
        [04] "grunt"                  p = 2.20029304891715773362225361625726864054E-57
        [05] "growl"                  p = 2.439037039382042034219520385934205902E-59
        [06] "cry"                    p = 1.88195863671069810503523357125843478227711E-59
        [07] "modulated_moan"         p = 1.40018732354754242388401224319700266340E-63
        [08] "descending_shriek"      p = 1.64902746836862745884007617557312729948408E-68
        [09] "modulated_cry"          p = 3.7407499661916821545640505224264289422067E-72
        [10] "trill"                  p = 3.19219634096045953284207858714363208136E-73
        [11] "purr"                   p = 1.7089088712782651486387437797689365324763993239890290E-77
        [12] "croak"                  p = 1.04065525418142237562106471573841647163E-77
        [13] "grunts"                 p = 3.40870371825143055602972338132825974685214E-81
        [14] "groan"                  p = 5.391610131363962531196046032716783904E-86
        [15] "upsweep"                p = 8.6590635970703533766466088914631903E-88
        [16] "BB"                     p = 5.517562512516609641201062761380504324083E-89
        [17] "bellow"                 p = 2.46742878467655400364355109454586148415043E-89
        [18] "long_grunt"             p = 1.315804227913511965241043994295016437E-90
        [19] "scream"                 p = 1.00000000000000000000000000000000298E-95
        [20] "tone_w_slight_downsweep" p = 1.00000000000000000000000000000000272E-95
        [21] "moan"                   p = 9.9999999999999999999999999999999987E-96
        [22] "tone"                   p = 9.9999999999999999999999999999999912E-96


      "gurgle"                 sequence (T=24):
        [00] "ascending_moan"         p = 2.04126426254855501538865110058482460870326477E-28
      * [01] "gurgle"                 p = 3.239716514570132163138285845636375184589777958E-29
        [02] "cry"                    p = 2.29879948597261198956945034191498563775757075241E-32
        [03] "modulated_cry"          p = 2.597424290914968629911647110109110820074062989E-34
        [04] "ascending_shriek"       p = 4.21011999834482539389163017128624314864158E-40
        [05] "grunts"                 p = 1.166926186099185776423187802383870573447874053362886700925E-40
        [06] "growl"                  p = 4.3317856712993788835090267271774735476398741E-42
        [07] "descending_moan"        p = 4.11073105975473829967502293484702015603788003E-43
        [08] "modulated_moan"         p = 4.3018670754484838000188639451766123939231118858475E-55
        [09] "grunt"                  p = 6.62058771827104292212694430948677217424284876E-57
        [10] "trill"                  p = 7.00545708961828943050342805079570811616001162E-58
        [11] "bellow"                 p = 3.5454300409159941472314442335722456406114654710984834482444534E-85
        [12] "croak"                  p = 2.220612732877320832142494009672828681363E-95
        [13] "descending_shriek"      p = 3.501763341992584858763081998578120442697E-102
        [14] "purr"                   p = 1.5955975339360113450036997783845817841881E-109
        [15] "groan"                  p = 3.206093422964678196778947570603943724E-116
        [16] "long_grunt"             p = 1.7247605756016943115791614786705394438E-118
        [17] "scream"                 p = 1.00000000000000000000000000000000347E-120
        [18] "upsweep"                p = 1.00000000000000000000000000000000301E-120
        [19] "tone_w_slight_downsweep" p = 1.00000000000000000000000000000000237E-120
        [20] "BB"                     p = 1.00000000000000000000000000000000090E-120
        [21] "moan"                   p = 9.9999999999999999999999999999999993E-121
        [22] "tone"                   p = 9.9999999999999999999999999999999931E-121


      "gurgle"                 sequence (T=20):
        [00] "ascending_moan"         p = 5.685460058706765897460981063192666198232552604E-23
      * [01] "gurgle"                 p = 6.988626011710512954967354007911936998578658E-25
        [02] "growl"                  p = 7.542814225342593906205314910817439058817749334795195E-27
        [03] "cry"                    p = 9.12067919881734366180942956859430170213805123717E-30
        [04] "ascending_shriek"       p = 4.2771916643631896186566313403847413431858195E-31
        [05] "descending_moan"        p = 1.82521511382259907207653387342146167297909330E-33
        [06] "modulated_moan"         p = 2.814359470075944401418045647701666711330567620630E-36
        [07] "modulated_cry"          p = 6.8900388531723674354871087596325185676799013E-38
        [08] "grunts"                 p = 6.12856907022307590735071974105099179226749591563930153838670E-38
        [09] "grunt"                  p = 6.289153900915411408933341599923255847001644E-49
        [10] "trill"                  p = 4.0965148250541316596728235564973184189373686E-59
        [11] "bellow"                 p = 1.1292400413456694443998680965766701509099053141511712513E-68
        [12] "croak"                  p = 4.3151110964167521666287294271695473101220E-81
        [13] "purr"                   p = 5.9327316596589702324126381036821111863099537418E-85
        [14] "upsweep"                p = 9.4565174460451427533313714072663582E-90
        [15] "descending_shriek"      p = 1.828576008378206192792522627532176678E-93
        [16] "groan"                  p = 2.29556419161054423465285786763442854128E-96
        [17] "scream"                 p = 1.00000000000000000000000000000000314E-100
        [18] "tone_w_slight_downsweep" p = 1.00000000000000000000000000000000270E-100
        [19] "BB"                     p = 1.00000000000000000000000000000000089E-100
        [20] "moan"                   p = 9.9999999999999999999999999999999985E-101
        [21] "tone"                   p = 9.9999999999999999999999999999999906E-101
        [22] "long_grunt"             p = 9.9999999999999999999999999999999791E-101


      "gurgle"                 sequence (T=56):
        [00] "cry"                    p = 2.76014470812469867012182364215978798545377898560417E-88
      * [01] "gurgle"                 p = 2.614336766518535588207934241730259925547152893E-88
        [02] "modulated_moan"         p = 1.844006695683929163334781403663093271884121721839E-88
        [03] "growl"                  p = 3.4331669443504860069697863616935164405406077000721134233E-90
        [04] "ascending_moan"         p = 3.6085500807243546779329507822269416400301930688E-96
        [05] "grunt"                  p = 5.42194487934576643423716151585484495211599457116004E-97
        [06] "descending_moan"        p = 2.0569946195968779266560991357663963279934687080E-102
        [07] "trill"                  p = 2.50315162103751135439723124798756829915642425644348E-114
        [08] "grunts"                 p = 8.5352520035483815364068230468497461694484832820739685831349E-115
        [09] "ascending_shriek"       p = 1.7721229328522045004038904547586923380687802E-117
        [10] "modulated_cry"          p = 8.178067146581382232869217900834389539633428303E-142
        [11] "bellow"                 p = 2.765286849551644251245959373357566307113600000000000316670771447640695341706554324118399785024E-159
        [12] "purr"                   p = 6.6605387881761160568445847969486532413135278643704830267747351E-180
        [13] "descending_shriek"      p = 1.62023944345901707458634841512152140542E-197
        [14] "groan"                  p = 8.91209760395968963909583806350146138E-206
        [15] "croak"                  p = 2.2748920596770365655985241715068978193206E-241
        [16] "long_grunt"             p = 2.6800918088565429902108437587569927897E-246
        [17] "BB"                     p = 8.5267183359900988508392904339741785E-254
        [18] "tone_w_slight_downsweep" p = 3.7233038884540999926827504447835840E-258
        [19] "upsweep"                p = 1.18885060614877301317772130816986423E-274
        [20] "moan"                   p = 1.10643595808967261957939305296232592E-278
        [21] "scream"                 p = 7.4581505166644921772482427335397825E-279
        [22] "tone"                   p = 6.89133338348440514187362284788302875E-279


      "gurgle"                 sequence (T=45):
        [00] "grunts"                 p = 4.012064084746039801307320305015406640660695912E-72
        [01] "ascending_shriek"       p = 6.3476562484219617465716282242455937655604574E-83
        [02] "grunt"                  p = 1.410125669455787420486149688146754627320839819509E-83
      * [03] "gurgle"                 p = 1.0184786659565003972529740992748943202875624660486E-86
        [04] "ascending_moan"         p = 2.073261892460249391273822984356058721017399556E-95
        [05] "descending_moan"        p = 1.162997779407392655922075922289574375235204995E-98
        [06] "purr"                   p = 5.1839880215095832306155047211458296461968132569155469796126913501417275E-103
        [07] "cry"                    p = 2.369378491030089673011754552078523833112945856910261923E-108
        [08] "trill"                  p = 2.896937805817673417632422350907199700756662720E-112
        [09] "modulated_moan"         p = 5.15254070881586703379899330552903317359449640081452E-119
        [10] "scream"                 p = 2.0988555973959099946857911019795363304501110E-131
        [11] "groan"                  p = 3.91397637343925464523201736026239538502E-166
        [12] "tone"                   p = 4.2879743962441110456045797318767984E-180
        [13] "descending_shriek"      p = 4.2361999338752764518891563523580899191382E-180
        [14] "tone_w_slight_downsweep" p = 6.2844946635566402494809127969555088E-186
        [15] "upsweep"                p = 2.57016354701372514868624849101499209E-187
        [16] "BB"                     p = 6.43775576377500965211161650205597881E-192
        [17] "moan"                   p = 4.01470117241964252281291217902947868E-206
        [18] "long_grunt"             p = 1.03082352644543049212771942764647053E-218
        [19] "modulated_cry"          p = 4.77985972904203271848310184624382792E-220
        [20] "bellow"                 p = 4.461991719938373354107517887344690111E-221
        [21] "growl"                  p = 5.56348701639586751969308503252941813E-222
        [22] "croak"                  p = 5.5741539482328518658679685822117775E-224


      "gurgle"                 sequence (T=55):
        [00] "grunts"                 p = 5.615683430897902656950295562466991212022916124E-68
        [01] "grunt"                  p = 1.28077562791958097203953202451886827754074444233867E-94
        [02] "descending_moan"        p = 7.54381862647926253541856636446359280417598225E-100
        [03] "ascending_shriek"       p = 3.599444332502278816718825676594760688370615E-102
        [04] "cry"                    p = 3.331629602440050428366739319801067530457102249022139931E-104
        [05] "purr"                   p = 6.644076626358425726779675624795933880279172486146780699673781950326E-106
        [06] "trill"                  p = 4.710212304799002091885402935415328785937695051E-107
        [07] "ascending_moan"         p = 3.5182154964127517286677881186269075864347192E-113
      * [08] "gurgle"                 p = 3.280161888664383704855366516673486770497471E-119
        [09] "scream"                 p = 1.23142995999678928769203326079270844300001490E-126
        [10] "modulated_moan"         p = 2.35087348520784261781648455067721546146834484627823455E-131
        [11] "groan"                  p = 3.576923213177725455147500820967277814048E-164
        [12] "descending_shriek"      p = 1.02284875436151126407837244719088235E-221
        [13] "long_grunt"             p = 8.9049698625057595751944226629608568359E-238
        [14] "tone_w_slight_downsweep" p = 1.11971066818204724664724995420066069454E-242
        [15] "BB"                     p = 4.2803505067119547729261431684984929538E-244
        [16] "tone"                   p = 2.42920895058702548074997515433549550155E-247
        [17] "croak"                  p = 2.091403829368959054301828416682343320055E-259
        [18] "growl"                  p = 6.1217426862854383757448339803070560781E-260
        [19] "upsweep"                p = 2.9586489801724992148242794120659687E-267
        [20] "modulated_cry"          p = 1.07347789833791185808569994831934977E-267
        [21] "bellow"                 p = 7.9538592110590969484707337836995577400440E-269
        [22] "moan"                   p = 1.57177926756059411067923560790764428E-272


      "gurgle"                 sequence (T=18):
        [00] "descending_moan"        p = 3.4074704730972256246159839862724096205121948380E-27
        [01] "modulated_moan"         p = 3.081305378591401866771096299237210284190174226974E-27
        [02] "trill"                  p = 2.607088432444063113196899786275734449789774476E-33
      * [03] "gurgle"                 p = 1.043767615817829431740920855410675434663567218E-33
        [04] "grunts"                 p = 1.123035218046247937142305889741137387219073221806E-34
        [05] "ascending_moan"         p = 2.12489660246657488379541927113156392930449957E-37
        [06] "grunt"                  p = 2.1616199693307455949915192317632749920815E-39
        [07] "bellow"                 p = 1.76883877949123565724579353475783977361041434133950122494598050E-39
        [08] "ascending_shriek"       p = 8.6718996478416367066432343832581788644890689E-47
        [09] "cry"                    p = 2.9145084720992975764028628745801629436870E-47
        [10] "growl"                  p = 7.9762799425603604996605829557739332419263E-54
        [11] "modulated_cry"          p = 6.53724068786173051015421349036384319209758128E-58
        [12] "long_grunt"             p = 4.864505866047028148372805193342914805923914E-59
        [13] "descending_shriek"      p = 2.167772528926640800349254017514940741298042E-60
        [14] "groan"                  p = 3.842017658959106073533488717046780148E-68
        [15] "croak"                  p = 9.213523756048876191115592226361255561394228E-70
        [16] "purr"                   p = 2.16827373105559037696988699809890926056E-71
        [17] "tone_w_slight_downsweep" p = 5.68115880017549214843389115687631877E-86
        [18] "BB"                     p = 1.248619060585509705043566513164307116E-88
        [19] "scream"                 p = 1.00000000000000000000000000000000291E-90
        [20] "upsweep"                p = 1.00000000000000000000000000000000283E-90
        [21] "moan"                   p = 9.9999999999999999999999999999999991E-91
        [22] "tone"                   p = 9.9999999999999999999999999999999904E-91


      "gurgle"                 sequence (T=41):
        [00] "modulated_moan"         p = 2.0004998802190812182707512617725029286907008354E-35
        [01] "purr"                   p = 1.330723695716135797393269324704670545062017270541873007078521617578843121854756535E-35
        [02] "groan"                  p = 1.74622362418576928852385959517788110849359173450413E-38
      * [03] "gurgle"                 p = 2.046568666592211078995078299107030748890946666527797E-39
        [04] "descending_moan"        p = 5.627845347379772587070127761485700835421147742E-54
        [05] "grunt"                  p = 4.839441400280675390371516748716513185570300E-56
        [06] "ascending_moan"         p = 2.5453522816067792437385628616449360357014401E-60
        [07] "ascending_shriek"       p = 5.88679768487825236489218585598125307221629E-64
        [08] "descending_shriek"      p = 3.63980086412818795863543055568942601400607463E-71
        [09] "grunts"                 p = 2.6673671987882072926014120832067078848947021E-82
        [10] "cry"                    p = 2.9253095495023950112030220236445292836874192790E-92
        [11] "growl"                  p = 1.145369034253069047853429236545619320727345961990E-109
        [12] "trill"                  p = 3.26730140119389240173222174292920456112415E-112
        [13] "modulated_cry"          p = 4.50374291356678076963315567937875846533778664E-134
        [14] "long_grunt"             p = 1.0055453327014641530823791197650156711E-148
        [15] "bellow"                 p = 2.85154271887998534588123181413362207165000000000000007245859500304000591141370558606703377E-151
        [16] "scream"                 p = 1.9452605825842670627857739914667295911776E-165
        [17] "BB"                     p = 1.02100900010064865074164271486091436E-166
        [18] "tone"                   p = 3.1637367575358476548644290210128268801E-192
        [19] "upsweep"                p = 3.33769005850071608382319163521440954E-203
        [20] "tone_w_slight_downsweep" p = 3.0922502632931318404337475112999783E-203
        [21] "moan"                   p = 1.08998446704734963578558936666542499E-203
        [22] "croak"                  p = 1.00000000000000000000000000000000009E-205


      "gurgle"                 sequence (T=54):
        [00] "purr"                   p = 1.28173735659852096137245403293946034329721677964314847772875751098180151893681750E-51
      * [01] "gurgle"                 p = 3.2709283547645541212267709183374806100857009868522283E-59
        [02] "modulated_moan"         p = 3.47282654475769821793081436000164972611173251897E-70
        [03] "groan"                  p = 4.0483339225770108576984010999079222160346523104595352E-86
        [04] "grunt"                  p = 1.313687131559460476446398505430685655102017E-95
        [05] "descending_moan"        p = 1.854891313108113590718766627511500368413934E-96
        [06] "ascending_moan"         p = 3.75623525533471853482513850623536988161438279E-99
        [07] "descending_shriek"      p = 1.299702623134929187618503965738802595733336153E-103
        [08] "grunts"                 p = 2.3390887416512221793906872161886817792656680E-104
        [09] "ascending_shriek"       p = 2.75618748417440272986872332944317244070844E-131
        [10] "cry"                    p = 6.584195271266695650451701160214419621198534661E-148
        [11] "trill"                  p = 9.075218428709701079168173977249706201145952E-153
        [12] "modulated_cry"          p = 5.9122773661175231776057179142231548115871641853835E-155
        [13] "long_grunt"             p = 6.128603311550966651883278413329943753731704E-178
        [14] "growl"                  p = 2.4383117577422077476414898786368062436178403794E-184
        [15] "bellow"                 p = 1.85859897646201633375600257814316555670000000000000000000000000000170902712601709869107765769919305566E-196
        [16] "scream"                 p = 5.79241161178064322111532280711978481613447985E-202
        [17] "BB"                     p = 2.45070952321525099428933716636064792E-208
        [18] "tone_w_slight_downsweep" p = 7.8015309348920306910971138768177217E-223
        [19] "upsweep"                p = 6.0326462574108032089138160516459624E-225
        [20] "moan"                   p = 1.99423074354092314864685545178219254E-229
        [21] "tone"                   p = 3.293501598937969625439465602435147986E-247
        [22] "croak"                  p = 1.00000000000000000000000000000000005E-270


      "gurgle"                 sequence (T=29):
        [00] "croak"                  p = 1.623350235204673692331931993964407753673088E-33
        [01] "purr"                   p = 6.745693947563916738965006765467426926676909953134242132659206929176655764912027973E-34
        [02] "trill"                  p = 1.491114511367126349761983005833087647343400175E-36
      * [03] "gurgle"                 p = 4.527990542031370594708407667621701604939996592731E-38
        [04] "descending_moan"        p = 1.05757961343006356085046386532968959929068341986E-41
        [05] "grunt"                  p = 5.14390652993417690966137496905337053758588503638052E-42
        [06] "bellow"                 p = 1.95688038328858004809845999792180123093596790000000000000000000000000000000000000000000000000000000000447331258006164501781844049198331330E-42
        [07] "long_grunt"             p = 1.1582672010192519065944206534841237762874054E-42
        [08] "groan"                  p = 4.95194911524229032531123096635662893946389582159471100360E-51
        [09] "ascending_shriek"       p = 2.123924960154579191569699983889770480051363E-57
        [10] "ascending_moan"         p = 8.5691926793844064188794179135122916732110572E-58
        [11] "cry"                    p = 7.44150129112776467509758497481306170627008091859988471E-68
        [12] "descending_shriek"      p = 7.00856527656023681073906099507564031156818684E-74
        [13] "modulated_moan"         p = 1.591227499656875213673654653012977743578E-101
        [14] "scream"                 p = 2.25132166485948438690006016740359102E-131
        [15] "grunts"                 p = 8.06703012080832632341545511060357704E-138
        [16] "BB"                     p = 4.5261992446493262459348377656980639E-143
        [17] "upsweep"                p = 1.00000000000000000000000000000000315E-145
        [18] "tone_w_slight_downsweep" p = 1.00000000000000000000000000000000194E-145
        [19] "growl"                  p = 1.00000000000000000000000000000000066E-145
        [20] "moan"                   p = 9.9999999999999999999999999999999973E-146
        [21] "tone"                   p = 9.9999999999999999999999999999999955E-146
        [22] "modulated_cry"          p = 9.99999999999999999999999999999997113E-146


      "gurgle"                 sequence (T=38):
        [00] "trill"                  p = 1.0711846904508078063863560205253427946836302E-45
        [01] "purr"                   p = 3.2454797890071693455005163003862998039415526014185270891715815696449119210629913422E-48
      * [02] "gurgle"                 p = 1.63075625225754202360658559671329549155087793006966E-49
        [03] "descending_moan"        p = 6.0113919518177031859341582674914782585030114467E-51
        [04] "bellow"                 p = 8.371915050677971677144708776774795216887871000000000000000000000000000000000000000000000000000000000000000000000000000000000296299560516014886815608977059564499E-57
        [05] "long_grunt"             p = 1.40202803539834777650931220884014542480E-60
        [06] "croak"                  p = 2.003102567341375104123284339202602848287214364E-61
        [07] "grunt"                  p = 6.48460737182846987480072970363377447473413913620043E-65
        [08] "groan"                  p = 6.0508072171521916246269394566225556190692761209982751962E-68
        [09] "cry"                    p = 1.191699946333225937501557964908809084500023520365528140E-75
        [10] "ascending_moan"         p = 2.05115680688938904047505973758929675939571451E-76
        [11] "ascending_shriek"       p = 1.76411890984258820665683632221184414116501967E-79
        [12] "descending_shriek"      p = 2.01080188864320197919592234288475049464164723E-113
        [13] "modulated_moan"         p = 1.26266463441230728189284072898278554594E-120
        [14] "grunts"                 p = 3.2668945751806036896092144811836238648E-152
        [15] "scream"                 p = 3.8884032599584633925847878571942123E-157
        [16] "growl"                  p = 2.037510517997857948877916254668002992E-177
        [17] "modulated_cry"          p = 8.43451798646709915562540660471414366E-185
        [18] "upsweep"                p = 1.00000000000000000000000000000000303E-190
        [19] "tone_w_slight_downsweep" p = 1.00000000000000000000000000000000126E-190
        [20] "BB"                     p = 1.00000000000000000000000000000000048E-190
        [21] "tone"                   p = 9.9999999999999999999999999999999985E-191
        [22] "moan"                   p = 9.9999999999999999999999999999999982E-191


      "gurgle"                 sequence (T=38):
        [00] "groan"                  p = 2.29627627139522748860367391553776874823541701028E-61
      * [01] "gurgle"                 p = 2.92477849291806178582740123518614310377354E-68
        [02] "trill"                  p = 7.24805928906023582258452471643228903639780E-72
        [03] "croak"                  p = 9.50986442985048195116511134000738889925827E-86
        [04] "ascending_shriek"       p = 1.38162591875132795342578049596583845422782857E-88
        [05] "descending_shriek"      p = 1.5125262226871390244223478914329204334027673E-92
        [06] "bellow"                 p = 5.493930653156420158009720543261143737830000000000000000000000000000000000000000000000000000000000049999999999999999999999999999999832E-93
        [07] "purr"                   p = 5.3562324350480811789831120758208336373245495606080449404070836E-104
        [08] "long_grunt"             p = 2.6770071419256401642098858087183587591553E-104
        [09] "grunt"                  p = 5.1298514186403869190365001790532128739E-121
        [10] "descending_moan"        p = 4.005783326666463989878970729176981738093290100E-140
        [11] "modulated_moan"         p = 5.945569066409586546550930343867615810213161E-147
        [12] "grunts"                 p = 4.871410006565427809952351719335292666E-178
        [13] "ascending_moan"         p = 1.0758130966203274466872165412606463367136E-179
        [14] "cry"                    p = 2.958894846717782496210329861806269587766E-180
        [15] "scream"                 p = 4.51281967912494956977803220550869681E-186
        [16] "upsweep"                p = 1.00000000000000000000000000000000303E-190
        [17] "tone_w_slight_downsweep" p = 1.00000000000000000000000000000000126E-190
        [18] "growl"                  p = 1.00000000000000000000000000000000087E-190
        [19] "BB"                     p = 1.00000000000000000000000000000000048E-190
        [20] "tone"                   p = 9.9999999999999999999999999999999985E-191
        [21] "moan"                   p = 9.9999999999999999999999999999999982E-191
        [22] "modulated_cry"          p = 9.9999999999999999999999999999999651E-191


      "gurgle"                 sequence (T=61):
        [00] "descending_moan"        p = 3.9366541632382958675922014341340058297518975630E-74
        [01] "modulated_moan"         p = 3.427453648087332015085294235625095303250832151E-74
        [02] "growl"                  p = 1.5953433879668332473774769809315094669008370036E-76
        [03] "cry"                    p = 2.1050126245095969710244729430696535998182301880534E-79
      * [04] "gurgle"                 p = 2.374563911124442781174177423869256080613590307E-87
        [05] "trill"                  p = 5.8567697763991368572243247971781885264820761074026E-95
        [06] "ascending_moan"         p = 8.35881711624516336373038640665012666302313943E-96
        [07] "ascending_shriek"       p = 3.00626774170626280202405875772081161096121960E-99
        [08] "grunts"                 p = 2.4122728139874944171443341601537742166574371730587722E-121
        [09] "grunt"                  p = 1.26371702159758354328442846783480766663606251029E-129
        [10] "modulated_cry"          p = 4.64327487961733910774956860959587519504207107601E-187
        [11] "purr"                   p = 1.3275589005355667950429531768277870799377524493785851592527909321628105118E-200
        [12] "groan"                  p = 5.03808462905525339850556419192989560416337E-203
        [13] "bellow"                 p = 4.864665368518717409121049125224364866494000000000000000000000000000443996550190611611439040129279624478E-210
        [14] "descending_shriek"      p = 1.445661362952127690088198012394385581103E-264
        [15] "croak"                  p = 6.612163659323799263769139985510494321893E-271
        [16] "long_grunt"             p = 5.16714539078162886717417121719937309E-290
        [17] "upsweep"                p = 3.8221888519449064235822274721579503E-301
        [18] "BB"                     p = 2.03635408012593167473646900832682865E-303
        [19] "scream"                 p = 1.00000000000000000000000000000000623E-305
        [20] "tone"                   p = 1.00000000000000000000000000000000083E-305
        [21] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999984E-306
        [22] "moan"                   p = 9.9999999999999999999999999999999929E-306


      "gurgle"                 sequence (T=71):
        [00] "modulated_moan"         p = 1.39393213826853668512936311114357603621324152339E-83
        [01] "growl"                  p = 2.8474652771319008869576763423261585922551141650366E-88
        [02] "descending_moan"        p = 1.2686484977163985845669800637755929084719561561E-92
      * [03] "gurgle"                 p = 8.60058577118426498573641560365955279971620906E-94
        [04] "cry"                    p = 8.3471596938491460228769798876278056901098931049203E-95
        [05] "trill"                  p = 2.08916887039899825138027095982588761061032610254861E-95
        [06] "grunts"                 p = 1.39274648948743915097787417514300082501697218530540391E-103
        [07] "ascending_shriek"       p = 1.600389000442801065908008047107490822501859116E-116
        [08] "ascending_moan"         p = 7.197444578885861084283944290889125966378514E-124
        [09] "grunt"                  p = 1.005151888629591304742801702673607068029898040761E-130
        [10] "modulated_cry"          p = 9.752993466907592641281689214213330910935165E-186
        [11] "groan"                  p = 2.379189123969252717152606581572561242895120E-191
        [12] "bellow"                 p = 1.252935446036357936115940199600560264000000000000000000000000000000000000000000000000000000000000000000001543492391067537612778793337998214783572E-213
        [13] "purr"                   p = 2.585654649531921094765582043267751832972541193093067949660326354722399994391E-227
        [14] "descending_shriek"      p = 1.802068934857834690268905930716874160E-263
        [15] "long_grunt"             p = 3.899173630188759349459509314909293157E-326
        [16] "croak"                  p = 1.97004072176067848349037500977582403814E-327
        [17] "BB"                     p = 6.9937276525777364083281993180083499E-351
        [18] "upsweep"                p = 9.1740108073257759634384264065503003E-354
        [19] "scream"                 p = 1.00000000000000000000000000000000626E-355
        [20] "tone"                   p = 1.00000000000000000000000000000000096E-355
        [21] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999944E-356
        [22] "moan"                   p = 9.9999999999999999999999999999999903E-356


      "gurgle"                 sequence (T=90):
        [00] "modulated_moan"         p = 1.3135322233422372648752261154057271099011137671567E-87
        [01] "grunts"                 p = 5.7087272645508625498176749724335712604472684562447453566E-98
        [02] "trill"                  p = 3.02141267484419290981306572158530850037483121108317E-98
        [03] "growl"                  p = 2.451768275782720905150309365063282385280440054255134E-105
        [04] "grunt"                  p = 2.5372003782429663525761441196056476689548180050658E-108
        [05] "descending_moan"        p = 5.606971517148191343634522467388453038240349690E-109
        [06] "modulated_cry"          p = 5.7609600617781578821272398704184987192732470826E-116
      * [07] "gurgle"                 p = 8.53231569914922344618650131376399060880069003E-122
        [08] "ascending_shriek"       p = 7.185809385481060221423492215868789888054233179E-135
        [09] "cry"                    p = 2.476900229645120738457060639232704591258978619342453E-140
        [10] "bellow"                 p = 1.47425107623125561759109857348152031402245400000000000000000000000000000000000000000000000000000000000000000000816946473352053007574315228963906252506555E-145
        [11] "ascending_moan"         p = 7.135150244447677702659156048265155070369171266E-159
        [12] "descending_shriek"      p = 3.4010380084742099196180697325851945285277E-253
        [13] "purr"                   p = 9.092449681021112788935653812065661093664297590609565246263234912175033751690E-279
        [14] "groan"                  p = 6.6977924712949442186442184670204470695E-333
        [15] "croak"                  p = 9.889048416400057604079785411819480322359278320E-337
        [16] "long_grunt"             p = 2.741795567977315161259959034115185764353745E-368
        [17] "scream"                 p = 1.00000000000000000000000000000000741E-450
        [18] "upsweep"                p = 1.00000000000000000000000000000000319E-450
        [19] "tone"                   p = 1.00000000000000000000000000000000189E-450
        [20] "moan"                   p = 9.9999999999999999999999999999999906E-451
        [21] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999881E-451
        [22] "BB"                     p = 9.9999999999999999999999999999999877E-451


      "gurgle"                 sequence (T=61):
        [00] "modulated_moan"         p = 4.47332803635429822703442664649283501588539400E-65
        [01] "trill"                  p = 1.419433485879759715399520717094588071211361081193E-69
        [02] "grunts"                 p = 1.522895613553200680932582152023244794495475734216967E-75
        [03] "descending_moan"        p = 9.352183724289430083069157193478884694066165E-77
      * [04] "gurgle"                 p = 5.207274600408657510426245216738912070390078E-86
        [05] "growl"                  p = 4.18694488594399299132755332030864734080251601115E-88
        [06] "grunt"                  p = 6.64224163243595193817296215665463859255263183996E-94
        [07] "cry"                    p = 2.73984594360891165302784642602013159581149807836E-99
        [08] "ascending_shriek"       p = 4.319202152013695058157547059266415751131583E-102
        [09] "ascending_moan"         p = 7.2409416298306393481834985341672317546687851E-108
        [10] "modulated_cry"          p = 2.144440904505049065417876258722408217938E-122
        [11] "bellow"                 p = 7.53628966417676914937403691387362033600000000000000000000000000000000000000000000000000458381349367368392933124493964438950783E-133
        [12] "descending_shriek"      p = 2.57916772689878815694124484448635149190267E-201
        [13] "groan"                  p = 4.3286511512740090996219784833031789747E-208
        [14] "purr"                   p = 1.241638877405054907277153896391736005377855072272775E-219
        [15] "croak"                  p = 1.87436771535880466847366716203745815086276318477E-246
        [16] "long_grunt"             p = 2.55418328059813362926612436316241384138E-247
        [17] "BB"                     p = 2.01278297783921274262730205993638671E-303
        [18] "scream"                 p = 1.00000000000000000000000000000000623E-305
        [19] "upsweep"                p = 1.00000000000000000000000000000000259E-305
        [20] "tone"                   p = 1.00000000000000000000000000000000083E-305
        [21] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999984E-306
        [22] "moan"                   p = 9.9999999999999999999999999999999929E-306


      "gurgle"                 sequence (T=56):
        [00] "modulated_moan"         p = 7.9180583484444822647503230455156859469590734448E-57
        [01] "grunts"                 p = 3.72292771604716106869206424650976960208314075442606882871263E-59
      * [02] "gurgle"                 p = 1.3083169892804137492172033288059060523205569E-61
        [03] "growl"                  p = 3.715490991331732966294576950658134530971008665393847474E-62
        [04] "trill"                  p = 1.12039144350456818929722501596404835232372692390E-63
        [05] "descending_moan"        p = 3.8032813673823009510732837952519433341896341E-68
        [06] "ascending_shriek"       p = 3.2092163489303758834821672781129865365187905E-70
        [07] "cry"                    p = 7.506845476241650179584360481036811363410234640E-72
        [08] "grunt"                  p = 1.02759874256818342354940774352337565863102785370E-75
        [09] "ascending_moan"         p = 8.1816396428274002080178852954913383617283E-87
        [10] "modulated_cry"          p = 2.7529189031024654639726812310843988585406965263E-90
        [11] "bellow"                 p = 3.97788567716650101798709228820310825373701720000000000000000000000000000000000000103308473668186222697388791634783414E-144
        [12] "descending_shriek"      p = 2.5115949849008735627679368852670624261850470E-161
        [13] "groan"                  p = 3.234783924433832876951996997299687672645287E-196
        [14] "purr"                   p = 2.0994737000274626456638750809245605147254596274956715745655E-197
        [15] "croak"                  p = 1.727982351099046353748785575734125552E-219
        [16] "long_grunt"             p = 4.857723002896550625468386450233105351192E-240
        [17] "scream"                 p = 1.00000000000000000000000000000000583E-280
        [18] "upsweep"                p = 1.00000000000000000000000000000000284E-280
        [19] "tone"                   p = 1.00000000000000000000000000000000038E-280
        [20] "tone_w_slight_downsweep" p = 1.00000000000000000000000000000000022E-280
        [21] "BB"                     p = 9.9999999999999999999999999999999977E-281
        [22] "moan"                   p = 9.9999999999999999999999999999999950E-281


      "gurgle"                 sequence (T=66):
        [00] "descending_moan"        p = 5.34264198295071310893713496698692352610337958E-84
        [01] "cry"                    p = 1.4918167281742238523756883914036338476776652964E-92
        [02] "growl"                  p = 5.5882528927895742603979650909484290836836753E-94
      * [03] "gurgle"                 p = 1.18501987422178042806535543479844971852002139E-97
        [04] "modulated_moan"         p = 1.7840185809309370683011777268309501555468201E-98
        [05] "trill"                  p = 2.030198024017044713745685587423346669964175321247E-112
        [06] "ascending_shriek"       p = 1.94995160615800446925976555355900334178263E-115
        [07] "ascending_moan"         p = 5.014448983577514279383501511732249829855807E-121
        [08] "grunt"                  p = 1.1338480111872202788284110908433308628060108854E-147
        [09] "grunts"                 p = 1.53532185922189502202216957975101431115852550240605965E-155
        [10] "groan"                  p = 1.556104392141959523260677620289840719007456E-212
        [11] "modulated_cry"          p = 3.3228861566801167524840860356686165844113E-228
        [12] "bellow"                 p = 5.5318432369312861177852608029461900018156200000000000000000000000000175362558130104780091934056476135768E-231
        [13] "purr"                   p = 1.7438484640710771293835911785759468917995655483522877899941219977336808521942E-232
        [14] "descending_shriek"      p = 3.6725635362673282220616995974061378589339E-270
        [15] "croak"                  p = 2.64829702896080677302116118514391919E-279
        [16] "long_grunt"             p = 2.129146465397675681208561398658678249E-307
        [17] "BB"                     p = 5.440527075456059394905841692350108682E-320
        [18] "upsweep"                p = 7.1021860117930475036813358412430847E-329
        [19] "scream"                 p = 1.00000000000000000000000000000000647E-330
        [20] "tone"                   p = 1.00000000000000000000000000000000094E-330
        [21] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999972E-331
        [22] "moan"                   p = 9.9999999999999999999999999999999914E-331


      "gurgle"                 sequence (T=91):
        [00] "cry"                    p = 4.3994868195806686445247891755114875426855729130536756E-87
        [01] "ascending_moan"         p = 1.75138919098577488767572067441668703891426767946E-98
      * [02] "gurgle"                 p = 1.66943663515471889819341198705424922832097567534841E-100
        [03] "descending_moan"        p = 2.10614134375531732770351405159606850068259194844E-101
        [04] "groan"                  p = 8.459398771573492389174045927602491040001740409467E-103
        [05] "modulated_moan"         p = 5.519883570517510358921534814399101048490260222072705724879E-116
        [06] "purr"                   p = 1.731024959086855177530279845266451550569679987577899839546000048628021584517885267598926E-161
        [07] "ascending_shriek"       p = 2.6382874622886170365243088859784778281511984E-226
        [08] "grunt"                  p = 3.908371598181279224715196043933023332691172E-269
        [09] "long_grunt"             p = 7.033025701217786841839748439164979052883E-299
        [10] "bellow"                 p = 7.5192350831838352085443905787182141480800000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000331398381825750033624435625455429453E-308
        [11] "trill"                  p = 2.702869820657760970997890508955861864E-309
        [12] "grunts"                 p = 7.2998637162652423519866395875728695422890415493E-358
        [13] "scream"                 p = 4.01016580704042570099548037078586519238578250E-373
        [14] "descending_shriek"      p = 5.87553806057857359352468140416057665202685E-378
        [15] "tone"                   p = 1.125783015647422124764672147832237989E-385
        [16] "croak"                  p = 7.72172160827612537126207945383951324E-426
        [17] "BB"                     p = 4.24586001441453357618520200799376139E-443
        [18] "growl"                  p = 1.81072097211680042668634201893497116E-446
        [19] "tone_w_slight_downsweep" p = 7.5283153337249128754692276705791488E-452
        [20] "upsweep"                p = 2.7816657168020784220102125463235153E-453
        [21] "moan"                   p = 1.19285477209241475733266537848455845E-453
        [22] "modulated_cry"          p = 8.6055570473860953296620796833892519E-454


      "gurgle"                 sequence (T=111):
        [00] "groan"                  p = 1.35511374903730497653417198875005800416173E-121
      * [01] "gurgle"                 p = 1.94373410491660742603683908463548527408775020E-122
        [02] "cry"                    p = 3.1652009517742350975734868760135853788040E-125
        [03] "purr"                   p = 1.300298956600862988856894679923634591634632611913257623102804326005961771E-130
        [04] "descending_moan"        p = 1.625320295312587770486536778724704409491792134E-132
        [05] "modulated_moan"         p = 2.31784300075701973173253650547346375802971E-146
        [06] "ascending_moan"         p = 5.18924654873495430841131620287768779922923E-149
        [07] "ascending_shriek"       p = 4.66160749351811771073387550494465150412003261E-257
        [08] "grunt"                  p = 9.5226493196885993789514373714986956961275804284E-346
        [09] "grunts"                 p = 4.584209826572902661653254497485067891341000853095133418583076E-379
        [10] "bellow"                 p = 2.2026927737078399357452701069612560180100000000000000000000000000000000000000166392172294007912077236622235506882143573344E-404
        [11] "descending_shriek"      p = 4.304651293346997801114232952605341748043190E-419
        [12] "trill"                  p = 7.5752059229519951373192313227404343754434425356E-442
        [13] "tone"                   p = 6.808207339817786358394610077923551637E-447
        [14] "growl"                  p = 1.08899735315265778865549542410376021404202245838532343E-451
        [15] "long_grunt"             p = 5.627740525900292776374723790351164484E-480
        [16] "scream"                 p = 1.668635536622737558841271777450102380E-486
        [17] "modulated_cry"          p = 5.32073084630156126827804077972899599737336E-492
        [18] "BB"                     p = 2.69137860780273906028942156374424438E-530
        [19] "croak"                  p = 7.5883626147936851725433314528297043E-543
        [20] "upsweep"                p = 1.00000000000000000000000000000000331E-555
        [21] "moan"                   p = 9.9999999999999999999999999999999936E-556
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999762E-556


      "gurgle"                 sequence (T=63):
        [00] "groan"                  p = 3.260612637884001718087706017437068994448736920809E-53
        [01] "cry"                    p = 2.648363147460390508973162034827440208672024369931115E-58
      * [02] "gurgle"                 p = 5.87022448713270509923954175250524841765570213194734E-60
        [03] "ascending_moan"         p = 1.90168096768629304067632402379542613284294858587E-64
        [04] "modulated_moan"         p = 2.955037992757168429476381292322635648161121799731394022758E-69
        [05] "descending_moan"        p = 1.21133536048962345974117672191985718192366891642E-69
        [06] "purr"                   p = 2.28405613072899037128299670958942377197000289110460072546157813043740709949817065329E-70
        [07] "ascending_shriek"       p = 1.5763086447278951034640652028637350127705167E-131
        [08] "grunt"                  p = 2.19049164198282373130256531553257596317618124682E-171
        [09] "grunts"                 p = 5.847763377943278040116841994188397145855715014E-193
        [10] "long_grunt"             p = 3.0117631484967734443089298708478884225635E-194
        [11] "bellow"                 p = 1.1583007729528588374679036694839620090000000000000000000000000278715933932247850194775357627977263E-226
        [12] "trill"                  p = 4.235874959100551407330173545833689691E-257
        [13] "scream"                 p = 6.665099464970759314669677947050793824298E-261
        [14] "descending_shriek"      p = 5.08985693675101187948910580971349865657E-273
        [15] "croak"                  p = 2.614441440039681674626560699393691804E-282
        [16] "tone"                   p = 2.43816084002141415209841834455330936E-282
        [17] "growl"                  p = 1.44211754966257679832519727739059426E-308
        [18] "modulated_cry"          p = 1.53827977050516813219311669326758146E-310
        [19] "upsweep"                p = 1.00000000000000000000000000000000257E-315
        [20] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999991E-316
        [21] "BB"                     p = 9.9999999999999999999999999999999945E-316
        [22] "moan"                   p = 9.9999999999999999999999999999999925E-316


      "gurgle"                 sequence (T=85):
        [00] "cry"                    p = 1.382342922339511751588895688988416575301644951077E-79
        [01] "ascending_moan"         p = 5.96176699735729145504912054502060611406334055E-90
      * [02] "gurgle"                 p = 2.470014287534371210568588762205005077125362155948E-91
        [03] "groan"                  p = 1.4375893972241947980346854838539692138878119746E-92
        [04] "descending_moan"        p = 1.71844980456114719071096783537232959736072475461E-99
        [05] "modulated_moan"         p = 5.6989570858082787389673815706587504741360463030258710346E-112
        [06] "purr"                   p = 4.057635226602084111853580112041007222568620816122789283479208950261830783110602384171064E-187
        [07] "ascending_shriek"       p = 1.3544652373203440426328112860231346205044746E-210
        [08] "grunt"                  p = 3.54642572578848192768859717924349207549191122E-233
        [09] "trill"                  p = 1.08160052607452947055110515952532650099E-244
        [10] "long_grunt"             p = 5.37488598948550119323197990327724010217597E-259
        [11] "grunts"                 p = 7.0559837509610207093688522803139543842915276647E-292
        [12] "bellow"                 p = 4.57316755720188882998558527369438062255495945445304881630705015597051259187245373E-308
        [13] "scream"                 p = 4.915835612194138653351819866947752375170E-361
        [14] "croak"                  p = 2.2399711890372892580461250273168415344994245E-374
        [15] "tone"                   p = 1.02874594099144003817204947223511613278E-375
        [16] "growl"                  p = 4.3636016373579345014714819717902093E-391
        [17] "descending_shriek"      p = 1.02088721318176152743461256949236641E-396
        [18] "modulated_cry"          p = 1.83547219060164487112299021023918390E-422
        [19] "BB"                     p = 4.5616914814284014903734195425301726E-423
        [20] "tone_w_slight_downsweep" p = 3.5998269306424259617749749983059084E-423
        [21] "upsweep"                p = 2.7454041249050108142844979736070922E-423
        [22] "moan"                   p = 1.20019837546888783500451750160567338E-423


      "descending_moan"        sequence (T=179):
        [00] "growl"                  p = 4.2881138244462203733319561866305482943839522613602E-169
        [01] "ascending_moan"         p = 8.655336760597085628421996041629623062816032738E-180
      * [02] "descending_moan"        p = 1.7885265867062989952657589211322600745291035E-182
        [03] "gurgle"                 p = 2.6840600479324227542045370995613847001200997573E-219
        [04] "ascending_shriek"       p = 7.7187923614068403526795765957369582795680469E-262
        [05] "modulated_moan"         p = 7.68846627062277824871560020274288971432339358E-289
        [06] "cry"                    p = 2.1261805570804901046647932522073454267476060156020E-304
        [07] "grunt"                  p = 1.07447137271797673663796318600749806716007147301E-413
        [08] "trill"                  p = 1.335521800028420000921758063771903543714324128703379E-587
        [09] "upsweep"                p = 1.29374874180870679473876395458767393E-640
        [10] "grunts"                 p = 5.23467819919234666317272921997880342610270434327798907922E-680
        [11] "modulated_cry"          p = 1.875730406446013070438549204707918818166804547677E-722
        [12] "purr"                   p = 3.15847148567620051722945872735481237940372509059211439938988927651234985466878E-734
        [13] "descending_shriek"      p = 2.624663983524042456929778586998594933646E-797
        [14] "groan"                  p = 4.5847802054284688759485610306202970372395E-798
        [15] "bellow"                 p = 8.276738385484883753134801096142199847470975000000000000000000000000000354959915208839966807233094139015476E-814
        [16] "croak"                  p = 5.78549614151992620113806012841576781E-838
        [17] "BB"                     p = 1.01210214601575914145770443729098112E-888
        [18] "long_grunt"             p = 1.71392912502546412770856652591238991E-893
        [19] "scream"                 p = 1.00000000000000000000000000000001436E-895
        [20] "tone"                   p = 1.00000000000000000000000000000000543E-895
        [21] "moan"                   p = 9.9999999999999999999999999999999849E-896
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999414E-896


      "descending_moan"        sequence (T=206):
        [00] "grunts"                 p = 2.33320992340032503392095480526200854785144021E-266
      * [01] "descending_moan"        p = 1.76448436712744627353347142611994590448732623E-296
        [02] "purr"                   p = 4.7904068713128251273244288239866503573020393163547081785506051090E-297
        [03] "cry"                    p = 1.51694691887282535801990467095790656777828353244576017E-297
        [04] "trill"                  p = 4.169993505414806088179429347420934622596272961E-310
        [05] "grunt"                  p = 2.825016754273342511782048344333381585822404136E-321
        [06] "ascending_moan"         p = 6.59147026632446517832636023599715952889062813E-377
        [07] "ascending_shriek"       p = 1.68155218445205250268797602380011138785396721E-382
        [08] "modulated_moan"         p = 1.01985726432303870020427844866423333683251275380033E-398
        [09] "groan"                  p = 1.169219144174704559884959772128634634646299701464E-432
        [10] "gurgle"                 p = 2.096782337044857420608549999395989639471857676E-442
        [11] "scream"                 p = 2.8750168510728209389959269521243040466205198E-698
        [12] "tone_w_slight_downsweep" p = 2.5683303868378684364590204350885941207719E-733
        [13] "BB"                     p = 7.19479807360562220808075839216042443433886E-813
        [14] "upsweep"                p = 4.4814993700123286854228740848842828113332E-853
        [15] "tone"                   p = 2.729031677609382972711816401562812299E-903
        [16] "long_grunt"             p = 4.091608833359531742460040031668921893E-905
        [17] "descending_shriek"      p = 1.2136751747586360615867115111345536577535E-955
        [18] "growl"                  p = 1.1432495313857165581712590680016047356E-982
        [19] "modulated_cry"          p = 1.15913646736108960073475504172162456735E-994
        [20] "moan"                   p = 4.70096573725584534047658899318002981E-1013
        [21] "croak"                  p = 4.460971055891350554097308090992284063E-1013
        [22] "bellow"                 p = 8.000463301316147051770235963570101166E-1029


      "descending_moan"        sequence (T=120):
        [00] "bellow"                 p = 8.43245336645200196707401706955726354674410000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000260461062651707079020320819738491936360012E-65
        [01] "modulated_moan"         p = 6.90610441942315503780500761598209333403988867225163584E-67
      * [02] "descending_moan"        p = 3.19866507436298316489182370175032789162493940280E-103
        [03] "ascending_shriek"       p = 3.1255857986936716963116690492028201860792823E-129
        [04] "gurgle"                 p = 3.0151319355742933757366877437272956248574586647E-137
        [05] "modulated_cry"          p = 8.06213450349764988369885027418666571177276690E-171
        [06] "grunts"                 p = 4.94733099101667477192252309776488025582737867093337971E-171
        [07] "trill"                  p = 4.4647347041154132475625416335456796922371705282324E-185
        [08] "grunt"                  p = 8.2412667032476616445666284672035404912887913E-209
        [09] "ascending_moan"         p = 1.3009312049766302422168252036793458796837914968E-209
        [10] "growl"                  p = 1.16857084914626012100750650490906193738622447042850488E-237
        [11] "descending_shriek"      p = 7.634639738202183334577823459300140372832053E-283
        [12] "croak"                  p = 9.377674723834604190647856710931963065759198E-284
        [13] "long_grunt"             p = 3.33173614445890975604359792138378370496212E-292
        [14] "cry"                    p = 2.061796371694180159971973632476368670155314565E-303
        [15] "purr"                   p = 3.829166814042400290836748742312978684350106588278195135500081E-538
        [16] "groan"                  p = 6.15848804370575992431524994131853985753E-569
        [17] "scream"                 p = 1.00000000000000000000000000000000972E-600
        [18] "upsweep"                p = 1.00000000000000000000000000000000326E-600
        [19] "tone"                   p = 1.00000000000000000000000000000000284E-600
        [20] "moan"                   p = 9.9999999999999999999999999999999969E-601
        [21] "BB"                     p = 9.9999999999999999999999999999999837E-601
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999713E-601


      "descending_moan"        sequence (T=105):
        [00] "modulated_moan"         p = 1.436321172176003833072893630236442942621405269574735E-92
      * [01] "descending_moan"        p = 1.3656657076309789362179948837344151905291299854E-104
        [02] "trill"                  p = 4.363702045052448120631684371445229538388218185217867E-124
        [03] "grunts"                 p = 7.856535717479634611657856952106985801784340828452813E-130
        [04] "gurgle"                 p = 1.505757889613222238486162674021529453456920643E-155
        [05] "grunt"                  p = 4.2393903120751509238363445891861985624383781601E-188
        [06] "ascending_moan"         p = 4.55381228758100282438616183088314013423440E-198
        [07] "cry"                    p = 1.2708675448530397413717923286512655012032875307822E-201
        [08] "growl"                  p = 6.56136348145159911012444759513349329463052180329086204E-225
        [09] "long_grunt"             p = 5.973427186117333096999119148230843553509E-228
        [10] "bellow"                 p = 2.26118369913739599001220257567746435393500000000000000000000000000000000000000000000000000000000000000000000000000000000695312392142647495677189584937572115828E-238
        [11] "ascending_shriek"       p = 1.0625235704313133552201821582773042277880009E-272
        [12] "groan"                  p = 1.972603373833411802810043096242679281900E-284
        [13] "purr"                   p = 1.59402609569791906693527633733621744364599726139026872959762784100E-364
        [14] "descending_shriek"      p = 1.4326496499518164806532930423956932675979E-421
        [15] "modulated_cry"          p = 4.04514654282372194317323906455408541864478350E-427
        [16] "croak"                  p = 6.94255721267341049507745838305339402190618E-468
        [17] "scream"                 p = 1.00000000000000000000000000000000881E-525
        [18] "upsweep"                p = 1.00000000000000000000000000000000311E-525
        [19] "tone"                   p = 1.00000000000000000000000000000000224E-525
        [20] "moan"                   p = 9.9999999999999999999999999999999940E-526
        [21] "BB"                     p = 9.9999999999999999999999999999999845E-526
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999791E-526


      "descending_moan"        sequence (T=190):
        [00] "bellow"                 p = 2.5680875271130594920502272993998713809749600000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000002138632026966394542586146495599823588882946153E-143
      * [01] "descending_moan"        p = 1.86846032956783550146420930978087282432363456835E-164
        [02] "modulated_moan"         p = 7.8642885614104239735244541211269635192440911695265986640E-196
        [03] "trill"                  p = 1.169572644248948884942949251035034966346242124382E-261
        [04] "grunts"                 p = 3.42246101267612958610285969561903713490048072650112194E-285
        [05] "gurgle"                 p = 1.0975836206620113850372327598188644723903698645E-325
        [06] "ascending_shriek"       p = 3.2732640820958844564294197704389084823312588E-362
        [07] "ascending_moan"         p = 7.735252884959106604254862644871074891175664996709E-374
        [08] "grunt"                  p = 2.74627938455606170755722903412614105167046355E-399
        [09] "cry"                    p = 5.0009415892221452727723391973619400172928320041E-445
        [10] "descending_shriek"      p = 6.402292984918889954249402979197794674470925E-483
        [11] "long_grunt"             p = 1.792577744775183270331062741711070460199241E-504
        [12] "purr"                   p = 1.4523297629464215955595146170472825819895401026312457835218E-577
        [13] "groan"                  p = 6.70976205435775605430595844268876651E-605
        [14] "growl"                  p = 8.8373337733803714079873323624448646007222772388910094E-631
        [15] "modulated_cry"          p = 2.957307074377849716133949400290288532449032872E-648
        [16] "croak"                  p = 1.8068861122574596222881310793023100618776469E-786
        [17] "scream"                 p = 1.51070521496604476673254447144323541E-948
        [18] "tone"                   p = 1.00000000000000000000000000000000564E-950
        [19] "upsweep"                p = 1.00000000000000000000000000000000383E-950
        [20] "moan"                   p = 9.9999999999999999999999999999999784E-951
        [21] "BB"                     p = 9.9999999999999999999999999999999681E-951
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999336E-951


      "descending_moan"        sequence (T=193):
        [00] "bellow"                 p = 1.38332231070345358299010055866191238457485300000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000009864541336856292656561406376618626693220453767E-111
        [01] "modulated_moan"         p = 5.9424178321411032923403373401097768847357550411662840839E-160
      * [02] "descending_moan"        p = 1.31437402357428475669002561712392902247953193917E-179
        [03] "trill"                  p = 3.4749079774942497412885936258375055825249472132871E-264
        [04] "gurgle"                 p = 1.2733296217094309608723177689515419602207627906E-266
        [05] "grunts"                 p = 2.95286065001601674301861312495245219934864249034218098E-279
        [06] "ascending_shriek"       p = 6.9581150591356923793378990947451098125177780E-290
        [07] "ascending_moan"         p = 1.803415209696623492245750798476962436679047042373E-356
        [08] "grunt"                  p = 3.181791727817813437901037508032769212352E-384
        [09] "modulated_cry"          p = 5.208220272636805569801161496058166123779799083E-452
        [10] "descending_shriek"      p = 3.383260897769071283188672046269094693091872E-467
        [11] "cry"                    p = 4.9934937269819301266507489981973406791896726607E-477
        [12] "growl"                  p = 2.2805494556494429928100322951907918684877059844677958E-498
        [13] "long_grunt"             p = 1.638739019971518311836757341907940894855896E-500
        [14] "croak"                  p = 5.356876515002062053568163825407135421098173E-617
        [15] "purr"                   p = 6.5068344493390476385409348467387702703360839897837266504250E-686
        [16] "groan"                  p = 1.18381832329871519726289086179402170E-777
        [17] "scream"                 p = 2.37015485650755688317480442485854502E-963
        [18] "tone"                   p = 1.00000000000000000000000000000000584E-965
        [19] "upsweep"                p = 1.00000000000000000000000000000000377E-965
        [20] "moan"                   p = 9.9999999999999999999999999999999760E-966
        [21] "BB"                     p = 9.9999999999999999999999999999999669E-966
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999341E-966


      "descending_moan"        sequence (T=186):
        [00] "bellow"                 p = 2.296191347070271950597962750678152189816000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010738585852213480848969048242222825548973806757E-100
        [01] "modulated_moan"         p = 2.269259371178396363888413537632783451334287527100890965E-149
      * [02] "descending_moan"        p = 2.3537397237455563885084660365887462994969087867E-162
        [03] "ascending_shriek"       p = 8.61881416709411907557300228473862742648305E-254
        [04] "gurgle"                 p = 3.258964942199853945354294775080425739279582932E-256
        [05] "trill"                  p = 1.6690288049753311115063329095322924777055471791262E-269
        [06] "grunts"                 p = 7.589139530426583004408411847514026865860112623592190417E-282
        [07] "ascending_moan"         p = 1.4327521242878064114535447895345511935736489088269E-345
        [08] "grunt"                  p = 1.2141767382430233393821142381595432551611E-363
        [09] "modulated_cry"          p = 4.2931389242419009111525604852750128026897770762E-443
        [10] "descending_shriek"      p = 4.320561954537265169972069220522879940891E-454
        [11] "cry"                    p = 2.913996656630975171779716007948646610081974161105E-461
        [12] "long_grunt"             p = 2.746827406200540133512044850774930042393E-481
        [13] "growl"                  p = 3.080962477413365195638973285318081852015333117803138095E-506
        [14] "croak"                  p = 5.278319747220857337367964986668772084383E-587
        [15] "purr"                   p = 1.63819411801261176894252387284272566556289292068457011648701943E-682
        [16] "groan"                  p = 7.04819879782646781506449616022253842E-760
        [17] "scream"                 p = 1.00000000000000000000000000000001483E-930
        [18] "tone"                   p = 1.00000000000000000000000000000000552E-930
        [19] "upsweep"                p = 1.00000000000000000000000000000000341E-930
        [20] "moan"                   p = 9.9999999999999999999999999999999819E-931
        [21] "BB"                     p = 9.9999999999999999999999999999999691E-931
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999360E-931


      "descending_moan"        sequence (T=171):
        [00] "modulated_moan"         p = 4.74191708436791937370075825906839765657704816E-175
        [01] "purr"                   p = 4.23163132566876670670790351199225775461007145341270935542471816540315912293818193E-242
        [02] "gurgle"                 p = 4.9844345651862480136548729997256729755834173038658323E-258
      * [03] "descending_moan"        p = 3.45755648294093304177965237536909929129789133E-300
        [04] "trill"                  p = 8.693926316174684222501808226406826499863E-415
        [05] "ascending_moan"         p = 4.18854226221383503350839763856239437289583278E-419
        [06] "descending_shriek"      p = 3.2609308143390128436776568466003530860418122E-428
        [07] "grunts"                 p = 8.1691947999668374669834558194922112668224E-439
        [08] "cry"                    p = 3.62178498391879973053912255680642884522965094E-445
        [09] "grunt"                  p = 1.4006626006239254019485644996855333712351875E-480
        [10] "groan"                  p = 1.45829887057092215395314082824756548577208427186918E-509
        [11] "ascending_shriek"       p = 1.5673758892111863975013408257827526466518E-547
        [12] "tone_w_slight_downsweep" p = 1.60439205448489248239334266671520978E-578
        [13] "growl"                  p = 4.71106445336930869223443197027791010912815625672E-634
        [14] "upsweep"                p = 3.3237503538169320189254698405603422E-679
        [15] "BB"                     p = 2.468018476973248574666831300488048871E-683
        [16] "tone"                   p = 1.6557220985129783002015268625292028123E-692
        [17] "long_grunt"             p = 8.794337508295787041314156568746892860442E-709
        [18] "modulated_cry"          p = 4.23689667208533263114582308411655061692240E-756
        [19] "bellow"                 p = 1.3524448321389545285834183623711087900000000000000000000000000019928823057893172335929776544703196850E-782
        [20] "scream"                 p = 7.212135735134584448665074929157778499919E-800
        [21] "moan"                   p = 1.98174557017204394951836884694462098E-841
        [22] "croak"                  p = 9.9999999999999999999999999999999717E-856


      "descending_moan"        sequence (T=116):
        [00] "ascending_moan"         p = 9.3253037056028722822552886814254770449722176961664E-152
      * [01] "descending_moan"        p = 2.690383675194555457528189659803427573036781119E-153
        [02] "gurgle"                 p = 2.8462728667680155571268336057516410522295040E-177
        [03] "modulated_moan"         p = 1.1817429022500844479748428201043253008315177E-181
        [04] "grunt"                  p = 1.07843532981660047500326439760636548980989364229737E-186
        [05] "ascending_shriek"       p = 8.7849923309551899876397669443644259552668410E-223
        [06] "cry"                    p = 5.54199733347661866223768153252597581269305E-225
        [07] "growl"                  p = 2.3989392267456419208956456421654395248509255570912804E-233
        [08] "purr"                   p = 6.4380443839699793759325379976061230392680163831537488718076220644357628421520E-278
        [09] "trill"                  p = 4.9311704200644987638609463539220259808817051313668E-285
        [10] "grunts"                 p = 2.009672213545900228632860956968625437569183904314E-314
        [11] "groan"                  p = 1.31710303083021186242298491421507698480642E-404
        [12] "descending_shriek"      p = 2.33439057730246891137873798984269536713E-427
        [13] "long_grunt"             p = 2.67591754147508661442348692758080677E-479
        [14] "modulated_cry"          p = 2.12662695285819205073647261652723409842280108226E-493
        [15] "BB"                     p = 1.85940286917374555994918082558169477114E-514
        [16] "tone_w_slight_downsweep" p = 2.4723202031608396348153269949338115E-518
        [17] "bellow"                 p = 1.55228498322144414320214463587480537027961201E-529
        [18] "scream"                 p = 8.6065346644243452153591309100805450417E-549
        [19] "upsweep"                p = 6.7623303491158250535361006789597298E-570
        [20] "tone"                   p = 7.45507542612990701272695177509561262E-579
        [21] "moan"                   p = 9.9999999999999999999999999999999947E-581
        [22] "croak"                  p = 9.9999999999999999999999999999999845E-581


      "descending_moan"        sequence (T=166):
        [00] "ascending_moan"         p = 4.139494934912475342930380736712938564503799447825089E-153
      * [01] "descending_moan"        p = 1.489797229999641858718697483536061574135765855E-155
        [02] "gurgle"                 p = 1.582763261858926723707383529753247875529176575966715E-239
        [03] "modulated_moan"         p = 8.5786461404091497101209900559912722939152738E-252
        [04] "grunt"                  p = 3.175479190538999804305506993739761120355723452065E-300
        [05] "cry"                    p = 3.125124049611544258797265525546952150915636E-334
        [06] "purr"                   p = 2.15009104942282828761604664366007010258147348844540505893022992856435098839628145E-348
        [07] "growl"                  p = 3.012427827178024992473913779462134333324764612924825E-403
        [08] "ascending_shriek"       p = 3.4919961296758378949034631063901023118008666E-425
        [09] "grunts"                 p = 6.5831834355779798624035180140637093008049334716777E-426
        [10] "trill"                  p = 2.8184307996032417100497231148885945500354258383E-443
        [11] "groan"                  p = 4.4655828808648271030704510032869131162889E-537
        [12] "descending_shriek"      p = 1.5501659330610884522175393925138426761089E-595
        [13] "long_grunt"             p = 9.45386602183602406647456968044868148946E-693
        [14] "BB"                     p = 4.13885454211188554132567570008360141E-696
        [15] "modulated_cry"          p = 4.83556308872247113280440424841859688551889223E-707
        [16] "tone_w_slight_downsweep" p = 2.8276786767684877667534041096997031E-722
        [17] "bellow"                 p = 1.6528763873162941628486135778121008952225554E-747
        [18] "scream"                 p = 3.4800561724532464744935889608902581E-771
        [19] "upsweep"                p = 3.6942472725990761220674621746030010E-811
        [20] "tone"                   p = 1.13430657035225887774207642463156139E-814
        [21] "moan"                   p = 1.23547855764478372611954122105964494E-828
        [22] "croak"                  p = 9.9999999999999999999999999999999732E-831


      "descending_moan"        sequence (T=102):
        [00] "trill"                  p = 5.14607727986102860505482568420263393170482114E-161
        [01] "long_grunt"             p = 1.9376346395011622482898850542899088625666610E-193
      * [02] "descending_moan"        p = 1.798345212779944524143903208169019015835880103E-214
        [03] "gurgle"                 p = 1.924164354110355113542664413742785292439980283546E-273
        [04] "croak"                  p = 3.6067832016751233492468553132766692759728807048E-320
        [05] "ascending_shriek"       p = 3.2621033379667399315337776255410704930577006E-324
        [06] "groan"                  p = 6.400032162654119922342562759979200941530212184486E-325
        [07] "bellow"                 p = 2.226358849421735883788053417362865970587769160000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000049999999999999999999999999999999832E-326
        [08] "purr"                   p = 4.2064321981325558608070867938395875722945757973265706358649274280371913952E-345
        [09] "grunt"                  p = 5.6685748410884476315081914037745399895373271644147E-368
        [10] "modulated_moan"         p = 4.7414111281548581335817589153539615861037718413636E-379
        [11] "descending_shriek"      p = 1.3945173202771702935647304887187373050300362E-389
        [12] "ascending_moan"         p = 5.652224238492255482936041984875787261546973E-395
        [13] "cry"                    p = 3.805787498402491270759675358791867368292530583435E-435
        [14] "grunts"                 p = 2.5666598592089200300660721280644740354E-461
        [15] "upsweep"                p = 3.4696139127665556757827822970688481E-462
        [16] "tone_w_slight_downsweep" p = 3.3743292709316168984169012546625716E-485
        [17] "scream"                 p = 1.4127377679241143945046310690224483984E-506
        [18] "growl"                  p = 1.20960423740280099037898708436712877E-507
        [19] "BB"                     p = 7.0319540763272356125618360148706957E-509
        [20] "tone"                   p = 1.00000000000000000000000000000000203E-510
        [21] "moan"                   p = 9.9999999999999999999999999999999938E-511
        [22] "modulated_cry"          p = 9.9999999999999999999999999999999405E-511


      "descending_moan"        sequence (T=286):
        [00] "modulated_moan"         p = 2.322018283187328430515999207147943188142495829434573E-300
        [01] "trill"                  p = 3.44326520533731273790064408033991728152055798333484E-328
        [02] "grunts"                 p = 5.661907724032719565956241416195910804322003664436802048E-334
      * [03] "descending_moan"        p = 9.47710091818262475040972432808037184286191203E-362
        [04] "growl"                  p = 2.8817320923102076823067506698318331089378314841821971238E-382
        [05] "gurgle"                 p = 4.484837947581281707455182796163574452334382268E-398
        [06] "grunt"                  p = 1.113437403738588400148201957263810475769146685180532E-398
        [07] "cry"                    p = 1.574352319092067522048347738359497471227509749510199E-443
        [08] "ascending_shriek"       p = 2.98561989571474411822085150220447340832843199E-469
        [09] "modulated_cry"          p = 3.215965572080544839225798826755135252083426772E-528
        [10] "ascending_moan"         p = 4.2336206907396616956911095316043448351105171625376E-544
        [11] "bellow"                 p = 8.487276153147954020400380076343889773719771300000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000161943173943483689507092772002134372729279E-557
        [12] "descending_shriek"      p = 1.8165552888576185449801923899590649172350E-898
        [13] "purr"                   p = 5.4057050668247279609207315496834367756830435055794707472977593052E-968
        [14] "groan"                  p = 8.192410278427630876130008542476536307E-1043
        [15] "croak"                  p = 8.789946260813227097607335963359467065986E-1117
        [16] "long_grunt"             p = 1.752578367862947335517047104519818115E-1118
        [17] "BB"                     p = 1.64328662621128558365581854503005551E-1413
        [18] "scream"                 p = 4.3710387582836964452298922916121055E-1425
        [19] "tone"                   p = 1.00000000000000000000000000000000837E-1430
        [20] "upsweep"                p = 1.00000000000000000000000000000000479E-1430
        [21] "moan"                   p = 9.9999999999999999999999999999999683E-1431
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999998885E-1431


      "descending_moan"        sequence (T=218):
        [00] "modulated_moan"         p = 2.18877733753015590603320856154695825229058975066E-221
        [01] "grunts"                 p = 3.21935354551602659811838317705355471630645706280502336433E-240
        [02] "trill"                  p = 1.583574142029202560475066359811721272941155428082548E-244
        [03] "growl"                  p = 1.159919959983081982912773457319787586522773367613627654E-264
      * [04] "descending_moan"        p = 7.15950507216698769169938870341919590499415829E-278
        [05] "grunt"                  p = 7.991309470511693787700026083063514101632096882664E-296
        [06] "gurgle"                 p = 2.282070474573101703164296493042834647964866E-311
        [07] "cry"                    p = 2.922070827268764316889618772453445440375783629E-344
        [08] "ascending_shriek"       p = 1.075738205768538710405553778052370818859345E-347
        [09] "modulated_cry"          p = 2.4122821127943830737100556735982236394012193223806E-376
        [10] "ascending_moan"         p = 1.22671429781107948628012630568996992218535656E-388
        [11] "bellow"                 p = 4.6600524264821423422581769097493336240670258247000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000389720292770860218512255224180987370277E-426
        [12] "descending_shriek"      p = 6.1282629067548302092192539811208028203875E-668
        [13] "purr"                   p = 3.600160529493104255344598281325635905870572287765323746E-726
        [14] "groan"                  p = 3.0914744707924976946696052403067400078557574E-740
        [15] "croak"                  p = 5.30393471576883204641783751632823545E-866
        [16] "long_grunt"             p = 1.1920801642823916931964632302660718105E-881
        [17] "BB"                     p = 3.29406424042642244223500575910372982E-1083
        [18] "scream"                 p = 6.9289588245814015218628258773215425E-1087
        [19] "tone_w_slight_downsweep" p = 1.43116950208072971536207378207606217E-1088
        [20] "tone"                   p = 1.00000000000000000000000000000000697E-1090
        [21] "upsweep"                p = 1.00000000000000000000000000000000431E-1090
        [22] "moan"                   p = 9.9999999999999999999999999999999697E-1091


      "descending_moan"        sequence (T=175):
        [00] "cry"                    p = 1.0865742429751614371983268723648572634398095375575E-168
        [01] "groan"                  p = 3.79281402042260876155963358568211330621653600671E-174
        [02] "gurgle"                 p = 1.300501633600722847145301491822276712487420855662E-188
      * [03] "descending_moan"        p = 1.22303419098083041702093563220717351453499089E-195
        [04] "purr"                   p = 2.2058164608702414527731429226770226900470980134731174538380494351410522654287213002069360E-209
        [05] "ascending_moan"         p = 8.000515322377344482536164698112696113524684E-214
        [06] "modulated_moan"         p = 2.00156821908385623226231967707575122940472264851921698594E-242
        [07] "ascending_shriek"       p = 4.93252493069696033184235870805098411771586E-414
        [08] "grunt"                  p = 4.220198043080698699824129694501282102205E-562
        [09] "long_grunt"             p = 2.30575509168402445941164534043163372E-564
        [10] "bellow"                 p = 8.81637310915122093500112020589532830000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000115162229509791631873225428624404162E-578
        [11] "trill"                  p = 2.747650596742565156366594253110621670E-614
        [12] "grunts"                 p = 1.9053424151105470821147248234945354671012E-678
        [13] "tone"                   p = 5.518573800711141264398060220970458737351325E-724
        [14] "scream"                 p = 8.3173319913779552215263031688625695410E-763
        [15] "descending_shriek"      p = 2.355952738091326307476100575837954004E-806
        [16] "croak"                  p = 1.33365431937508761393703560147787473E-825
        [17] "growl"                  p = 5.6214884145178383942506711936012384171E-864
        [18] "tone_w_slight_downsweep" p = 1.28809718126937802842426376027973210E-870
        [19] "BB"                     p = 3.76304695096210503905927528419903298E-871
        [20] "modulated_cry"          p = 7.6578473912110488137524382457926498E-874
        [21] "upsweep"                p = 1.00000000000000000000000000000000331E-875
        [22] "moan"                   p = 9.9999999999999999999999999999999845E-876


      "growl"                  sequence (T=96):
        [00] "modulated_cry"          p = 1.63096129306365371794640467797929156633797960443E-68
        [01] "grunts"                 p = 8.59222276716992120357797642526890917121895395320177606806663E-71
      * [02] "growl"                  p = 8.13762672477376550581204958005108547460433912208352157944E-75
        [03] "grunt"                  p = 6.4575577616950113332901016956996312382015307167440489E-81
        [04] "modulated_moan"         p = 7.604026437448384702789716418532493088553942170975526E-88
        [05] "cry"                    p = 9.555156363681329492865358297694925673907779510612696E-111
        [06] "gurgle"                 p = 1.03627142577764689613771873020565758462325100E-111
        [07] "ascending_shriek"       p = 6.87673955137070122523863901628375507059501275E-135
        [08] "trill"                  p = 2.48643548986646279086409366593020021429943940545605E-137
        [09] "descending_moan"        p = 1.0657499011491861670811426037816555089415344504E-137
        [10] "bellow"                 p = 3.095144522689574109036075570946651237504632260000000000000000000000000000000000000000000000000000000000000000000000000000000000519081092671860030232848822246867624958031E-146
        [11] "ascending_moan"         p = 5.562842957090338075131799465764764085473869047006E-163
        [12] "croak"                  p = 8.2933662813892007537842763126568792995376516E-297
        [13] "purr"                   p = 3.4834567164834439999372331420152730201772894972018876636982850128007479148E-322
        [14] "descending_shriek"      p = 2.34885264499197598904017342725351027E-389
        [15] "groan"                  p = 1.65820245690349914092570955537908586E-438
        [16] "long_grunt"             p = 1.32437506550846125333285033815153081E-461
        [17] "scream"                 p = 1.00000000000000000000000000000000810E-480
        [18] "upsweep"                p = 1.00000000000000000000000000000000330E-480
        [19] "tone"                   p = 1.00000000000000000000000000000000183E-480
        [20] "moan"                   p = 9.9999999999999999999999999999999928E-481
        [21] "BB"                     p = 9.9999999999999999999999999999999898E-481
        [22] "tone_w_slight_downsweep" p = 9.9999999999999999999999999999999833E-481


      "upsweep"                sequence (T=140):
        [00] "tone"                   p = 2.389098014639589854383767793521357328969249835947453E-187
        [01] "descending_shriek"      p = 8.5186033953992697922961143522540255E-350
        [02] "BB"                     p = 2.4777949412746993603345670227081984527509571E-498
        [03] "grunts"                 p = 5.5265702454191462216774192140168727311156279E-538
        [04] "gurgle"                 p = 1.633182114326160122737667865171382390E-546
        [05] "grunt"                  p = 2.8885183065594582519935222520847872229624478E-554
        [06] "scream"                 p = 2.52852887547205073300917512830961634E-561
        [07] "ascending_shriek"       p = 8.8320297687504801442802277452018539554758E-603
      * [08] "upsweep"                p = 3.1349049198317674403471382018132372080290363E-611
        [09] "purr"                   p = 2.341692596724733555584468116617867132500000798826639743161387429947300876605506606646E-633
        [10] "descending_moan"        p = 3.34494100155003921787090623208489615206930E-654
        [11] "moan"                   p = 1.08732235235128557177852187538366742E-657
        [12] "ascending_moan"         p = 2.8919088560589076010731392048794287E-670
        [13] "tone_w_slight_downsweep" p = 3.9952776513216343860488556041497665E-694
        [14] "long_grunt"             p = 1.12394351391254284745073924545370877E-698
        [15] "trill"                  p = 2.56623657422940175351966948551497040E-699
        [16] "modulated_moan"         p = 1.166249455271889213819098615462456849E-699
        [17] "cry"                    p = 1.00000000000000000000000000000001126E-700
        [18] "groan"                  p = 1.00000000000000000000000000000000719E-700
        [19] "growl"                  p = 1.00000000000000000000000000000000594E-700
        [20] "bellow"                 p = 9.9999999999999999999999999999999927E-701
        [21] "croak"                  p = 9.9999999999999999999999999999999806E-701
        [22] "modulated_cry"          p = 9.9999999999999999999999999999999299E-701


      "upsweep"                sequence (T=115):
        [00] "tone"                   p = 3.8000634200846567407484210967622091090700657802E-169
      * [01] "upsweep"                p = 1.047327926427537928150525486841915506743788366E-173
        [02] "tone_w_slight_downsweep" p = 1.127068238433464792295332245939878937495152159E-219
        [03] "BB"                     p = 1.251664190821061476265672478519327261244713031E-251
        [04] "grunt"                  p = 1.07953822215804547389154006485896067493300041E-298
        [05] "grunts"                 p = 3.6536812995775895332723365319661727025986786516934980540E-377
        [06] "moan"                   p = 2.70941044579002990528531465550883372243441253E-391
        [07] "trill"                  p = 3.072896806348208274341442462037141188E-414
        [08] "ascending_shriek"       p = 3.02576772945613971734179888786057722E-418
        [09] "purr"                   p = 1.187764169588272711420376087546592301898871911886784860504E-492
        [10] "gurgle"                 p = 5.7973285139159840153829907874488794169095589E-514
        [11] "modulated_moan"         p = 2.459005574705682584465969729486566466E-520
        [12] "descending_moan"        p = 2.91808853770359428406627758657072611E-525
        [13] "cry"                    p = 6.1532787629186174618258797680340015500E-540
        [14] "ascending_moan"         p = 5.19396501758654642596510681747866423E-541
        [15] "groan"                  p = 3.71753953190043393247012691808503691E-559
        [16] "long_grunt"             p = 4.4775087377902734898068568331759207E-571
        [17] "scream"                 p = 1.00000000000000000000000000000000943E-575
        [18] "growl"                  p = 1.00000000000000000000000000000000488E-575
        [19] "descending_shriek"      p = 1.00000000000000000000000000000000418E-575
        [20] "bellow"                 p = 9.9999999999999999999999999999999902E-576
        [21] "croak"                  p = 9.9999999999999999999999999999999852E-576
        [22] "modulated_cry"          p = 9.9999999999999999999999999999999380E-576

    Confusion matrix:
                 actual \ predicted  [00]  [01]  [02]  [03]  [04]  [05]  [06]  [07]  [08]  [09]  [10]  [11]  [12]  [13]  [14]  [15]  [16]  [17]  [18]  [19]  [20]  [21]  [22]  tests  correct  percent
     ------------------------------  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  -----  -------  -------
                          "BB" [00]     6     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     1      7        6   85.71%

              "ascending_moan" [01]     0     1     1     0     0     2     3     0     0     0     0     0     1     0     0     0     3     0     0     0     0     0     0     11        1    9.09%

            "ascending_shriek" [02]     0     1    16     0     2     0     0     4     0     0     0     1     1     0     0     0     1     0     1     0     0     0     0     27       16   59.26%

                      "bellow" [03]     0     0     0     3     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0      3        3  100.00%

                       "croak" [04]     0     0     0     0     3     0     0     0     0     0     1     0     0     0     0     0     0     0     0     0     0     0     0      4        3   75.00%

                         "cry" [05]     0     2     0     0     0     8     1     0     0     0     1     2     1     0     0     0     0     0     0     0     0     1     0     16        8   50.00%

             "descending_moan" [06]     0     2     0     4     0     1    16     0     0     1     0     1     0     0     0     0     4     0     0     0     0     1     0     30       16   53.33%

           "descending_shriek" [07]     0     0     3     0     0     0     0     3     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0      6        3   50.00%

                       "groan" [08]     0     0     0     0     0     2     0     0     4     0     0     0     1     0     0     0     2     0     0     0     0     0     0      9        4   44.44%

                       "growl" [09]     0     0     0     0     0     0     0     0     0     3     0     0     0     0     0     1     0     0     0     0     0     0     0      4        3   75.00%

                       "grunt" [10]     0     1     0     0     1     0     0     0     1     1    22     2     0     0     0     0     1     0     0     0     1     0     0     30       22   73.33%

                      "grunts" [11]     2     0     0     0     0     0     0     0     0     0     0     6     0     0     0     0     0     0     0     0     0     0     0      8        6   75.00%

                      "gurgle" [12]     0     2     1     0     1     3     3     0     3     0     0     2     7     0     0     1     5     1     0     0     0     1     0     30        7   23.33%

                  "long_grunt" [13]     0     0     0     0     0     0     0     0     0     0     0     0     0     3     0     0     0     0     0     0     0     1     0      4        3   75.00%

                        "moan" [14]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     3     0     0     0     0     0     0     0     0      3        3  100.00%

               "modulated_cry" [15]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     5     0     0     0     0     0     0     0      5        5  100.00%

              "modulated_moan" [16]     0     0     0     1     0     6     0     0     0     1     2     1     1     0     0     0    11     0     0     0     0     1     0     24       11   45.83%

                        "purr" [17]     0     0     0     0     0     1     2     0     1     0     1     0     1     1     0     0     0     2     0     0     0     0     0      9        2   22.22%

                      "scream" [18]     0     0     2     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     1     0     0     0     0      3        1   33.33%

                        "tone" [19]     0     0     0     0     0     0     0     0     0     0     1     0     0     0     0     0     0     0     0     4     1     0     0      6        4   66.67%

     "tone_w_slight_downsweep" [20]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     3     0     0      3        3  100.00%

                       "trill" [21]     0     0     3     0     1     0     0     0     0     0     0     0     0     0     0     0     1     0     0     0     0     7     0     12        7   58.33%

                     "upsweep" [22]     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     0     2     0     0     1      3        1   33.33%

     ------------------------------  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  ====  -----  -------  -------
                                        2     8    10     5     5    15     9     4     5     3     6     9     6     1     0     2    17     1     1     2     2     5     1    257      138   53.70%

