package scala_study

import scala.reflect.ClassTag

/**
  * Created by Administrator on 2017/11/6.
  * ClassTag[T]保存了泛型擦除后的原始类型T,提供给被运行时的
  * 主要用于构建泛型数组（因为直接使用泛型构建数组会报错）
  *
  * 这里解释Manifest：
  * 数组在声明时必须要求指定具体的类型，在函数泛型是无法知道具体类型，
  * 通过Manifest关键字使得运行时可以根据这个Manifest参数做更多的事情。
  *
  * 在引入Manifest的时候，还引入了一个更弱一点的ClassManifest，所谓的弱是指类型信息不如Manifest那么完整。
  * 用TypeTag替代了Manifest，用ClassTag替代了ClassManifest，原因是在路径依赖类型中，Manifest存在问题
  */
object Study_Bounds_4_ClassTag {
  def main(args: Array[String]): Unit = {

    val s_1 = new SB4C("scala", 4, 4.1)

    val s_2 = new SB4C(4, "scala", 5.0)

    println(s_1)
    println(s_2)

    //从理论上讲，创建出一个泛型数组是不可以的，因为没有指明具体的数据类型，而在scala中运行的时候数组必须要有具体的数据类型
    //对于上面的问题，scala中引入了ClassTag帮我们解决
    def mkArray[T: ClassTag](first: T, second: T): Array[T] = {
      var arr = Array[T](first, second)
      arr
    }

    val aa = mkArray("scala", "java")
    aa.foreach(println)

  }

}

class SB4C[F: ClassTag, S, T](val first: F, val second: S, val third: T)
