package scala_study

import scala.io.Source
import java.io.File

/**
  * Created by Administrator on 2017/11/8.
  * 1、Scala中如何更好的使用隐式转换：通过伴生类与伴生对象机制，编译器将会自动导入隐式转换，而无需用户手动导入隐式转换
  * 2、Scala中的隐式转换操作规则：
  * 1）要想成为隐式转换，必须标记为implicit关键字，implicit可以用来修饰类、对象、函数、参数。
  * 2）作用域规则：隐式转换在整个作用域中，必须是单一的标识符。
  * 3、在隐式转换的作用域查找中，如果当前作用域没有隐式转换，编译器会自动到相应源或目标类型的伴生对象中查找隐式转换
  *
  * 通过伴生类与伴生对象机制导入的隐式转换
  */
object Study_Implicit_4 {
  def main(args: Array[String]): Unit = {
    val aa = new Context("")
    aa.read
  }

}

class FileRicher(val file: File) {
  def read = Source.fromFile(file).mkString
}

class Context(path: String) extends File(path) //借助于伴生类

object Context {
  implicit def File2RicherFile(file: File): FileRicher = new FileRicher(file) //File-->FileRicher
}
