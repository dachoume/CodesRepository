package tianyun_v1

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object Random_Statistics {
  def main(args: Array[String]): Unit = {
    //  1 数据库名 2 输入数据表名 3 sql 4 输出数据表名
    //        if (args.length != 4) {
    //            System.err.println("传入的参数数量不正确")
    //            System.exit(1)
    //        }
    val sql = "select ip,count(act) num from nginx group by ip "

    val input_database_name = "tianyun_test"

    val input_table_name = "test_sjn_0504"

    val output_table_name = "result_wx"


    val spark = SparkSession
      .builder()
      .appName("Random_Statistics")
      .getOrCreate()

    val input = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://172.18.29.154:3306/" + input_database_name)
      .option("dbtable", input_table_name)
      .option("user", "root")
      .option("password", "root")
      .load()

    input.createOrReplaceTempView("nginx")

    val result: DataFrame = spark.sql(sql)

    result.write.mode(SaveMode.Overwrite)
      .format("jdbc")
      .option("url", "jdbc:mysql://172.18.29.154:3306/" + input_database_name)
      .option("dbtable", output_table_name)
      .option("user", "root")
      .option("password", "root")
      .save()

    //        result.printSchema()


    spark.stop()
  }

}
