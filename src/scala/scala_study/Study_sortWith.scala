package scala_study

/**
  * Created by Administrator on 2017/11/3.
  * 本程序是为了测试List中foldLeft、foldRight、sort操作代码实战
  */
object Study_sortWith {
  def main(args: Array[String]): Unit = {

    val list = List(1, 2, 3)
    var sum = 0

    list.foreach((x: Int) => sum += x) //将函数作用在具体的数值上面
    println(sum)

    //z为初始值
    println(list.foldLeft(0)(_ + _)) //(3+(2+(1+0))) //函数体执行两元素相加从左到右:即从1开始

    println((0 /: (1 to 3)) (_ + _)) //6

    println(list.foldRight(2)(_ - _)) //(1-(2-(3-2))) //函数体执行两元素相减从右到左:即从3开始

    println(((1 to 3) :\ 2) (_ - _)) //2

    //sortWith排序算法的效率还是很高的
    val list2 = list.sortWith((x, y) => x < y) //如果变量在=>的右侧只出现一次,则可以用占位符替换它
    println(list2.mkString("[", "\t", "]")) //[1    2   3]

    val list3 = list.sortWith(_ > _) //如果变量在=>的右侧只出现一次,则可以用占位符替换它
    println(list3.mkString("[", "\t", "]")) //[3    2   1]
  }

}
