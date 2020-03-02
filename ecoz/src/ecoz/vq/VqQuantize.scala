package ecoz.vq

import java.io.File

import ecoz.config.Config.dir
import ecoz.lpc.Predictors
import ecoz.rpt.magenta
import ecoz.symbol.SymbolSequences


object VqQuantize {

  def usage(error: String = ""): Unit = {
    println(s"""$error
       |
       | vq.quantize - Vector quantization
       |
       | vq.quantize [options] <predictor> ...
       |
       | Options:
       |   -cb <codebook>    Codebook to be used
       |
       | Sequences are generated under ${dir.sequences}/.
     """.stripMargin)
      sys.exit()
  }


  def main(args: List[String]): Unit = {
    var cbFilenameOpt: Option[String] = None
    var prdFilenames = List[String]()

    def processArgs(opts: List[String]): Unit = {
      opts match {
        case "-cb" :: cb :: rest =>
          cbFilenameOpt = Some(cb)
          processArgs(rest)

        case opt :: _ if opt.startsWith("-") =>
          usage(s"unrecognized option: $opt")

        case filenames =>
          prdFilenames = filenames
      }
    }
    processArgs(args)
    if (cbFilenameOpt.isEmpty) {
      usage("indicate codebook")
    }
    if (prdFilenames.isEmpty) {
      usage("indicate predictor files to be quantized")
    }

    val cbFilename = cbFilenameOpt.get

    println(
      s"""codebook    : $cbFilename
         |predictors  : ${prdFilenames.length}
       """.stripMargin)

    val codebook = Codebooks.load(new File(cbFilename))

    var numSeqs = 0
    var totalDist = 0F

    prdFilenames foreach { prdFilename =>
      val prdFile = new File(prdFilename)
      val predictor = Predictors.load(prdFile)
      val sequence = codebook.quantize(predictor)
      totalDist += sequence.distortion.get
      numSeqs += 1
      println(s"\n$prdFilename:\n  " + sequence)

      val destDir = {
        if (prdFilename.contains("TRAIN/")) {
          new File(dir.sequences, s"TRAIN")
        }
        else if (prdFilename.contains("TEST/")) {
          new File(dir.sequences, s"TEST")
        }
        else dir.sequences
      }

      val seqFilename = prdFile.getName.replaceFirst("\\.[^.]*$", ".seq")

      val seqFile = {
        val baseDir = new File(destDir, s"M%d" format sequence.M)
        val className = predictor.classNameOpt.getOrElse("UNKNOWN")
        val seqDir = new File(baseDir, className)
        seqDir.mkdirs()
        new File(seqDir, seqFilename)
      }

      SymbolSequences.save(sequence, seqFile)
    }

    if ( numSeqs > 0 ) {
      val d = totalDist / numSeqs
      printf("\nTotal: %d sequences; average distortion = %s\n",
        numSeqs, magenta(d.toString))
    }
  }
}
