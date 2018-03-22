package data_analysis_tf

import org.apache.spark.sql.SparkSession

/**
  * Created by Administrator on 2017/8/28.
  */
object Test_Mobile_error {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "D:\\SoftWare\\winutils\\lib")
    val spark = SparkSession
      .builder()
      .appName("Mobile_Distribution_Bak")
      .getOrCreate()

    val input = spark.read.textFile("E:\\test.txt").rdd
    input.map(x=>{
      val arr: Array[String] =x.split("\\|",-1)
      arr.length
    }).saveAsTextFile("E:\\result")

    spark.stop()
  }

}
