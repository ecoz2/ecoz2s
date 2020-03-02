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

    var i = 0
    while (i < N) {
      alpha(0)(i) = pi(i) * B(i)(O(0))
      i += 1
    }

    var t = 1
    while (t < T) {
      var j = 0
      while (j < N) {
        var sum = BigDecimal(0)
        i = 0
        while (i < N) {
          sum += alpha(t - 1)(i) * A(i)(j)
          i += 1
        }
        alpha(t)(j) = B(j)(O(t)) * sum
        j += 1
      }
      t += 1
    }

    alpha(T - 1).sum
  }

  def genQopt(seq: SymbolSequence): (Array[Int], BigDecimal) = {
    val O = seq.symbols
    val T = seq.T

    val delta = Array.fill[BigDecimal](T, N)(0)
    val psi   = Array.fill[Int](T, N)(0)

    var i = 0
    while (i < N) {
      delta(0)(i) = pi(i) * B(i)(O(0))
      psi(0)(i) = 0
      i += 1
    }

    var argMax = 0

    var t = 1
    while (t < T) {
      var j = 0
      while (j < N) {
        var deltaA = BigDecimal(0)
        i = 0
        while (i < N) {
          val deltaAcomp = delta(t - 1)(i) * A(i)(j)
          if ( deltaAcomp > deltaA ) {
            deltaA = deltaAcomp
            argMax = i
          }
          i += 1
        }
        delta(t)(j) = B(j)(O(t)) * deltaA
        psi(t)(j) = argMax
        j += 1
      }
      t += 1
    }

    var prob = BigDecimal(0)
    i = 0
    while (i < N) {
      if (delta(T - 1)(i) > prob) {
        prob = delta(T - 1)(i)
        argMax = i
      }
      i += 1
    }

    val Qopt = new Array[Int](T)
    Qopt(T - 1) = argMax
    t = T - 2
    while (t >= 0) {
      Qopt(t) = psi(t + 1)(Qopt(t + 1))
      t -= 1
    }

    (Qopt, prob)
  }

  def estimateB(sequences: List[SymbolSequence]): Unit = {
    // to capture state-symbol occurrences:
    val B2 = Array.fill[BigDecimal](N, M)(0)
    val vec_est = Array.fill[Int](N)(0)

    for (seq <- sequences) {
      val O = seq.symbols
      val T = seq.T

      val (qOpt, _) = genQopt(seq)

      var t = 0
      while (t < T) {
        B2 ( qOpt(t) ) ( O(t) ) += 1

        vec_est ( qOpt(t) ) += 1

        t += 1
      }
    }

    // now, update B:

    val one = BigDecimal(1)

    var j = 0
    while (j < N) {
      if (vec_est(j) == 0) {
        // no symbols emitted by state j.
        // set uniform distribution:
        var k = 0
        while (k < M) {
          B(j)(k) = one / M
          k += 1
        }
      }
      else {
        var k = 0
        while (k < M) {
          B(j)(k) = B2(j)(k) / vec_est(j)
          k += 1
        }
      }
      j += 1
    }
  }

  def adjustB(ε: BigDecimal): Unit = {
    require(ε > 0)
    var j = 0
    while (j < N) {
      var defect = BigDecimal(0)
      var excess = BigDecimal(0)

      var k = 0
      while (k < M) {
        val Bjk = B(j)(k)
        if (Bjk < ε) {
          defect += ε - Bjk
          B(j)(k) = ε
        }
        else {
          excess += Bjk - ε
        }
        k += 1
      }
      // now, all B(j)(k) >= ε.
      // Reestablish normalized distribution:
      k = 0
      while (k < M) {
        B(j)(k) -= ((B(j)(k) - ε) / excess) * defect
        k += 1
      }
      j += 1
    }
  }

  override def toString: String = {
    val classNameStr = classNameOpt.map(b => s""""$b"""").getOrElse("(unknown)")

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

