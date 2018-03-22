package data_analysis_tf

import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Administrator on 2017/8/29.
  */
object Find_Other {
  def main(args: Array[String]): Unit = {
    //    System.setProperty("hadoop.home.dir", "D:\\SoftWare\\winutils\\lib")
    val spark = SparkSession
      .builder()
      .appName("Find_Other")
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
          } else if (next(1).contains("HUAWEI") || next(1).contains("Honor")|| next(1).contains("Huawei")|| next(1).contains("HONOR")) {
            arr += ((next(0), "HUAWEI"))
          } else if (next(1).contains("SM") || next(1).contains("GT")) {
            arr += ((next(0), "SAMSUNG"))
          } else if (next(1).contains("MIUI")) {
            arr += ((next(0), "MI"))
          } else if (next(1).contains("OPPO")) {
            arr += ((next(0), "OPPO"))
          } else if (next(1).contains("vivo")) {
            arr += ((next(0), "VIVO"))
          } else {
            arr += ((next(1), "other"))
          }
        }
      }
      arr.toIterator
    }).filter(_._2=="other").map(_._1).repartition(1).saveAsTextFile(args(1))

    spark.stop()
  }

}
