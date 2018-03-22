package scala_study

/**
  * Created by Administrator on 2017/10/30.
  */
object Study_Private_This {
  def main(args: Array[String]): Unit = {

    val obj = new Test_Private_This

    println(obj.getData)


  }
}


class Test_Private_This {
  private[this] val ss = 404

  private[Test_Private_This] val vv = 505

  def test(t: Test_Private_This) = {

    println(t.vv)

    //    println(t.ss) 报错
    //使用private[this]修饰的只能在本对象中使用
    //如果方括号中的是类名的话可以在该类中使用
  }


  def getData: Int = ss //这个就可以使用

}
