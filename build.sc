import mill._
import mill.scalalib._

object ecoz extends ScalaModule {
  override def mainClass = Some("ecoz.Ecoz")
  def scalaVersion = "2.12.4"

  override def ivyDeps = Agg(
    ivy"de.sciss::audiofile:1.5.0",
    ivy"com.github.tototoshi::scala-csv:1.3.5",
    ivy"io.spray::spray-json:1.3.4",
    ivy"com.lihaoyi::pprint:0.5.2",
    ivy"org.scala-lang:scala-reflect:${scalaVersion()}", // https://github.com/lihaoyi/mill/issues/132
  )

  override def scalacOptions = Seq(
    //"-opt:help",
    //"-optimize",  --> deprecated
    "-opt:l:inline", "-opt-inline-from:**",
    "-opt:simplify-jumps",
    "-opt:box-unbox",
    "-deprecation", "-feature", "-encoding", "utf8",
    "-Ywarn-dead-code",
    "-unchecked",
    "-Xlint",
    "-Ywarn-unused-import",
  )
}
