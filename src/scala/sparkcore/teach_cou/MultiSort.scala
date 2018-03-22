package testcou

import org.apache.spark.sql.{Dataset, SparkSession}

/**
  * Created by 56308 on 2017/12/5.
  */
object MultiSort {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "D:\\My_Working\\winutils\\lib")
    val spark = SparkSession
      .builder()
      .appName("MultiSort")
      .getOrCreate()

    import spark.implicits._

    val input: Dataset[String] = spark.read.textFile("C:\\Users\\56308\\Desktop\\sort.txt")

    //二次排序方法1，使用的是rdd，需要在自定义排序类中实现排序逻辑
    //    input.map(_.split(" ")).map(x=>{
    //      (MySortKey(x(0),x(1).toInt,x(2).toInt),1)
    //    }).repartition(1).rdd.sortByKey().foreach(println)

    //二次排序方法2，使用ds，可以直接在sort函数中写排序方法，比较简单
//    input.map(_.split(" ")).map(x => {
//      MySortKey(x(0), x(1).toInt, x(2).toInt)
//    }).repartition(2)
//      //      .sort($"first".asc,$"second".asc,$"third".asc)
//      .sort($"second".asc, $"first".asc, $"third".asc)
//      .show()


    //二次排序方法3，直接写sql
    input.map(_.split(" ")).map(x => {
      MySortKey(x(0), x(1).toInt, x(2).toInt)
    }).toDF().createOrReplaceTempView("test")

    spark.sql("select * from test order by first asc,second asc,third asc").show()
  }

}
