package scala_study

/**
  * Created by Administrator on 2017/11/8.
  * 多重循环的写法
  */
object Study_For {
  def main(args: Array[String]): Unit = {

    //    for (x <- 1 to 5; y <- 20 to 25; z <- 50 to 55) {
    //      println(x + "  " + y + "  " + z)
    //    }

    val l_1 = List(1, 2, 3, 4, 5)
    val l_2 = List(11, 12, 13, 14, 15)
    val l_3 = List(21, 22, 23, 24, 25)

    for (x <- l_1; y <- l_2; z <- l_3) {
      println(x + "  " + y + "  " + z)
    }
  }

}
