package ecoz.config
import java.io.File

import ecoz.hmm.HmmType


object Config {
  object dir {
    val workspace = new File("data")
    val signals = new File(workspace, "signals")
    val predictors = new File(workspace, "predictors")
    val codebooks = new File(workspace, "codebooks")
    val sequences = new File(workspace, "sequences")
    val hmms = new File(workspace, "hmms")

    signals.mkdirs()
    predictors.mkdirs()
    codebooks.mkdirs()
    sequences.mkdirs()
    hmms.mkdirs()
  }

  object lpa {
    //  P ≈ f_s / 1000 + γ  (Parsons, p. 164)
    // So, in the order of 32+ for f_s=32K

    val P = 36

    // TODO to be determined:
    val windowLengthMs = 45
    val offsetLengthMs = 15
  }

  val maxCodebookSize = 2048

  object hmm {
    val typ = HmmType.Cascade3
    val N = 12
    val valAuto = BigDecimal(0.3)

    val trickMaxT = 50
  }
}
