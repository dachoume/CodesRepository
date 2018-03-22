package data_analysis_tf_v2

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SaveMode, SparkSession}
import org.apache.spark.storage.StorageLevel

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Administrator on 2017/9/6.
  */
object Mobile_Distribution {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Mobile_Distribution")
      .getOrCreate()


    val input = spark.read.textFile(args(0)).rdd

    import spark.implicits._

    //uid,品牌
    val tmp1: RDD[(String, String)] = input.mapPartitions(x => {
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

    val input_users = spark.read.json(args(1))
    input_users.createOrReplaceTempView("users")

    val users_map: collection.Map[Int, Int] = spark.sql("select id,branch_id from users where id is not null").rdd.map({
      case Row(id: String, branch_id: String) => (id.toInt, branch_id.toInt)
    }).collectAsMap()

    val broadcast_map = spark.sparkContext.broadcast(users_map)

    tmp1.map(x => {
      ((broadcast_map.value.getOrElse(x._1.toInt, 0), x._2), 1)
    }).filter(x => {
      x._1._1.toString.toInt != 0
    }).reduceByKey(_ + _).map(x => {
      Mobile_Count(x._1._1, x._1._2, x._2)
    }).toDF.write.mode(SaveMode.Append)
      .format("jdbc")
      .option("url", "jdbc:mysql://10.20.4.4:3306/zbindex_v2?useUnicode=true&characterEncoding=utf-8")
      .option("dbtable", "mobile_distribution")
      .option("user", "root")
      .option("password", "12345678")
      .save()


    spark.stop()
  }

}
