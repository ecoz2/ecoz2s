package ecoz.signal

import java.io.File

import com.github.tototoshi.csv._

case class Selection(
                      beginTimeSecs: Float,
                      endTimeSecs: Float,
                      description: Option[String] = None
                    )

/**
  * Helper to ingest a Raven selection file (very basic, adjusted as needed).
  */
object CsvSelection {

  def main(args: Array[String]): Unit = {
    if (args.length != 1) {
      println("USAGE: ecoz sig.raven_selections <selection-file>")
      return
    }

    val selectionFile = new File(args(0))
    println(s"Loading selection file: $selectionFile")
    val allSelections = readSelections(selectionFile)
    println("allSelections="); pprint.pprintln(allSelections)
  }

  def readSelections(selectionPath: File, includingDescription: Boolean = false): Seq[Selection] = {
    val reader = CSVReader.open(selectionPath)(new TSVFormat {})
    val allLines = reader.all
    val headerCols = allLines.head
    print("Header: "); pprint.pprintln(headerCols)
    val begTimeIndex = headerCols.indexOf("Begin Time (s)")
    val endTimeIndex = headerCols.indexOf("End Time (s)")

    val descriptionIndex = if (includingDescription) {
      val Dunlop_Fournet = headerCols.indexWhere(_.startsWith("Description (Dunlop & Fournet)"))
      if (Dunlop_Fournet > 0) {
        // the case of data/songs/selections/20151207T070326.txt
        Dunlop_Fournet
      }
      else {
        // all other 9 selection files
        headerCols.indexOf("Description")
      }
    }
    else -1

    require(begTimeIndex >= 0, s"""expected column: "Begin Time (s)" """)
    require(endTimeIndex >= 0, s"""expected column: "End Time (s)" """)

    if (includingDescription) {
      require(descriptionIndex >= 0, s"""expected column: "Description [(Dunlop & Fournet)]" """)
    }

    val sels = allLines.drop(1).map { columns =>
      val begTime = columns(begTimeIndex).toFloat
      val endTime = columns(endTimeIndex).toFloat
      val description = if (includingDescription) Some(columns(descriptionIndex).trim) else None
      //pprint.pprintln(columns)
      Selection(begTime, endTime, description)
    }

    if (includingDescription) sels.filter(_.description.nonEmpty)
    else sels
  }
}
