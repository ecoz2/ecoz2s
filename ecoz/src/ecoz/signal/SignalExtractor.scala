package ecoz.signal

import java.io.File

import com.github.tototoshi.csv._
import de.sciss.synth.io._
import ecoz.config.Config.dir


object SignalExtractor {

  def main(args: Array[String]): Unit = {
    if (args.length < 2) {
      println("USAGE: <wav-file> <selection-file> description ...")
      return
    }

    val wavFile = new File(args(0))
    val selectionFile = new File(args(1))
    val descriptions = args.drop(2)

    val wavFileSimpleName = wavFile.getName.replaceFirst("\\.[^.]*$", "")

    println(s"Reading audio file: $wavFile")
    val in = AudioFile.openRead(wavFile)
    pprint.pprintln(in)
    val duration = in.numFrames / in.sampleRate
    println(f"sampleRate: ${in.sampleRate}%f Hz  duration: $duration%f secs")
    val samplePeriod = 1.0 / in.sampleRate

    println(s"Loading selection file: $selectionFile")
    val allSelections = readSelections(selectionFile)
    //println("allSelections="); pprint.pprintln(allSelections)

    println("  %30s : %s".format(
      "description",
      "#selections"))
    val selByDescription: Map[String, Seq[Selection]] = allSelections.groupBy(_.description)
    val allSortedDescriptions = selByDescription.keys.toList.sorted
    allSortedDescriptions foreach { description ⇒
        println("  %30s : %2d".format(
          "\"" + description + "\"",
          selByDescription(description).length))
    }

    if (descriptions.nonEmpty) {
      val actualDescriptions = if (descriptions.contains("--all")) {
        allSortedDescriptions
      }
      else descriptions.toList

      actualDescriptions foreach { description ⇒
        selByDescription.get(description) foreach extractSelections
      }
    }
    else {
      println("USAGE: <wav-file> <selection-file> description ...")
    }

    in.close()

    def extractSelections(selections: Seq[Selection]): Unit = {
      val bufSize = 8192  // perform operations in blocks of this size
      val buf = in.buffer(bufSize)

      selections foreach extractSelection

      def extractSelection(s: Selection): Unit = {
        //print("extractSelection: s="); pprint.pprintln(s)

        val outputDirName = if (s.description.trim.isEmpty) "_"
        else s.description.trim.replaceAll("""/|\\""", "_")
        val outputDir = new File(dir.signals, outputDirName)
        outputDir.mkdirs()

        val outName = s"from_${wavFileSimpleName}__${s.beginTimeSecs}_${s.endTimeSecs}.wav"
        val outFile = new File(outputDir, outName)
        val out = AudioFile.openWrite(outFile, in.spec)

        val posBeg = position(s.beginTimeSecs)
        val posEnd = position(s.endTimeSecs)
        var remain = posEnd - posBeg

        in.seek(posBeg)
        while (remain > 0) {
          val chunkLen = math.min(bufSize, remain).toInt
          in.read(buf, 0, chunkLen)
          out.write(buf, 0, chunkLen)
          remain -= chunkLen
        }
        out.close()

        println(f"Selection extracted: $outFile")

      }

      def position(timeSecs: Float): Long = (timeSecs / samplePeriod).toLong
    }
  }


  def readSelections(selectionPath: File): Seq[Selection] = {
    val reader = CSVReader.open(selectionPath)(new TSVFormat {})
    val allLines = reader.all
    val headerCols = allLines.head
    print("Header: ")
    pprint.pprintln(headerCols)
    val begTimeIndex = headerCols.indexOf("Begin Time (s)")
    val endTimeIndex = headerCols.indexOf("End Time (s)")
    val descriptionIndex = headerCols.indexOf("Description (Dunlop & Fournet)")

    allLines.drop(1) map { columns ⇒
      val begTime = columns(begTimeIndex).toFloat
      val endTime = columns(endTimeIndex).toFloat
      val description = columns(descriptionIndex).trim
      //pprint.pprintln(columns)
      Selection(begTime, endTime, description)
    }
  }
}

case class Selection(
                      beginTimeSecs: Float,
                      endTimeSecs: Float,
                      description: String
                    )
