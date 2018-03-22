package scala_study

import scala.io.Source

/**
  * Created by Administrator on 2017/10/27.
  * Source不但可以指定本地文件的路径,还可以关联URL,读取一个URL中的内容
  */
object Study_Source {
  def main(args: Array[String]): Unit = {
    val file = Source.fromFile("E:\\电脑互备临时文件夹\\vps\\readme.txt")
    //    for (line <- file.getLines()) {
    //      println(line)
    //    }

    val page = Source.fromURL("http://blog.csdn.net/a2011480169/article/details/52141773")
    for (web_line <- page.getLines()) {
      println(web_line)
    }
  }

}
