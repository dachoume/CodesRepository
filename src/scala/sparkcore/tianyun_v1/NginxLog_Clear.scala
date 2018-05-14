package tianyun_v1

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by 56308 on 2018/5/8.
  */
object NginxLog_Clear {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "D:\\My_Working\\winutils\\lib")
    val spark = SparkSession
      .builder()
      .appName("Log_Clear")
      .getOrCreate()

    import spark.implicits._

    val input: Dataset[String] = spark.read.textFile("D:\\My_Working\\tmp\\data\\wx*")

    val result_tmp: Dataset[(String, String, String, String, String)] = input.mapPartitions(x => {
      val arr = new ArrayBuffer[(String, String, String, String, String)]()
      while (x.hasNext) {
        val next = x.next()
        val arr_tmp = next.split("\"")
        val arr_space = next.split(" ")
        if (arr_space(3).startsWith("[")) {
          if (arr_tmp(1).startsWith("POST") || arr_tmp(1).startsWith("GET")) {
            val time: Array[String] = arr_space(3).replace("[", "").split("\\:")
            if (time.length == 4) {
              val time_day = time(0)
              val time_hour = time(0) + ":" + time(1)
              val arr_op = arr_tmp(1).split(" ")
              if (arr_op.length == 3 && !arr_op(1).endsWith(".png") && !arr_op(1).endsWith(".css") && !arr_op(1).endsWith(".js") && !arr_op(1).endsWith(".jpg") && !arr_op(1).equals("/")) {
                if (next.contains("iPhone")) {
                  arr += ((arr_space(0), time_day, time_hour, arr_op(1), "iPhone"))
                } else if (next.contains("Android")) {
                  arr += ((arr_space(0), time_day, time_hour, arr_op(1), "Android"))
                } else {
                  arr += ((arr_space(0), time_day, time_hour, arr_op(1), "PC"))
                }
              }
            }

          }
        }
      }
      arr.toIterator
    })

    result_tmp.rdd.map(x => {
      x._1 + "|" + x._2 + "|" + x._3 + "|" + x._4 + "|" + x._5
    }).saveAsTextFile("D:\\My_Working\\tmp\\result_wx2")


    spark.stop()
  }

}
