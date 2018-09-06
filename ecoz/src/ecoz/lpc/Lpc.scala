package ecoz.lpc

import java.io.File

import ecoz.config.Config.dir
import ecoz.signal.Signals


object Lpc {

  def usage(error: String = ""): Unit = {
    println(s"""$error
       |
       | lpc - LP coding
       |
       | lpc -classes <className> ...
       | lpc -signals <wav-file> ...
       |
       | Predictor files are generated under ${dir.predictors}.
       | These files are processed by vq.learn for codebook generation
       | and by vq.quantize for quantization.
     """.stripMargin)
      sys.exit()
  }

  def main(args: Array[String]): Unit = {
    var classNames: List[String] = List.empty
    var wavFilenames: List[String] = List.empty

    def processArgs(opts: List[String]): Unit = opts match {
      case "-classes" :: c1 :: moreClasses ⇒
        classNames = c1 :: moreClasses

      case "-signals" :: s1 :: moreSignals ⇒
        wavFilenames = s1 :: moreSignals

      case opt :: _ ⇒
        usage(s"missing argument or unrecognized option: $opt")

      case Nil ⇒
        usage()
    }
    processArgs(args.toList)

    val lpa = new Lpa()

    val classAndWavFiles: List[(String, List[File])] = if (classNames.nonEmpty) {
      classNames map { className ⇒
        val classDir = new File(className)
        (classDir.getName, classDir.listFiles().toList)
      }
    }
    else {
      // get parent's name as className, eg:
      // if fn == ".../.../foo/bar.wav', then className = foo
      val names = wavFilenames map { fn ⇒ new File(fn) }
      val groups = names.groupBy { n ⇒
        val path = n.getPath
        val slash = path.lastIndexOf('/')
        if (slash >= 0) {
          val slashPrev = path.lastIndexOf('/', slash - 1)
          if (slashPrev >= 0)
            path.substring(slashPrev + 1, slash)
          else ""
        }
        else ""
      }
      groups.toList
    }

    classAndWavFiles foreach { case (className, wavFiles) ⇒
      wavFiles foreach { processFile(className, _) }
    }

    def processFile(className: String, wavFile: File): Unit = {
      var signal = Signals.load(wavFile)

      println(s"\nProcessing: $wavFile")
      //println(s" Samples : ${signal.x.length}")
      //println(s" Mean: ${signal.mean}")

      if (signal.mean != 0) {
        //println(s" - removing mean...")
        signal = signal.zeroMean
      }

      //println(s" - applying preemphasis...")
      signal = signal.preemphasis
      //println(s" Mean after preemphasis: ${signal.mean}")

      val results = try lpa.onSignal(signal)
      catch {
        case e: LpaException ⇒
          println(s"problem with lpa.onSignal: ${e.getMessage}")
          return
      }

      // note: we save the autocorrelations `.r` as predictor file
      val predictor = Predictor(
        vectors = results.map(_.r).toArray,
        classNameOpt = Some(className)
      )

      val prdFile = {
        val baseDir = new File(dir.predictors, s"P%d" format lpa.P)
        val dirPrd = new File(baseDir, className)
        dirPrd.mkdirs()
        val prdFilename = wavFile.getName.replaceFirst("\\.[^.]*$", ".prd")
        new File(dirPrd, prdFilename)
      }
      Predictors.save(predictor, prdFile)
      println(s" saved $prdFile")
    }
  }

  def lpaOnSignals(wavFilenames: List[String]): Unit = {
    val lpa = new Lpa()
    wavFilenames foreach lpcSignal

    def lpcSignal(wavFilename: String): Unit = {
      val signal = Signals.load(new File(wavFilename))
      println(s"\n$wavFilename:")
      try {
        val results = lpa.onSignal(signal)
        println(s"LPC predictor vectors ${results.length}: ")
        results foreach { res ⇒
          println("  r = " + res.rc.mkString("[", ", ", "]"))
          println(" rc = " + res.rc.mkString("[", ", ", "]"))
          println("  a = " + res.a.mkString("[", ", ", "]"))
          println(" pe = " + res.pe)
          println()
        }
      }
      catch {
        case e: LpaException ⇒
          println(s"problem with lpa.onSignal: ${e.getMessage}")
      }
    }
  }

  def showPredictors(prdFilenames: List[String]): Unit = {
    prdFilenames foreach showPredictor
  }

  def showPredictor(prdFilename: String): Unit = {
    val predictor = Predictors.load(new File(prdFilename))
    println(s"\n$prdFilename:")
    showPredictor(predictor)
  }

  def showPredictor(predictor: Predictor): Unit = {
    val T = predictor.vectors.length
    val P = predictor.vectors.head.length - 1
    val classNameStr = predictor.classNameOpt.map(b ⇒ s""""$b"""").getOrElse("(unknown)")
    println(s""" className=$classNameStr T=$T P=$P""")
    predictor.vectors.zipWithIndex foreach { case (vector, index) ⇒
      println(s" %2d: %s" format (index, vector.mkString("[", ", ", "]")))
    }
  }
}