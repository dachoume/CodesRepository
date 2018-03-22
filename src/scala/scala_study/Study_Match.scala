package scala_study

/**
  * Created by Administrator on 2017/11/3.
  *
  */
object Study_Match {
  def main(args: Array[String]): Unit = {

    //match----catch的第一种用法:case后面匹配常量表达式
    var data = readInt()
    data match {
      case 1 => println("111111111111")
      case 2 => println("222222222222")
      case _ => println("OK")
    }

    //match----catch的第二种用法:case后面匹配变量或条件表达式
    var result = data match {
      case i if i == 1 => "spark"
      case j if j == 2 => "hadoop"
      case _ => "OK"
    }
    println(result)

    //match----catch的第三种用法:在println中使用match---case模式匹配
    "spark !".foreach { x =>
      println( //foreach循环遍历字符串中的每一个字符
        x match {
          case ' ' => "space"
          case ch => "char:" + ch
        }
      )
    }

    def f_1(t: Any) = {
      t match {
        case t: Int => println("t是数字")
        case t: String => println("t是字符串")
      }
    }

    f_1(10)
  }

}
