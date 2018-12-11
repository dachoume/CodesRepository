package dao

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{SaveMode, SparkSession}

/**
  * 测试将数据保存到mysql中
  * 运行jar包时需要 --driver-class-path /path/mysql-connector-java.jar (一定要写成绝对路径)
  */
object WriteToMySQL {
    def main(args: Array[String]): Unit = {
        val spark = SparkSession
          .builder()
          .appName("writetomysql")
          .getOrCreate()

        import spark.implicits._


        val input: RDD[String] = spark.read.textFile(args(0)).rdd


        val temp: RDD[(Int, String, Int)] = input.map(x => {
            val arr = x.split("\\|")
            (arr(0).toInt, arr(1), arr(2).toInt)
        })

        temp.map(x => {
            User(x._1, x._2, x._3)
        }).toDF().write.mode(SaveMode.Append)
          .format("jdbc")
          .option("url", "jdbc:mysql://172.18.29.154:3306/tianyun_test")
          .option("dbtable", "test_sjn_0321")
          .option("user", "root")
          .option("password", "root")
          .save()
        spark.stop()
    }

}

case class User(id: Int, name: String, age: Int)
