package ecoz.hmm

import ecoz.symbol.SymbolSequence

case class Hmm(
                pi: Array[BigDecimal],
                A: Array[Array[BigDecimal]],
                B: Array[Array[BigDecimal]],
                classNameOpt: Option[String] = None
              ) {
  val N: Int = pi.length
  val M: Int = B.head.length

  def probability(seq: SymbolSequence): BigDecimal = {
    val O = seq.symbols
    val T = seq.T
    val alpha = Array.ofDim[BigDecimal](T, N)

    for (i ← 0 until N) {
      alpha(0)(i) = pi(i) * B(i)(O(0))
    }

    for (t ← 1 until T) {
      for (j ← 0 until N) {
        var sum = BigDecimal(0)
        for (i ← 0 until N) {
          sum += alpha(t - 1)(i) * A(i)(j)
        }
        alpha(t)(j) = B(j)(O(t)) * sum
      }
    }
    var prob = BigDecimal(0)
    for (i ← 0 until N) {
      prob += alpha(T - 1)(i)
    }
    prob
  }

  def genQopt(seq: SymbolSequence): (Array[Int], BigDecimal) = {
    val O = seq.symbols
    val T = seq.T

    val delta = Array.fill[BigDecimal](T, N)(0)
    val psi   = Array.fill[Int](T, N)(0)

    for (i ← 0 until N) {
      delta(0)(i) = pi(i) * B(i)(O(0))
      psi(0)(i) = 0
    }

    var argMax = 0

    for (t ← 1 until T) {
      for (j ← 0 until N) {
        var deltaA = BigDecimal(0)
        for (i ← 0 until N) {
          val deltaAcomp = delta(t - 1)(i) * A(i)(j)
          if ( deltaAcomp > deltaA ) {
            deltaA = deltaAcomp
            argMax = i
          }
        }
        delta(t)(j) = B(j)(O(t)) * deltaA
        psi(t)(j) = argMax
      }
    }

    var prob = BigDecimal(0)
    for (i ← 0 until N) {
      if (delta(T - 1)(i) > prob) {
        prob = delta(T - 1)(i)
        argMax = i
      }
    }

    val Qopt = new Array[Int](T)
    Qopt(T - 1) = argMax
    for (t ← T - 2 to 0 by -1) {
      Qopt(t) = psi(t + 1)(Qopt(t + 1))
    }

    (Qopt, prob)
  }

  def estimateB(sequences: List[SymbolSequence]): Unit = {
    // to capture state-symbol occurrences:
    val B2 = Array.fill[BigDecimal](N, M)(0)
    val vec_est = Array.fill[Int](N)(0)

    for (seq ← sequences) {
      val O = seq.symbols
      val T = seq.T

      val (qOpt, _) = genQopt(seq)

      for (t ← 0 until T) {
        B2 ( qOpt(t) ) ( O(t) ) += 1

        vec_est ( qOpt(t) ) += 1
      }
    }

    // now, update B:

    val one = BigDecimal(1)

    for (j ← 0 until N) {
      if (vec_est(j) == 0) {
        // no symbols emitted by state j.
        // set uniform distribution:
        for (k ← 0 until M) {
          B(j)(k) = one / M
        }
      }
      else {
        for (k ← 0 until M) {
          B(j)(k) = B2(j)(k) / vec_est(j)
        }
      }
    }
  }

  def adjustB(ε: BigDecimal): Unit = {
    require(ε > 0)
    for (j ← 0 until N) {
      var defect = BigDecimal(0)
      var excess = BigDecimal(0)

      for (k ← 0 until M) {
        val Bjk = B(j)(k)
        if (Bjk < ε) {
          defect += ε - Bjk
          B(j)(k) = ε
        }
        else {
          excess += Bjk - ε
        }
      }
      // now, all B(j)(k) >= ε.
      // Reestablish normalized distribution:
      for (k ← 0 until M) {
        B(j)(k) -= ((B(j)(k) - ε) / excess) * defect
      }
    }
  }

  override def toString: String = {
    val classNameStr = classNameOpt.map(b ⇒ s""""$b"""").getOrElse("(unknown)")

    def distribution(dist: Array[BigDecimal]): String = {
      dist.mkString("[", ", ", "]")
    }

    s"""className=$classNameStr N=$N M=$M
       |pi = ${distribution(pi)}
       | A = ${distribution(A(0))}
       |     ${A.tail.map(distribution).mkString("\n     ")}
       | B = ${distribution(B(0))}
       |     ${B.tail.map(distribution).mkString("\n     ")}
     """.stripMargin
  }
}

object HmmType extends Enumeration {
  type HmmType = Value

  val Cascade3, Cascade2, Random, Uniform = Value

  val default: HmmType = Random
}

