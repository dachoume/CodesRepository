package tianyun_v1

import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SaveMode, SparkSession}
import utils.DateUtils

import scala.collection.mutable.ArrayBuffer

/**
  * 用户每月评论的次数
  */
object Comment_Count_M {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "D:\\My_Working\\winutils\\lib")
    val spark = SparkSession
      .builder()
      .appName("Comment_Count_M")
      .master("local")
      .getOrCreate()

    val input_database_name = "ty_tests_logs"

    val input_args = "uid|time_month|count" //字段类型，可以传入

    val input_table_name = "ty_comment_count_m"

    //uid|时间戳
    val input = spark.sparkContext.textFile("D:\\My_Working\\tmp\\tbl_i_activity_cmt.txt")

    val result = input.mapPartitions(x => {
      val arr_tmp = new ArrayBuffer[((String, String), Int)]()
      while (x.hasNext) {
        val next = x.next().split("\\|")
        if (next.length == 2) {
          val time_month = DateUtils.getDateUtils.getMonth(next(1))
          if (time_month.isDefined) {
            arr_tmp += (((next(0), time_month.get), 1))
          }
        }
      }
      arr_tmp.toIterator
    }).reduceByKey(_ + _)
      .map(x => {
        Row(x._1._1, x._1._2, x._2.toString)
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
