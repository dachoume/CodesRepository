package scala_study

/**
  * Created by Administrator on 2017/11/8.
  * 隐式转换用到之前的泛型中
  *
  * 1、T:Ordering的含义：在上下文界定中说明只要存在T：Ordering，那么就存在一个隐式值，这个隐式值就是Ordering[T]，
  * 这个隐式值 Ordering[T]会作用在当前的作用域中，在作用域中的所有方法都可以使用这个隐式值，尤其是对应的隐式函数
  * 2、通过上下文界定:可以为程序的上下文环境提供一个默认的隐式值，
  * class A[T:Ordering] 为整个上下文环境提供了一个默认的隐式值 Ordering[T]
  * 3、class A[T:Ordering](val first:T,val second:T):因为first与second都是Ordering[T]类型的,
  * 所以可以使用Ordering[T]中的数学逻辑操作符
  * 4、Ordered伴生对象中含有很多内容:其中一个就是将Ordering类型转化为Oredered类型。
  * 如：implicit def orderingToOrdered[T](x: T)(implicit ord: Ordering[T]): Ordered[T]
  */
object Study_Implicit_3 {
  def main(args: Array[String]): Unit = {
    //回顾之前的写法
    def bigger_1[T <% Ordered[T]](first: T, second: T): T = {
      if (first > second) first else second
    }

    println(bigger_1(20, 50)) //50

    //隐式转换的写法
    def bigger_2[T](frist: T, second: T)(implicit m: T => Ordered[T]) = {
      if (frist > second) frist else second
    }

    println(bigger_2(32.1, 33.4)) //33.4
    println(bigger_2("a", "b")) //b

    //使用类的写法1
    val si = new SI3(120, 110)
    println(si.bigger_3)

    //使用类的写法2
    val si_1 = new SI3_1("a", "b")
    println(si_1.bigger_4)
  }

}

class SI3[T: Ordering](val first: T, val second: T) {
  def bigger_3(implicit m: Ordering[T]): T = if (m.compare(first, second) > 0) first else second
}

class SI3_1[T: Ordering](val first: T, val second: T) {

  import scala.math.Ordered.orderingToOrdered

  def bigger_4: T = if (first > second) first else second
}
