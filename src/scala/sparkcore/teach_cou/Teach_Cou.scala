package testcou

import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by 56308 on 2017/11/28.
  */
object Teach_Cou {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "D:\\My_Working\\winutils\\lib")
    val spark = SparkSession
      .builder()
      .appName("Teach_Cou")
      .getOrCreate()

    import spark.implicits._

    val input = spark.read.textFile("C:\\Users\\56308\\Desktop\\新建文本文档.txt")
    val input_df: DataFrame = input.map(x => {
      val arr = x.split(" ", -1)
      Weight(arr(0), arr(1))
    }).toDF()

    //方式1
//    input_df.select("name", "weight").show()

    //方式2
    input_df.createOrReplaceTempView("users")

    val result: DataFrame = spark.sql("select * from users")

    val tmp: Dataset[(String, Int)] = result.mapPartitions(x => {
      val arr_result = new ArrayBuffer[(String, Int)]()
      while (x.hasNext) {
        val next: Row = x.next()
        next match {
          case Row(name: String, weight: String) => arr_result += ((name, weight.toInt+1))
          case _ => println("数据格式不正确")
        }
      }
      arr_result.toIterator
    })

    tmp.show()

    spark.stop()


  }

}
