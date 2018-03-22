package data_analysis_tf

import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Administrator on 2017/8/28.
  */
object Mobile_Distribution_Bak {
  def main(args: Array[String]): Unit = {
    //    System.setProperty("hadoop.home.dir", "D:\\SoftWare\\winutils\\lib")
    val spark = SparkSession
      .builder()
      .appName("Mobile_Distribution_Bak")
      .getOrCreate()

    //uid useragent
    val input = spark.read.textFile(args(0)).rdd
    import spark.implicits._

    input.mapPartitions(x => {
      val arr = new ArrayBuffer[(String, String)]()
      while (x.hasNext) {
        val next: Array[String] = x.next().split("\\|", -1)
        if (next.length == 2) {
          if (next(1).contains("iOS") || next(1).contains("iPhone")) {
            arr += ((next(0), "Apple"))
          } else if (next(1).contains("HUAWEI") || next(1).contains("Honor") || next(1).contains("Huawei") || next(1).contains("HONOR")) {
            arr += ((next(0), "HUAWEI"))
          } else if (next(1).contains("SM") || next(1).contains("GT")) {
            arr += ((next(0), "SAMSUNG"))
          } else if (next(1).contains("MIUI") || next(1).contains("m1") || next(1).contains("m2")) {
            arr += ((next(0), "MI"))
          } else if (next(1).contains("OPPO")) {
            arr += ((next(0), "OPPO"))
          } else if (next(1).contains("vivo")) {
            arr += ((next(0), "VIVO"))
          } else {
            arr += ((next(0), "other"))
          }
        }
      }
      arr.toIterator
    }).distinct().map(x => {
      (x._2, 1)
    }).reduceByKey(_ + _)
      .map(x => {
        Mobile_Count(x._1, x._2)
      }).repartition(1).toDF.write
      .json(args(1))

    spark.stop()
  }

}
