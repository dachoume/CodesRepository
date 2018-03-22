package scala_study

/**
  * Created by Administrator on 2017/11/6.
  * 1、T >: A <: B 含义：类型变量T将同时拥有上界B与下界A
  * 注意：(1)A必须是B的子类型 (2)下界必须写在前面，上界必须写在后面，位置不能颠倒。
  * 2、拥有多个上界的描述： T <： A with B 含义：T是A或者B的子类 with是或的含义
  * 3、T >：A with B 含义：A或者B是T的子类
  * 4、T <% A <% B 含义：T可以有多个视图界定，通过隐式转换T既可以直接转换成A的类型又可以直接转换成B的类型
  * 注意：T必须同时满足既能够隐式转化成A，也能够隐式转化成B的要求
  * 5、T ： A ：B（上下文界定）注意：T必须同时满足A[T]这种类型的隐式值，与B[T]这种类型的隐式值
  */
object Study_Bounds_5_MultipleBounds {
  def main(args: Array[String]): Unit = {
    //T：A：B与T<%A<%B中A与B不需要什么关系，虽然看起来A好像是B的子类

    val aa = new SB5M_1[String]("java", "scala")
    val bb = new SB5M_2[String]("scala", "java")

    println(aa.fun()) //scala
    println(bb.fun()) //scala
  }

}

class SB5M_1[T <% Ordered[T] <% Comparable[T]](var first: T, var second: T) {
  def fun() = if (first.compare(second) > 0) first else second
}

class SB5M_2[T <% Comparable[T] <% Ordered[T]](var first: T, var second: T) {
  def fun() = if (first.compare(second) > 0) first else second
}
