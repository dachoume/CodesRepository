package scala_study

/**
  * Created by Administrator on 2017/11/3.
  * 1、scala中的柯里化指的是将原来接受两个参数的函数变成新的接受一个参数的函数的过程.
  * 新的函数返回一个以原有第二个参数作为参数的函数
  * 2、柯里化可以理解为一个函数可以拥有多个形参列表
  */
object Study_Currying {
  def main(args: Array[String]): Unit = {
    //一个正常写法的函数
    def mulby(x: Int, y: Int): Int = {
      x / y
    }

    println(mulby(30, 6)) //5

    //柯里化写法一
    def mulbyone(x: Int) = (y: Int) => x / y

    val mm = mulbyone(30)

    println(mm(5)) //6

    //柯里化写法二
    def mulbytwo(x: Int)(y: Int) = x / y

    println(mulbytwo(50)(5)) //10
  }


}
