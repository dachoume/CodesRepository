package scala_study

/**
  * Created by Administrator on 2017/10/30.
  */
object Study_Constract {
  def main(args: Array[String]): Unit = {
    val p = new Person(30)
    println(p.name)
    println(p.age)
    println(p.message)
    //    val p1 = new Person("Tom", 20)  这样会报错，因为将主构造器私有了，只能通过辅助构造器创建对象
  }
}


//定义类的时候，就定义了构造器。即：是（）内的内容。
//主构造器会将类定义{}里的所有语句执行一次。
//如果主构造器参数不带val或var，那么会根据是否被方法使用来决定。
//如果不带val或var的参数被方法使用了，它会变为对象私有字段；
//如果没有被方法使用，则被当成一个普通的参数，不升级成字段。
//这里的主构造器是(pname: String, page: Int)

class Person private(pname: String, page: Int) { //私有主构造器
  println("start")

  var name: String = pname

  var age: Int = page

  var message = ""

  //从构造器，调用主构造器。
  //规则：每一个从构造器的第一个动作都是调用同一个类里面其他的构造器!!!!因此主构造器是类的唯一入口点。
  //这里的从构造器，调用了主构造器
  def this(pname: String) = this(pname, 20)

  //同时加入其他逻辑
  def this(page: Int) = {
    this("mike", page)
    message = "hello"
  }

  println("end")
}
