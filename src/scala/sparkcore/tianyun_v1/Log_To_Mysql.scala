package tianyun_v1

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql._

import scala.collection.mutable.ArrayBuffer

object Log_To_Mysql {
  def main(args: Array[String]): Unit = {
    // 1 数据源地址 2 字段类型，以竖线划分 3 字段数量 4 数据分割符 5 数据库名 6 数据表名
    //        if (args.length != 6) {
    //            System.err.println("传入的参数数量不正确")
    //            System.exit(1)
    //        }

    System.setProperty("hadoop.home.dir", "D:\\My_Working\\winutils\\lib")

    val input_args = "ip|time_day|time_hour|action|terminal" //字段类型，可以传入

    val input_column_num = 5 //这个数需要在写代码时知道

    val input_split = "|" //数据分割符，可以传入

    val input_database_name = "ty_log_test"

    val input_table_name = "ty_con"

    val spark = SparkSession
      .builder()
      .appName("Log_To_Mysql")
      .getOrCreate()

    import spark.implicits._

    val input: RDD[String] = spark.sparkContext.textFile("D:\\My_Working\\tmp\\result_wx2\\part-00000")

    val fields = input_args.split("\\|")
      .map(fieldName => StructField(fieldName, StringType, nullable = true))
    val schema = StructType(fields)

    val result: RDD[Row] = input.mapPartitions(x => {
      val arr_tmp = new ArrayBuffer[(String, String, String, String, String)]()
      while (x.hasNext) {
        val arr_next: Array[String] = x.next().split("\\" + input_split)
        if (arr_next.length == input_column_num) {
          arr_tmp += ((arr_next(0), arr_next(1), arr_next(2), arr_next(3), arr_next(4)))
        }

      }
      arr_tmp.toIterator
    }).map(x => {
      Row(x._1, x._2, x._3, x._4, x._5)
    })

    val result_df: DataFrame = spark.createDataFrame(result, schema)

    result_df.write.mode(SaveMode.Overwrite)
      .format("jdbc")
      .option("url", "jdbc:mysql://39.104.96.253:3306/" + input_database_name)
      .option("dbtable", input_table_name)
      .option("user", "root")
      .option("password", "Casia123")
      .save()

    spark.stop()

  }

}
