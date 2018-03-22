package scala_study

import util.control.Breaks._

/**
  * Created by Administrator on 2017/11/6.
  * scala中的continue和break
  * 个人理解就是遇到break就退出breakable{}这个函数
  * 所以要注意breakable函数的位置
  */
object Study_Continue_Break {
  def main(args: Array[String]): Unit = {
    //continue
    for (i <- 0 to 10) {
      breakable {
        if (i == 3 || i == 5) {
          break()
        }
        println(i)
      }
    }

    println("=================================")

    //break
    breakable {
      for (i <- 1 to 10) {
        println(i)
        if (i == 5) {
          break()
        }
      }
    }

  }

}
