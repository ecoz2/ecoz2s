package ecoz

import fansi.Str

package object rpt {

  def warn(msg: String): Unit = {
    println(yellow(s"\nWARN: $msg"))
  }

  def quoted(s: String): String = s""""$s""""

  def yellow (s: Any): Str = fansi.Color.LightYellow(s.toString)

  def green(s: Any): Str = fansi.Color.LightGreen(s.toString)

  def red(s: Any): Str = fansi.Color.LightRed(s.toString)

  def cyan(s: Any): Str = fansi.Color.LightCyan(s.toString)

  def magenta(s: Any): Str = fansi.Color.LightMagenta(s.toString)

  def lightBlue(s: Any): Str = fansi.Color.LightBlue(s.toString)

  def lightGray(s: Any): Str = fansi.Color.LightGray(s.toString)

  def lightYellow(s: Any): Str = fansi.Color.LightYellow(s.toString)
}
