package scala_study

/**
  * Created by Administrator on 2017/10/27.
  * 测试try catch
  */
object Study_TryCatch {
  def main(args: Array[String]): Unit = {

    for (x <- 0 to 10) {
      try {
        if (x == 0) {
          println("a is 0")
        } else {
          throw new Exception("wrong num")
        }
      } catch {
        case e: Exception => println("exception is :" + e.getMessage)
      }
    }
  }

}
