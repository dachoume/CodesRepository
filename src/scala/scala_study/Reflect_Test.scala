package tools

object Reflect_Test {
    def main(args: Array[String]): Unit = {
        //如果函数可以直接调用则直接创建对象调用
        val rc = new Reflect_Case
        val result_class = rc.test(1, 2)
        println(result_class)

        //如果函数不能直接调用则进行反射，此处是可以创建对象的情况下
        val result_class2 = rc.getClass.getMethod("test", classOf[Int], classOf[Int])
          .invoke(rc, new Integer(1), new Integer(2))
          .asInstanceOf[Integer]
        println(result_class2)

        //此处是不可以创建对象的情况下
        val rt = Class.forName("tools.Reflect_Case")
        val method = rt.getDeclaredMethod("reflect_Sout", classOf[String], classOf[String])
        val result = method.invoke(null, "hello", "world")
          .asInstanceOf[String]
        println(result)

        //具体方法见印象
    }

}

================================人工分割线=====================================
package tools

object Reflect_Case {
    def reflect_Sout(s: String, t: String): String = {
        s + t
    }

    def main(args: Array[String]): Unit = {
        println(reflect_Sout("hello", "world"))
    }

}

class Reflect_Case {
    def test(a: Int, b: Int): Int = {
        a + b
    }
}
