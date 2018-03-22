package scala_study

/**
  * Created by Administrator on 2017/10/30.
  * 单例和伴生
  *
  * 伴生类Tree的构造函数定义为private，虽然这不是必须的，却可以有效防止外部实例化Tree类，使得Tree类只能供对应伴生对象使用；
  * 每个类都可以有伴生对象，伴生类与伴生对象写在同一个文件中；
  * 在伴生类中，可以访问伴生对象的private字段Tree.msg；
  * 而在伴生对象中，也可以访问伴生类的private方法，apple.getFruit（）；
  * 最后，在外部不用实例化，直接通过伴生对象访问Tree.printMeg方法
  */
object Study_OneObject {
  def main(args: Array[String]): Unit = {
    Tree.printMeg()
  }

}


object Tree {
  private val apple = new Tree("Apple")
  private val msg = "Hello"

  def printMeg(): Unit = apple.getFruit()
}

class Tree private(fruit: String) {

  def getFruit(): Unit = {

    println(fruit + Tree.msg)

  }
}


