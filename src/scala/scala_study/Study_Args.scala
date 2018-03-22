package scala_study

/**
  * Created by Administrator on 2017/11/8.
  * 不指定参数个数的情况
  * 类型后面加个*表示可能有多个这种类型的参数
  */
object Study_Args {
  def main(args: Array[String]): Unit = {
    def fun(args: Any*): Unit = {
      for (x <- args) {
        println(x)
      }
    }

    fun("hello", "scala", 1, 2)
  }

}
