import mill._
import mill.scalalib._

object ecoz extends BaseModule {
  override def moduleDeps = Seq(selXtor, hmm)
  override def mainClass = Some("ecoz.Ecoz")
}

object hmm extends BaseModule {
  override def moduleDeps = Seq(vpl, rpt)
}

object vpl extends BaseModule {
  override def moduleDeps = Seq(signal, rpt)
  override def mainClass = Some("ecoz.vpl.Vpl")
}

object rpt extends BaseModule

object signal extends BaseModule {
  override def moduleDeps = Seq(config)
}

object selXtor extends BaseModule {
  override def moduleDeps = Seq(config)
  override def mainClass = Some("ecoz.selXtor.SelXtor")
}

object config extends BaseModule

trait BaseModule extends ScalaModule {
  def scalaVersion = "2.12.4"

  override def ivyDeps = Agg(
    ivy"de.sciss::audiofile:1.5.0",
    ivy"com.github.tototoshi::scala-csv:1.3.5",
    ivy"io.spray::spray-json:1.3.4",
    ivy"com.lihaoyi::pprint:0.5.2",
    ivy"org.scala-lang:scala-reflect:${scalaVersion()}", // https://github.com/lihaoyi/mill/issues/132
  )

  override def scalacOptions = Seq(
    "-deprecation", "-feature", "-encoding", "utf8",
    "-Ywarn-dead-code",
    "-unchecked",
    "-Xlint",
    "-Ywarn-unused-import",
  )
}
