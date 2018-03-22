package scala_study

/**
  * Created by Administrator on 2017/11/2.
  * 1、scala中的trait(特质)功能很强大,Java中抽象类能做的事情，trait都可以做。
  * Java中接口能做的事情,trait也可以做,它的长处在于可以多继承—即trait既可以当做接口来用,也可以当做抽象类来用.
  * 2、在Java当中,接口(interface)就是抽象方法和数值型常量的集合,不能有普通方法,
  * 但是在scala当中,trait中可以有普通方法
  * 3、在Java当中,接口只能被实现,不能被继承,但是在scala当中,特质trait可以被继承,
  * 如果是第一次使用trait,并且trait前面没有其它的类或者抽象类,就需要使用extends关键字,
  * 而后使用with关键字(注意:不是implements关键字)
  * 4、在对象中混入trait
  * 在scala中，通过trait我们可以将一些具体实现的方法混入到我们的类或对象中.
  * (注意:创建对象时混入特质，要重写trait中的抽象方法 )
  */
object Study_Trait {
  def main(args: Array[String]): Unit = {
    val fisheagel = new FishEagle("mike", 10) with FlySwim {
      override def flyswim(): Unit = {
        println("flyswim")
      }
    }
    fisheagel.fly()
    println(fisheagel.fruit)
    fisheagel.flyswim() //创建对象时混入特质，要重写trait中的抽象方法
  }

}

trait Flyable {
  def fly()
}

trait Swimable {
  def swim()
}

trait FlySwim extends Flyable with Swimable {
  override def fly(): Unit = {
    println("can fly")
  }

  override def swim(): Unit = {
    println("can swim")
  }

  def flyswim()
}

class FishEagle(name: String, age: Int) extends Animal(name, age) {
  override var fruit: String = "fish"

  override def run(): Unit = {
    println("can run")
  }
}
