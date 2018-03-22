package data_analysis_tf

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel

import scala.collection.mutable.ArrayBuffer

/**
  * Created by wulidachou on 2017/8/15.
  * 党员在线行为分析，request.log第二个字段分类累加
  * 输入格式为.sql
  */
object OnlineAction_PartyMember {
  def main(args: Array[String]): Unit = {
//    System.setProperty("hadoop.home.dir", "E:\\winutils\\lib")

    val spark = SparkSession
      .builder()
      .appName("OnlineAction_PartyMember")
      .getOrCreate()

        val input = spark.read.textFile(args(0)).rdd
//    val input = spark.read.textFile("G:\\Working\\测试数据\\党建云日志分析数据\\request.log").rdd
    //将操作模块取出来
    val act_module: RDD[String] = input
      .filter(_.startsWith("("))
      .mapPartitions(x => {
        val arr = new ArrayBuffer[String]()
        while (x.hasNext) {
          val next = x.next()
          val arr_tmp = next.split(",", -1)
          if (arr_tmp.length > 8 && arr_tmp(1).startsWith("'") && arr_tmp(1).endsWith("'")) {
            arr += arr_tmp(1).substring(arr_tmp(1).indexOf("'") + 1, arr_tmp(1).lastIndexOf("'"))
          }
        }
        arr.toIterator
      })

    import spark.implicits._

    act_module.persist(StorageLevel.MEMORY_AND_DISK_SER)
      .mapPartitions(x => {
        val arr_tmp = new ArrayBuffer[(String, Integer)]()
        while (x.hasNext) {
          val next_tmp: String = x.next()
          val arr_split: Array[String] = next_tmp.split("/")
          if (arr_split.length==2||arr_split.length==3){
          arr_tmp += ((arr_split(0) + "/" + arr_split(1), 1))
          }
        }
        arr_tmp.toIterator
      })
      .reduceByKey(_ + _).map(x => {
      Act_Module_Count(x._1, x._2)
    }).repartition(1)
      .sortBy(_.count, false)
      .toDF.write
            .json(args(1))
//      .json("G:\\支部数据分析结果临时文件夹\\test")

    spark.stop()
  }

}
