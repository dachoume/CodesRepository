package data_analysis_tf

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel

import scala.collection.mutable.ArrayBuffer

/**
  * Created by wulidachou on 2017/8/15.
  * 手机型号分类，苹果、华为、oppo、vivo、其他
  * 输入格式为.sql
  */
object Mobile_Distribution {
  def main(args: Array[String]): Unit = {
    //    System.setProperty("hadoop.home.dir", "E:\\winutils\\lib")

    val spark = SparkSession
      .builder()
      .appName("Mobile_Distribution")
      .getOrCreate()

    //    val input = spark.read.textFile("G:\\Working\\测试数据\\党建云日志分析数据\\request.log").rdd
    val input = spark.read.textFile(args(0)).rdd

    //将手机型号那一列取出来   uid 手机型号
    val mobile: RDD[(String, String)] = input
      .filter(x => {
        x.startsWith("(") && !x.contains("'0'")
      })
      .mapPartitions(x => {
        val arr = new ArrayBuffer[(String,String)]()
        while (x.hasNext) {
          val next = x.next()
          val arr_tmp = next.split(",", -1)
          if (arr_tmp.length > 8 && arr_tmp(2).startsWith("'") && arr_tmp(2).endsWith("'") && arr_tmp(8).startsWith("'") && arr_tmp(8).endsWith("'")) {
            arr += ((arr_tmp(2).substring(arr_tmp(2).indexOf("'") + 1, arr_tmp(2).lastIndexOf("'")),
              arr_tmp(8).substring(arr_tmp(8).indexOf("'") + 1, arr_tmp(8).lastIndexOf("'"))))
          }
        }
        arr.toIterator
      })

    import spark.implicits._

    mobile.persist(StorageLevel.MEMORY_AND_DISK_SER)
      .mapPartitions(x => {
        val arr_tmp = new ArrayBuffer[(String,String)]()
        while (x.hasNext) {
          val next: (String, String) = x.next()
          if (next._2.contains("iOS") || next._2.contains("iPhone")) {
            arr_tmp += ((next._1,"Apple"))
          } else if (next._2.contains("HUAWEI") || next._2.contains("Honor")) {
            arr_tmp += ((next._1,"HUAWEI"))
          } else if (next._2.contains("OPPO")) {
            arr_tmp += ((next._1,"OPPO"))
          } else if (next._2.contains("vivo")) {
            arr_tmp += ((next._1,"VIVO" ))
          } else {
            arr_tmp += ((next._1,"other"))
          }
        }
        arr_tmp.toIterator
      }).distinct().map(x=>{
      (x._2,1)
    }).reduceByKey(_ + _).map(x => {
      Mobile_Count(x._1, x._2)
    }).repartition(1).toDF.write
      .json(args(1))
    //      .json("G:\\支部数据分析结果临时文件夹\\Mobile_Distribution")
    //.saveAsTextFile("G:\\支部数据分析结果临时文件夹\\test")

    spark.stop()
  }

}
