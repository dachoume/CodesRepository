package tianyun_v1

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SaveMode, SparkSession}
import utils.DateUtils

import scala.collection.mutable.ArrayBuffer

/**
  * Created by 56308 on 2018/5/10.
  */
object Comment_Time {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "D:\\My_Working\\winutils\\lib")
    val spark = SparkSession
      .builder()
      .appName("Comment_Time")
      .master("local")
      .getOrCreate()

    val input_database_name = "ty_tests_logs"

    val input_args = "uid|time_duration|count_hour|count|prob|prob_p" //字段类型，可以传入

    val input_table_name = "ty_comment_time"

    val input = spark.sparkContext.textFile("D:\\My_Working\\tmp\\tbl_i_activity_cmt.txt")

    val result_tmp: RDD[((String, String), Int)] = input.mapPartitions(x => {
      val arr_tmp = new ArrayBuffer[((String, String), Int)]()
      while (x.hasNext) {
        val next = x.next().split("\\|")
        if (next.length == 2) {
          val hour_some = DateUtils.getDateUtils.getOnlyHour(next(1))
          if (hour_some.isDefined) {
            val hour = hour_some.get.toInt
            val duration: String = hour match {
              case _ if 8 until 12 contains hour => "8 ~ 11 "
              case _ if 12 until 15 contains hour => "12 ~ 14 "
              case _ if 15 until 19 contains hour => "15 ~ 18 "
              case _ if 19 until 24 contains hour => "19 ~ 24 "
              case _ if 0 until 8 contains hour => "1 ~ 7 "
            }
            arr_tmp += (((next(0), duration), 1))
          }
        }
      }
      arr_tmp.toIterator
    })

    //每个uid的评论总次数
    val uid_count: collection.Map[String, Int] = result_tmp.map(x => {
      (x._1._1, x._2)
    }).reduceByKey(_ + _).collectAsMap()

    val result: RDD[Row] = result_tmp.reduceByKey(_ + _).map(x => {
      val sum = uid_count(x._1._1)
      (x._1._1, x._1._2, x._2, sum, (x._2 / sum.toDouble).formatted("%.2f"), (x._2 * 100 / sum.toDouble).formatted("%.2f") + "%")
    }).map(x => {
      Row(x._1.toString, x._2.toString, x._3.toString, x._4.toString, x._5.toString, x._6.toString)
    })

    val fields = input_args.split("\\|")
      .map(fieldName => StructField(fieldName, StringType, nullable = true))
    val schema = StructType(fields)

    val result_df: DataFrame = spark.createDataFrame(result, schema)

    result_df.write.mode(SaveMode.Overwrite)
      .format("jdbc")
      .option("url", "jdbc:mysql://172.18.29.154:3306/" + input_database_name)
      .option("dbtable", input_table_name)
      .option("user", "root")
      .option("password", "root")
      .save()


    spark.stop()
  }

}
