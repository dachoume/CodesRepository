package scala_study

/**
  * Created by Administrator on 2017/11/3.
  * upper bounds适用于把泛型对象当作数据的提供者(生产者)的场景（类似于协变，返回值）
  * lower bound适用于把泛型对象当作数据的消费者的场景下（类似于逆变，输入参数）
  */
object Study_Bounds_1_LowerBounds {
  def main(args: Array[String]): Unit = {
    //下边界实现
    def getID[R >: Father_1](person: R) = {
      if (person.getClass == classOf[Father_1]) {
        println("show father's ID")
      } else if (person.getClass == classOf[Child_1]) {
        println("show child's ID")
      } else {
        println("sorry")
      }
    }

    val c = new Child_1("Tom")
    getID(c) //show child's ID
    val f = new Father_1("Mike")
    getID(f) //show father's ID
  }

}

class Father_1(val name: String)

class Child_1(name: String) extends Father_1(name)



