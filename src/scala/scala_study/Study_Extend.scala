package scala_study

/**
  * Created by Administrator on 2017/10/31.
  * 1、scala中 extends、final关键字用法Java中相同
  * 2、在Scala当中重写一个非抽象方法必须使用override关键字,即重写方法必须使用override
  * 3、只有主构造器可以调用超类的主构造器,当然主构造器也可以调用超类的辅助构造器
  * 4、Java当中一般是通过super关键字来调用父类的构造函数,而在scala中直接在子类声明的时候调用父类的构造函数并填充主构造器相应的参数
  */
object Study_Extend {
  def main(args: Array[String]): Unit = {
    val tt = new Tom("Tom", 18, 12000) //&1分配内存 &2父类构造函数 &3子类构造函数

    println(tt.school) //final类型的常量在子类中得到了重写

    println(tt.toString)

    println(tt.salary) //调用子类自己的成员变量，注：只有在定义子类时在主构造函数变量前加上val或var才可以这样使用

    tt.fun()

  }

}

class Man(var name: String, var age: Int) {
  println("这是父类")

  val school = "YSU"

  def fun(): Unit = {
    println("Hello World")
  }

  override def toString: String = name + "  " + age //重写从object继承过来的toString方法
}

class Tom(name: String, age: Int, val salary: Int) extends Man(name: String, age: Int) {
  println("这是子类")

  override val school: String = "PKU"

  override def toString: String = name + "  " + age + "  " + salary //重写Man类的构造方法

  override def fun(): Unit = {
    println("Hello Scala")
    super.fun()
  }
}
