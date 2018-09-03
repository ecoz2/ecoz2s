package ecoz.hmm

import java.io.File

import ecoz.config.Config
import ecoz.config.Config.dir
import ecoz.hmm.HmmType.HmmType
import ecoz.rpt.magenta
import ecoz.symbol.{SymbolSequence, SymbolSequences}

object HmmLearn {

  def usage(error: String = ""): Unit = {
    println(s"""$error
       |
       | hmm.learn - Trains an HMM according to given training symbols sequences
       |
       | hmm.learn [options] <sequence> ...
       |
       | Options:
       |   -w className           taken from first sequence
       |   -N #                   number of states
       |   -t <type>
       |   -e epsilon             value for epsilon restriction on B
       |   -I #                   num refinements
       |
       | Trained HMM is generated under ${dir.hmms}/.
     """.stripMargin)
    sys.exit()
  }

  def main(args: List[String]): Unit = {
    var classNameOpt: Option[String] = None
    var N = 5
    var ε = BigDecimal(1e-5)
    var typ = HmmType.default
    var seqFilenames = List[String]()
    var maxRefinements = -1
    var valAuto = BigDecimal(0.3)

    def processArgs(opts: List[String]): Unit = {
      opts match {
        case "-w" :: name :: rest ⇒
          classNameOpt = Some(name)
          processArgs(rest)

        case "-N" :: numStates :: rest ⇒
          N = numStates.toInt
          require(N > 0)
          processArgs(rest)

        case "-t" :: t :: rest ⇒
          typ = HmmType.withName(t)
          processArgs(rest)

        case "-e" :: eps :: rest ⇒
          ε = BigDecimal(eps.toDouble)
          require(ε > 0)
          processArgs(rest)

        case "-R" :: num :: rest ⇒
          maxRefinements = num.toInt
          processArgs(rest)

        case "-a" :: a :: rest ⇒
          valAuto = BigDecimal(a.toDouble)
          processArgs(rest)

        case opt :: _ if opt.startsWith("-") ⇒
          usage(s"unrecognized option: $opt")

        case filenames ⇒
          seqFilenames = filenames
      }
    }
    processArgs(args)

    if (seqFilenames.isEmpty) {
      usage("Indicate sequence files for training")
    }
    val sequences = seqFilenames map { filename ⇒
      SymbolSequences.load(new File(filename))
    }
    println(s"loaded ${sequences.length} sequences as training set")

    val classNameOpt2: Option[String] = classNameOpt orElse {
      sequences.map(_.classNameOpt).find(_.isDefined).flatten
    }
    if (classNameOpt2.isEmpty) {
      usage("No class name indicated (either explicitly or from given sequences)")
    }
    val className = classNameOpt2.get

    val M = sequences.head.M

    // verify all sequences are conformant wrt vocabulary size:
    val nonM = sequences.filterNot(_.M == M)
    if (nonM.nonEmpty) {
      usage(s"Error: ${nonM.length} non conformant sequences wrt to 1st one, M != $M")
    }

    val hmm = new HmmLearn(
      className, N, M, typ, ε,
      sequences,
      maxRefinements,
      valAuto
    ).learn()

    saveHmm(className, hmm)
  }

  def saveHmm(className: String, hmm: Hmm): Hmm = {
    val hmmFilename = s"$className.hmm"
    val hmmFile = new File(Config.dir.hmms, hmmFilename)
    Hmms.save(hmm, hmmFile)
    hmm
  }
}

class HmmLearn(className: String,
               N: Int,
               M: Int,
               typ: HmmType,
               ε: BigDecimal,
               sequences: List[SymbolSequence],
               maxRefinements: Int,
               valAuto: BigDecimal
             ) {

  println(
    s"""className : "$className"
       |        N : $N
       |        M : $M
       |     type : $typ
       |        ε : $ε
       |  maxRefs : $maxRefinements
       |  valAuto : $valAuto
       |sequences : ${sequences.length}
       """.stripMargin)

  private val maxT = sequences.map(_.T).max

  private val hmm = Hmms.create(className, N, M, typ)
  private val pi = hmm.pi
  private val A = hmm.A
  private val B = hmm.B

  // adjust initial B based on given training sequences:
  hmm.estimateB(sequences)
  hmm.adjustB(ε)

  //sequences foreach SymbolSequences.showSequence
  println(s"Initial HMM: $hmm")

  def learn(): Hmm = {

    def refinementStep(): Unit = {
      // accumulators for numerators/denominators
      val numA = Array.fill[BigDecimal](N, N)(0)
      val numB = Array.fill[BigDecimal](N, M)(0)
      val denA = Array.fill[BigDecimal](N)(0)
      val denB = Array.fill[BigDecimal](N)(0)
      val f_pi = Array.fill[BigDecimal](N)(0)

      val alpha = Array.ofDim[BigDecimal](maxT, N)
      val beta  = Array.ofDim[BigDecimal](maxT, N)
      val gamma = Array.ofDim[BigDecimal](maxT, N)


      // process each sequence:
      for (seq ← sequences) {

        val pO = gen_alpha_beta_pO(seq)
        gen_gamma(seq, pO)

        // accumulate frequencies for pi:
        gen_pi()

        // accumulate frequencies for A and B:
        gen_numA(seq, pO)
        gen_denA_denB(seq)
        gen_numB(seq)
      }

      // update parameters:
      if (typ != HmmType.Cascade3 && typ != HmmType.Cascade2) {
        refine_pi()
      }
      refine_A()
      refine_B()

      def gen_alpha_beta_pO(seq: SymbolSequence): BigDecimal = {
        val O = seq.symbols
        val T = seq.T

        // Alpha:
        for (i ← 0 until N) {
          alpha(0)(i) = pi(i) * B(i)(O(0))
        }
        for (t ← 1 until T) {
          for (j ← 0 until N) {
            var sum = BigDecimal(0)
            for (i ← 0 until N) {
              sum += alpha(t - 1)(i) * A(i)(j)
            }
            alpha(t)(j) = sum * B(j)(O(t))
          }
        }

        // Beta:
        for (i ← 0 until N) {
          beta(T - 1)(i) = BigDecimal(1)
        }
        for (t ← T - 2 to 0 by -1) {
          for (i ← 0 until N) {
            var sum = BigDecimal(0)
            for (j ← 0 until N) {
              sum += A(i)(j) * B(j)(O(t + 1)) * beta(t + 1)(j)
            }
            beta(t)(i) = sum
          }
        }

        // probability of the sequence:
        var prob = BigDecimal(0)
        for (i ← 0 until N) {
          prob += alpha(T - 1)(i)
        }
        prob
      }

      def gen_gamma(seq: SymbolSequence, pO: BigDecimal): Unit = {
        for (t ← 0 until seq.T) {
          for (i ← 0 until N) {
            gamma(t)(i) = alpha(t)(i) * beta(t)(i) / pO
          }
        }
      }

      def gen_pi(): Unit = {
        for (i ← 0 until N) {
          f_pi(i) += gamma(0)(i)
        }
      }

      def gen_numA(seq: SymbolSequence, pO: BigDecimal): Unit = {
        val O = seq.symbols
        val T = seq.T

        for (i ← 0 until N) {
          for (j ← 0 until N) {
            var value = BigDecimal(0)
            for (t ← 0 until T - 1) {
              value += alpha(t)(i) * B(j)(O(t + 1)) * beta(t + 1)(j)
            }
            value *= A(i)(j) / pO
            numA(i)(j) += value
          }
        }
      }

      def gen_denA_denB(seq: SymbolSequence): Unit = {
        val T = seq.T

        for (i ← 0 until N) {
          var value = BigDecimal(0)
          for (t ← 0 until T - 1) {
            value += gamma(t)(i)
          }
          denA(i) += value
          denB(i) += gamma(T - 1)(i) + value
        }
      }

      def gen_numB(seq: SymbolSequence): Unit = {
        val O = seq.symbols
        val T = seq.T

        for (j ← 0 until N) {
          for (k ← 0 until M) {
            var value = BigDecimal(0)
            for (t ← 0 until T) {
              if (O(t) == k) {
                value += gamma(t)(j)
              }
            }
            numB(j)(k) += value
          }
        }
      }

      def refine_pi(): Unit = {
        for (i ← 0 until N) {
          pi(i) = f_pi(i) / sequences.size
        }
      }

      def refine_A(): Unit = {
        for (i ← 0 until N) {
          for (j ← 0 until N) {
            A(i)(j) = numA(i)(j) / denA(i)
          }
        }
      }

      def refine_B(): Unit = {
        for (j ← 0 until N) {
          for (k ← 0 until M) {
            B(j)(k) = numB(j)(k) / denB(j)
          }
        }
        hmm.adjustB(ε)
      }
    }

    var PROld = showSeqProbabilities("Initial sequence probabilities:", hmm)

    var continue = true
    var r = 0
    while (continue) {
      r += 1

      refinementStep()

      val PR = showSeqProbabilities(s"After refinement $r:", hmm)

      val change = (PR - PROld) / PR
      println(s"CHANGE = " + magenta(change.toString()))
      PROld = PR

      continue = change > valAuto || r < maxRefinements
    }

    println(s"Trained HMM after $r refinements: $hmm")

    hmm
  }

  def showSeqProbabilities(msg: String, hmm: Hmm): BigDecimal = {
    println(msg)
    var PR = BigDecimal(1)
    for ( r ← sequences.indices) {
      val prob = hmm.probability(sequences(r))
      println(" %03d: %s" format (r, prob))
      PR *= prob
    }
    println("   Product = %s\n" format PR)
    PR
  }
}
