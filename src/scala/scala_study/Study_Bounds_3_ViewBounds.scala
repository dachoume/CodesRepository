package scala_study

/**
  * Created by Administrator on 2017/11/6.
  * 1、在scala的类型变量限定当中,Int,Double类型本来是不是Comparable[T]的子类，
  * 但是通过视图界定<%,Int,Double类型可以隐式的转换为Comparable[T]的子类。
  * 注意：只有Java中的基本数据类型和用户自定义的数据类型并且实现了Comparable接口中的compareTo方法，
  * 才能使用视图界定<%的方式。
  * 2、Ordered是Comparable接口的子类，并在其基础上面提供了一些常用的关系操作符。
  * 3、通过Ordered中的关系操作符，我们可以方便的对两个对象进行比较，而不需要用compareTo这种方法。
  * 4、只有在视图界定<%的前提下，我们才可以使用Ordered；若是直接的应用类型变量上界的方式，我们是不可以直接使用Ordered。
  * 5、视图界定<%比类型变量的上界 <: 更加常用
  */
object Study_Bounds_3_ViewBounds {
  def main(args: Array[String]): Unit = {

    val aa = new SB3V_A[String]("scala", "java")
    println(aa.bigger) //scala

    //报错
    //    val aa_1 = new SB3V_A[Int](14, 23)
    //    println(aa_1.bigger)

    //这回不报错
    val bb = new SB3V_B[Int](14, 24)
    println(bb.bigger) //24

    val cc = new SB3V_C[Int](24, 45)
    println(cc.bigger())
  }

}

class SB3V_A[T <: Comparable[T]](val first: T, val second: T) {
  def bigger: T = if (first.compareTo(second) > 0) first else second
}

class SB3V_B[T <% Comparable[T]](val first: T, val second: T) { //提示view bounds已经被淘汰，使用隐式转换
  def bigger: T = if (first.compareTo(second) > 0) first else second
}

//根据我们正常的数学逻辑思维，通常我们在比较两个对象的时候，习惯用< >这种数学符号，而不是 compareTo这种方式。
//即不是用：first.compareTo(second)>0，这个问题的解决措施：引入Ordered[T]的方式

class SB3V_C[T <% Ordered[T]](val first: T, val second: T) { //同样提示使用隐式转换
  def bigger(): T = if (first > second) first else second
}


