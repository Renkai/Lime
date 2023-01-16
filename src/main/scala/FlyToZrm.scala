import scala.util.control.Breaks.{break, breakable}
object FlyToZrm {
  def head(doc: String, name: String) =
    s"""
      |# Rime dictionary
      |# encoding: utf-8
      |# adapted from: [蓝天双拼＋自然快手](http://bbs.unispim.com/forum.php?mod=viewthread&tid=28002&highlight=%D7%D4%C8%BB%BF%EC%CA%D6)
      |# 自然码拼加自然快手辅助码 ${doc}
      |# 繁体字码表
      |---
      |name: ${name}
      |version: "2020.02.08"
      |sort: by_weight
      |use_preset_vocabulary: true
      |...
      |
      |""".stripMargin

  val skipLines = head("", "").linesIterator.length

  val singleCharPattern = "(.*)(\\w)(\\w)\\[(.*)".r

  val flyZrm                           = Map(
    "x" -> "w",
    "k" -> "y",
    "y" -> "p",
    "l" -> "d",
    "c" -> "k",
    "d" -> "l",
    "w" -> "z",
    "p" -> "x",
    "z" -> "b",
    "b" -> "n"
  )
  def singleChar(line: String): String = {
    line match {
      case singleCharPattern(char, first, second, assist) => {
        println(s"char:${char} ,fist: ${first}, second: ${second}, assist: ${assist}")
        ""
      }
      case ""                                             => ""
      case unexpected                                     =>
        throw new Exception(s"got unexpected: ${unexpected}")
    }
  }
  def main(args: Array[String]): Unit  = {
    val wordStart               = "#以下爲詞組"
    val lines: Iterator[String] = scala.io.Source
      .fromResource("flypy_zrmfast.dict.yaml")
      .getLines()
      .drop(skipLines)
      .takeWhile(s => !s.startsWith(wordStart))
    for (line <- lines) {
      println(line)
      singleChar(line)
    }
  }
}
