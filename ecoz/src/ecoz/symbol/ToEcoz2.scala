package ecoz.symbol

import java.io._

/**
  * Ad hoc conversion tool...
  */
object ToEcoz2 {

  case class SeqAndFile(seq: SymbolSequence, file: File)

  def convertSequences(filenames: List[String]): Unit = {
    val sequences = filenames map { fn ⇒
      val file = new File(fn)
      SeqAndFile(SymbolSequences.load(file), file)
    }

    val namedSequences = sequences filter { seq ⇒
      seq.seq.classNameOpt.isDefined
    }

    val byClassName = namedSequences.groupBy(_.seq.classNameOpt.get)

    val sortedClassNames = byClassName.keys.toSeq.sorted

    for ( (className, classNumber) ← sortedClassNames.zipWithIndex ) {
      val seqs = byClassName(className)

      seqs foreach { case SeqAndFile(seq, file) ⇒
        val cadFilename = file.getPath.replaceFirst("\\.[^.]*$", ".cad")
        val cadFile = new File(cadFilename)
        saveAsCadena(seq, classNumber.asInstanceOf[Short], cadFile)
      }
    }

    def saveAsCadena(sequence: SymbolSequence, classNumber: Short, file: File): Unit = {
      val s = new DataOutputStream(new FileOutputStream(file))

      s.writeBytes("<cadena>\u001a      ") // 15 bytes

      // "word" as a short
      writeShort(classNumber)

      // number of symbols:
      writeShort(sequence.T.asInstanceOf[Short])

      // vocabulary size:
      writeShort(sequence.M.asInstanceOf[Short])

      sequence.symbols foreach { symbol ⇒
        val short = symbol.asInstanceOf[Short]
        assert(0 <= short && short < sequence.M, s"symbol=$short but M=${sequence.M}")
        writeShort(short)
      }

      s.close()
      println(s"cadena saved: $file")

      def writeShort(short: Short): Unit = {
        val b0 = short & 0xff
        val b1 = (short >> 8) & 0xff
        s.writeByte(b0)
        s.writeByte(b1)
      }
    }
  }

}
