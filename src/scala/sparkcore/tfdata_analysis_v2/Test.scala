package data_analysis_tf_v2

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SaveMode, SparkSession}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Administrator on 2017/9/6.
  */
object Test {
  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "D:\\SoftWare\\winutils\\lib")

    val spark = SparkSession
      .builder()
      .appName("Test")
      .getOrCreate()

    val input = spark.read.json("E:\\Working\\测试数据\\党建云日志分析数据\\users.json")
    input.createOrReplaceTempView("users")

    val map1: collection.Map[String, String] = spark.sql("select id,branch_id from users where id is not null").rdd.map({
      case Row(id: String, branch_id: String) => (id, branch_id)
    }).collectAsMap()

    val input2=spark.read.textFile("E:\\Working\\测试数据\\党建云日志分析数据\\mobile_distribution_201702.txt").rdd

    val tmp1: RDD[(String, String)] = input2.mapPartitions(x => {
      val arr = new ArrayBuffer[(String, String)]()
      while (x.hasNext) {
        val next: Array[String] = x.next().split("\\|")
        if (next.length == 2) {
          if (next(1).contains("iOS") || next(1).contains("iPhone")) {
            arr += ((next(0), "Apple"))
          } else if (next(1).contains("HUAWEI") || next(1).contains("Honor")) {
            arr += ((next(0), "HUAWEI"))
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
    }).distinct()

    val broadcast_map = spark.sparkContext.broadcast(map1)

    tmp1.map(x => {
      ((broadcast_map.value.getOrElse(x._1, "0"), x._2), 1)
    }).reduceByKey(_ + _).saveAsTextFile("E:\\test")


    spark.stop()
  }

}
