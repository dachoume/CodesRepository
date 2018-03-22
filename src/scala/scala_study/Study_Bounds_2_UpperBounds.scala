package scala_study

/**
  * Created by Administrator on 2017/11/3.
  */
object Study_Bounds_2_UpperBounds {
  def main(args: Array[String]): Unit = {

    val s = new SB2U_Test[SB2U](new SB2U("zhangsan", 16), new SB2U("lisi", 24))

    println(s.bigger.name)
  }

}

class SB2U(val name: String, val age: Int) extends Comparable[SB2U] {

  override def compareTo(o: SB2U): Int = this.age - o.age

}

//注意:T这个泛型前提必须要实现Comparable接口中的compareTo方法。
//Java中自带的数据类型都已已经实现了,因此用户自定义的必须要提前实现
class SB2U_Test[T <: Comparable[T]](val first: T, val second: T) {

  def bigger: T = if (first.compareTo(second) > 0) first else second

}
