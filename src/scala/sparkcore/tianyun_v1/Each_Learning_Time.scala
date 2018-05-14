package tianyun_v1

import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SaveMode, SparkSession}

import scala.collection.mutable.ArrayBuffer

/**
  * 用户平均每次学习的时间
  */
object Each_Learning_Time {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "D:\\My_Working\\winutils\\lib")
    val spark = SparkSession
      .builder()
      .appName("Each_Learning_Time")
      .getOrCreate()

    val input_database_name = "ty_tests_logs"

    val input_args = "uid|each_time" //字段类型，可以传入

    val input_table_name = "each_learning_time"

    val input = spark.sparkContext.textFile("D:\\My_Working\\tmp\\tbl_i_materials_browse_time.txt")

    val result = input.mapPartitions(x => {
      val arr_tmp = new ArrayBuffer[(String, Int)]()
      while (x.hasNext) {
        val next = x.next().split("\\|")
        if (next.length == 3) {
          arr_tmp += ((next(0), next(1).toInt))
        }
      }
      arr_tmp.toIterator
    }).groupByKey().map(x => {
      (x._1, (x._2.sum / x._2.count(x => true).toDouble).formatted("%.2f"))
    }).map(x => {
      Row(x._1, x._2)
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
