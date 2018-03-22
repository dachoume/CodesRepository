package scala_study

/**
  * Created by Administrator on 2017/11/2.
  * 基于AOP的代码实战
  * 特质的另一应用方面在于：为类提供可堆叠的改变（super保留字）
  * 当为类添加多个互相调用的特质时，从最后一个开始进行处理
  * 在类中super.foo()这样的方法调用是静态绑定的，明确是调用它的父类的foo方法
  * 在特质中写下了super.foo()时，它的调用是动态绑定的。调用的实现将在每一次特质被混入到具体类的时候才被决定
  * 因此，特质混入的次序的不同其执行效果也就不同
  */
object Study_Trait_2 {
  def main(args: Array[String]): Unit = {
    val ra = new RealAction with BeforeAction
    ra.doaction()
  }

}

trait Action {
  def doaction()
}

trait BeforeAction extends Action {
  abstract override def doaction(): Unit = { //注：这里一定要写abstract修饰符
    println("Hello Scala")
    super.doaction()
    println("Hello Spark")
  }
}

class RealAction extends Action {
  override def doaction(): Unit = {
    println("RealAction")
  }
}
