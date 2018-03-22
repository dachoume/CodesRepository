package testcou

import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}


/**
  * Created by 56308 on 2017/11/29.
  */
object Test {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "D:\\My_Working\\winutils\\lib")
    val spark = SparkSession
      .builder()
      .appName("Teach_Cou")
      .getOrCreate()

    import spark.implicits._

    val input = spark.read.textFile("C:\\Users\\56308\\Desktop\\新建文本文档.txt")
    val input_df: DataFrame = input.map(x => {
      val arr = x.split(" ", -1)
      Weight(arr(0), arr(1))
    }).toDF()

    input_df.createOrReplaceTempView("users")

    spark.udf.register("test",Cou_UDAF2)

//    spark.sql("select weight,test(name) from users group by weight").show()
    spark.sql("select test(name) from users group by weight").repartition(1).write.text("C:\\Users\\56308\\Desktop\\cou")

    spark.stop()
  }

}
