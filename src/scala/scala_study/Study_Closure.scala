package scala_study

/**
  * Created by Administrator on 2017/11/2.
  * 1、Scala中的闭包类似于JavaScript中的方法–对其内部的方法进行动态调用。
  * 2、Scala中的闭包捕获到的是变量的本身而不仅仅是变量的数值,当自由变量发生变化时，Scala中的闭包能够捕获到这个变化
  * 3、如果自由变量在闭包内部发生变化，也会反映到函数外面定义的自由变量的数值
  */
object Study_Closure {
  def main(args: Array[String]): Unit = {
    //对应知识点1
    def f_1(x: Int) = (y: Int) => x + y

    val aa = f_1(10)
    println(aa) //<function1>
    println(aa.apply(20)) //30
    println(aa(30)) //40


    //对应知识点2
    var more = 10 //自由变量

    def f_2(x: Int) = {
      x + more
    }

    println(f_2(20)) //30

    more = 30

    println(f_2(30)) //60


    //对应知识点3
    var sum = 0

    val array = Array(10, 20, 30)

    array.foreach(x => sum += x)

    println(sum) //60


  }
}


