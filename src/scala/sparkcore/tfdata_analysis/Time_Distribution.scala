package data_analysis_tf

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel

import scala.collection.mutable.ArrayBuffer

/**
  * 党员在线时间分布
  * 输入格式为.sql
  */
object Time_Distribution {
  def main(args: Array[String]): Unit = {
    //    System.setProperty("hadoop.home.dir", "E:\\winutils\\lib")

    val spark = SparkSession
      .builder()
      .appName("Time_Distribution")
      .getOrCreate()

    //    val input = spark.read.textFile("G:\\Working\\测试数据\\党建云日志分析数据\\request.log").rdd
    val input = spark.read.textFile(args(0)).rdd

    //将操作时间取出来
    val timestamp: RDD[String] = input
      .filter(_.startsWith("("))
      .mapPartitions(x => {
        val arr = new ArrayBuffer[String]()
        while (x.hasNext) {
          val next = x.next()
          val arr_tmp = next.split(",", -1)
          if (arr_tmp.length > 8 && arr_tmp(7).startsWith("'") && arr_tmp(7).endsWith("'")) {
            arr += arr_tmp(7).substring(arr_tmp(7).indexOf("'") + 1, arr_tmp(7).lastIndexOf("'"))
          }
        }
        arr.toIterator
      })

    import spark.implicits._

    timestamp.persist(StorageLevel.MEMORY_AND_DISK_SER)
      .map(x => {
        (getTime(x).get, 1)
      }).reduceByKey(_ + _).map(x => {
      Time_Distribution_Count(x._1.substring(0, 4),
        x._1.substring(4, 6),
        x._1.substring(6, 8),
        x._1.substring(8, 10),
        x._2)
    }).toDF.write
      .json(args(1))
    //      .json("G:\\支部数据分析结果临时文件夹\\Time_Distribution")


    spark.stop()
  }


  private def getTime(t: String): Option[String] = {
    val sdf = new SimpleDateFormat("yyyyMMddHH")
    if (t == "") {
      None
    } else {
      val time = sdf.format(new Date(t.toLong * 1000))
      Some(time)
    }
  }

}
