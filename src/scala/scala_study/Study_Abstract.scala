package scala_study

/**
  * Created by Administrator on 2017/11/1.
  * 1、在scala中,含有抽象方法或抽象字段的类为抽象类(在Java中:含有抽象方法的类叫做抽象类)
  * 2、没有执行体的方法叫做抽象方法,scala当中的抽象方法不需要加abstract关键字,但是在Java当中,抽象方法必须加abstract关键字
  * 3、没有初始值的字段叫做抽象字段(注意:占位符除外),在Java中没有抽象字段的概念
  * 4、在子类中重写父类的抽象方法或字段时，写不写override关键字无所谓(这一点和Java一样)
  */
object Study_Abstract {
  def main(args: Array[String]): Unit = {
    val dog = new Dog("wangcai", 2, 20)
    dog.run()

    println(dog.name)
    println(dog.fruit)
    println(dog.speed)
  }

}

abstract class Animal(var name: String, var age: Int) {
  var fruit: String

  def run()
}

class Dog(name: String, age: Int, var speed: Int) extends Animal(name, age) {
  override var fruit: String = "meat"

  override def run(): Unit = {
    println(name + "  " + age + "  " + speed)
  }
}
