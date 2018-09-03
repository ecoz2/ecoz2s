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

## linear prediction

On all signals corresponding to a class name:

    $ mill vpl.run groan

On a single given signal:

    $ mill vpl.run groan groan/from_HBSe_20151207T070326__177.84088_178.81975.wav
