package scala_study

/**
  * Created by Administrator on 2017/11/3.
  * List中常用的方法：
  * partition：对集合中的元素按照某种条件进行分区
  * span：span的操作类似与partition，将集合分成不同的区域
  * find：找出集合中第一个满足条件的元素，返回值为Some或者None
  * takeWhile:获取集合当中所有满足条件的元素
  * dropWhile：获取集合当中满足条件以外的元素
  * forall：只有集合当中的所有元素都满足条件时才返回true，否则返回false
  * exists：只要集合当中存在一个元素满足条件就返回true
  */
object Study_Partition {
  def main(args: Array[String]): Unit = {
    val list = List(1, 2, 3, 4, 5, 6, 7)
    val (a, b) = list.partition(_ % 3 == 0) //partition函数默认将数据分为两个区
    println(a) //List(3, 6)
    println(b) //List(1, 2, 4, 5, 7)
  }

}
