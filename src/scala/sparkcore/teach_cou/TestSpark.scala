package testcou

import org.apache.spark.sql.SparkSession

/**
  * Created by 56308 on 2017/11/28.
  */
object TestSpark {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "D:\\My_Working\\winutils\\lib")
    val spark = SparkSession
      .builder()
      .appName("TestSpark")
      .getOrCreate()

    val test=spark.read.textFile("C:\\Users\\56308\\Desktop\\test.txt").rdd
      .flatMap(_.split(" "))
      .map(x=>(x,1)).reduceByKey(_+_).foreach(println)

    spark.stop()
  }

}
