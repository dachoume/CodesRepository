package data_analysis_tf

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel

import scala.collection.mutable.ArrayBuffer

/**
  * Created by wulidachou on 2017/8/15.
  * 浏览器分类，使用contains分出谷歌和火狐
  * 输入格式为.sql
  */
object Browser_Distribution {
  def main(args: Array[String]): Unit = {
    //    System.setProperty("hadoop.home.dir", "E:\\winutils\\lib")

    val spark = SparkSession
      .builder()
      .appName("Browser_Distribution")
      .getOrCreate()

    //    val input = spark.read.textFile("G:\\Working\\测试数据\\党建云日志分析数据\\request.log").rdd
    val input = spark.read.textFile(args(0)).rdd

    val browser: RDD[(String, String)] = input
      .filter(x => {
        x.startsWith("(") && x.contains("'0'")
      }).mapPartitions(x => {
      val arr = new ArrayBuffer[(String, String)]()
      while (x.hasNext) {
        val next: String = x.next()
        val arr_tmp = next.split(",", -1)
        if (arr_tmp.length > 8 && arr_tmp(2).startsWith("'") && arr_tmp(2).endsWith("'")) {
          arr += ((arr_tmp(2).substring(arr_tmp(2).indexOf("'") + 1, arr_tmp(2).lastIndexOf("'")), next))
        }
      }
      arr.toIterator
    })

    import spark.implicits._

    browser.persist(StorageLevel.MEMORY_AND_DISK_SER)
      .mapPartitions(x => {
        val arr_tmp = new ArrayBuffer[(String, String)]()
        while (x.hasNext) {
          val next: (String, String) = x.next()
          if (next._2.contains("Chrome")) {
            arr_tmp += ((next._1, "Chrome"))
          } else if (next._2.contains("Mozilla")) {
            arr_tmp += ((next._1, "Mozilla"))
          } else {
            arr_tmp += ((next._1, "other"))
          }
        }
        arr_tmp.toIterator
      }).distinct().map(x => {
      (x._2, 1)
    }).reduceByKey(_ + _).map(x => {
      Browser_Count(x._1, x._2)
    }).repartition(1).toDF.write
      .json(args(1))
    //      .json("G:\\支部数据分析结果临时文件夹\\Browser_Distribution")


    spark.stop()
  }

}
