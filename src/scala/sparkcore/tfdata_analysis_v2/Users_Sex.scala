package data_analysis_tf_v2

import org.apache.spark.sql.{Row, SaveMode, SparkSession}

/**
  * Created by Administrator on 2017/9/6.
  */
object Users_Sex {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Users_Sex")
      .getOrCreate()

    val input = spark.read.json(args(0))
    input.createOrReplaceTempView("users")

    val map_sex = Map[String, String](("0", "未设置"), ("1", "男"), ("2", "女"))

    val tmp = spark.sql("select branch_id,sex from users where id is not null").rdd.map({
      case Row(branch_id: String, sex: String) => ((branch_id, sex), 1)
    }).reduceByKey(_ + _).map(x => {
      (x._1._1, map_sex(x._1._2), x._2)
    }).map(x => {
      Sex_Count(x._1.toInt, x._2, x._3)
    })

    import spark.implicits._

    tmp.repartition(1).toDF.write.mode(SaveMode.Append).format("jdbc")
      .option("url","jdbc:mysql://10.20.4.4:3306/zbindex_v2?useUnicode=true&characterEncoding=utf-8")
      .option("dbtable","users_sex")
      .option("user","root")
      .option("password","12345678").save()


    spark.stop()
  }

}
