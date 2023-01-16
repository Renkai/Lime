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
    "b" -> "n",
    "n" -> "c"
  )
  def singleChar(line: String): String = {
    line match {
      case singleCharPattern(char, first, second, assist) => {
//        println(s"char:${char} ,fist: ${first}, second: ${second}, assist: ${assist}")
        if (Array("a", "e", "i", "o", "u").contains(first) && second == "n") {
          // don't switch
          line
        } else {
          s"$char$first${flyZrm.getOrElse(second, second)}[$assist"
        }
      }
      case ""                                             => ""
      case x if x.startsWith("#")                         => x
      case unexpected                                     =>
        throw new Exception(s"got unexpected: ${unexpected}")
    }
  }
  def main(args: Array[String]): Unit  = {
    val wordStart               = "#以下爲詞組"
    val resource                = scala.io.Source
      .fromResource("flypy_zrmfast.dict.yaml")
    val lines: Iterator[String] = resource
      .getLines()
      .drop(skipLines)
      .takeWhile(s => !s.startsWith(wordStart))
    import java.nio.file.{Files, Paths}

    val bufferedWriter = Files.newBufferedWriter(Paths.get("zrku.dict.yaml"))
    bufferedWriter.write(head("", "zrku"))

    for (line <- lines) {
      val zrku = singleChar(line)
      bufferedWriter.write(zrku + "\n")
    }
    resource.close()
    bufferedWriter.close()
  }
}
