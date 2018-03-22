package scala_study

/**
  * Created by Administrator on 2017/11/7.
  * 1、所谓隐式参数就是在函数或方法中的参数前面加上implicit修饰符，这样的参数叫做隐式参数
  * 2、若在参数的前面含有implicit修饰符，编译器就会自动寻找相应的隐式值作为默认值(缺省值)进行传递。
  * 3、隐式参数的运行机制：若函数的参数只传进部分参数，编译器就会自动在其作用域范围内或其object伴生对象中，
  * 寻找是否有相应的隐式值作为默认参数，找到之后，编译器就会自动调动隐式值作为隐式参数的默认参数。
  * 隐式函数的运行机制：若当前的类对象不含有相应的类方法，编译器就会自动在其作用域范围内或其object伴生对象中，
  * 寻找是否有相应的隐式函数进行对象的自动封转，找到之后，编译器就会自动调动隐式函数进行对象的自动封转，
  * 进而调用相应的类方法
  * 4、在整个上下文当中,用户自己设计的同类型同作用的隐式函数或者隐式参数只能被调用一次
  * 5、隐式函数将会隐式的被调用;隐式参数将会隐式的作为参数进行传递
  * 6、用户自己设计的隐式函数或者隐式参数常放在object中
  */
object Study_Implicit_2 {
  def main(args: Array[String]): Unit = {
    def fun(x: String)(implicit y: String): Unit = {
      println(x + "  " + y)
    }

    fun("hello")("java")

    import SI2.default
    fun("hello")
  }

}

object SI2 {
  implicit val default: String = "scala" //注：这里一定要注明阴式值的类型
}
