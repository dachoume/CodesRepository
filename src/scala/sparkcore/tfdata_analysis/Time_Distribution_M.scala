package data_analysis_tf

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * Created by Administrator on 2017/8/28.
  */
object Time_Distribution_M {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Time_Distribution_M")
      .getOrCreate()

    val input: RDD[String] = spark.read.textFile(args(0)).rdd

    import spark.implicits._

    input.map(x => {
      (getTime(x).get, 1)
    }).reduceByKey(_ + _)
      .map(x => {
        Time_Distribution_Count_M(x._1.substring(0, 4),
          x._1.substring(4, 6),
          x._2)
      }).repartition(1).toDF.write
      .json(args(1))

    spark.stop()
  }

  private def getTime(t: String): Option[String] = {
    val sdf = new SimpleDateFormat("yyyyMM")
    if (t == "") {
      None
    } else {
      val time = sdf.format(new Date(t.toLong * 1000))
      Some(time)
    }
  }

}
