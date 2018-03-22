package scala_study

/**
  * Created by Administrator on 2017/11/2.
  * 本地函数即内部函数,在一个函数内部定义的函数叫做本地函数,本地函数可以使用外部函数的形式参数,
  * 本地函数是外部函数的私有函数,外部其余成员不可以进行访问
  */
object Study_Local_Function {
  def main(args: Array[String]): Unit = {
    def f() = {
      def g() = {

      }
    }
  }

}
