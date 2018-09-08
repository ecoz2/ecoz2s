package ecoz.hmm

import java.io.File

import ecoz.config.Config
import ecoz.config.Config.dir
import ecoz.hmm.HmmType.HmmType
import ecoz.rpt.magenta
import ecoz.symbol.{SymbolSequence, SymbolSequences}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration

object HmmLearn {
  private val defaultN = 5
  private val default_ε = BigDecimal(1e-5)
  private val defaultType = HmmType.default
  private val defaultMaxRefinements = -1
  private val defaultValAuto = BigDecimal(0.3)

  def usage(error: String = ""): Unit = {
    println(s"""$error
       |
       | hmm.learn - Trains an HMM
       |
       | hmm.learn [options] <sequence> ...
       |
       | Options:
       |   -w className      name for the hmm (taken from first sequence)
       |   -N #              number of states  ($defaultN)
       |   -t <type>         hmm type ($defaultType)
       |   -e epsilon        value for epsilon restriction on B ($default_ε)
       |   -R #              max number of refinements ($defaultMaxRefinements)
       |   -a <auto>         threshold to complete training ($defaultValAuto)
       |
       | Trained HMM is generated under ${dir.hmms}/.
     """.stripMargin)
    sys.exit()
  }

  def main(args: List[String]): Unit = {
    var N = defaultN
    var ε = default_ε
    var typ = defaultType
    var maxRefinements = defaultMaxRefinements
    var valAuto = defaultValAuto
    var classNameOpt: Option[String] = None
    var seqFilenames = List[String]()

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

    new HmmLearn(
      className, N, M, typ, ε,
      sequences,
      maxRefinements,
      valAuto
    ).learn()
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

  private val hmmFile = {
    val hmmDir = new File(Config.dir.hmms, s"N%d__M%d" format (hmm.N, hmm.M))
    hmmDir.mkdirs()
    new File(hmmDir, s"$className.hmm")
  }

  //sequences foreach SymbolSequences.showSequence
  //println(s"Initial HMM: $hmm")

  // accumulators for numerators/denominators
  private val numA = Array.fill[BigDecimal](N, N)(0)
  private val numB = Array.fill[BigDecimal](N, M)(0)
  private val denA = Array.fill[BigDecimal](N)(0)
  private val denB = Array.fill[BigDecimal](N)(0)
  private val f_pi = Array.fill[BigDecimal](N)(0)

  private val alpha = Array.ofDim[BigDecimal](maxT, N)
  private val beta  = Array.ofDim[BigDecimal](maxT, N)
  private val gamma = Array.ofDim[BigDecimal](maxT, N)

  def learn(): Hmm = {

    def refinementStep(): Unit = {
      // initialize accumulators for numerators/denominators
      zero(numA)
      zero(numB)
      zero(denA)
      zero(denB)
      zero(f_pi)

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
            alpha(t)(j) = sum * B(j)(O(t))
            j += 1
          }
          t += 1
        }

        // Beta:
        i = 0
        while (i < N) {
          beta(T - 1)(i) = BigDecimal(1)
          i += 1
        }
        t = T - 2
        while (t >= 0) {
          i = 0
          while (i < N) {
            var sum = BigDecimal(0)
            var j = 0
            while (j < N) {
              sum += A(i)(j) * B(j)(O(t + 1)) * beta(t + 1)(j)
              j += 1
            }
            beta(t)(i) = sum
            i += 1
          }
          t -= 1
        }

        // probability of the sequence:
        var prob = BigDecimal(0)
        i = 0
        while (i < N) {
          prob += alpha(T - 1)(i)
          i += 1
        }
        prob
      }

      def gen_gamma(seq: SymbolSequence, pO: BigDecimal): Unit = {
        var t = 0
        while (t < seq.T) {
          var i = 0
          while (i < N) {
            gamma(t)(i) = alpha(t)(i) * beta(t)(i) / pO
            i += 1
          }
          t += 1
        }
      }

      def gen_pi(): Unit = {
        var i = 0
        while (i < N) {
          f_pi(i) += gamma(0)(i)
          i += 1
        }
      }

      def gen_numA(seq: SymbolSequence, pO: BigDecimal): Unit = {
        val O = seq.symbols
        val T = seq.T

        var i = 0
        while (i < N) {
          var j = 0
          while (j < N) {
            var value = BigDecimal(0)
            var t = 0
            while (t < T - 1) {
              value += alpha(t)(i) * B(j)(O(t + 1)) * beta(t + 1)(j)
              t += 1
            }
            value *= A(i)(j) / pO
            numA(i)(j) += value
            j += 1
          }
          i += 1
        }
      }

      def gen_denA_denB(seq: SymbolSequence): Unit = {
        val T = seq.T

        var i = 0
        while (i < N) {
          var value = BigDecimal(0)
          var t = 0
          while (t < T - 1) {
            value += gamma(t)(i)
            t += 1
          }
          denA(i) += value
          denB(i) += gamma(T - 1)(i) + value
          i += 1
        }
      }

      def gen_numB(seq: SymbolSequence): Unit = {
        val O = seq.symbols
        val T = seq.T

        var j = 0
        while (j < N) {
          var k = 0
          while (k < M) {
            var value = BigDecimal(0)
            var t = 0
            while (t < T) {
              if (O(t) == k) {
                value += gamma(t)(j)
              }
              t += 1
            }
            numB(j)(k) += value
            k += 1
          }
          j += 1
        }
      }

      def refine_pi(): Unit = {
        var i = 0
        while (i < N) {
          pi(i) = f_pi(i) / sequences.size
          i += 1
        }
      }

      def refine_A(): Unit = {
        var i = 0
        while (i < N) {
          var j = 0
          while (j < N) {
            A(i)(j) = numA(i)(j) / denA(i)
            j += 1
          }
          i += 1
        }
      }

      def refine_B(): Unit = {
        var j = 0
        while (j < N) {
          var k = 0
          while (k < M) {
            B(j)(k) = numB(j)(k) / denB(j)
            k += 1
          }
          j += 1
        }
        hmm.adjustB(ε)
      }
    }

    var PROld = productProbability("Initial sequence probabilities:", hmm)

    // save model every ~minute
    val savePeriodMs = 60*1000
    var saveBaseMs = System.currentTimeMillis()

    var continue = true
    var r = 0
    while (continue) {
      r += 1

      refinementStep()

      val PR = productProbability(s"After refinement $r:", hmm)

      val change = (PR - PROld) / PR
      println(s"CHANGE = " + magenta(change.toString()))
      PROld = PR

      continue = change > valAuto || r < maxRefinements

      if (continue) {
        val currMs = System.currentTimeMillis()
        if (currMs - saveBaseMs > savePeriodMs) {
          saveBaseMs = currMs
          Hmms.save(hmm, hmmFile)
        }
      }
    }

    Hmms.save(hmm, hmmFile)
    println(s"Trained HMM after $r refinements.")
    hmm
  }

  def productProbability(msg: String, hmm: Hmm): BigDecimal = {
    println(msg)

    val probsFut = Future.sequence {
      sequences map { seq ⇒
        Future { hmm.probability(seq) }
      }
    }
    val probs = Await.result(probsFut, Duration.Inf)
    val numProbs = probs.length

    var PR = BigDecimal(1)
    var r = 0
    while (r < numProbs) {
      val prob = probs(r)
      printf(" %03d: %s\n", r, prob)
      PR *= prob
      r += 1
    }
    println("   Product = %s\n" format PR)
    PR
  }
}
