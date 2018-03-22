package testcou

import org.apache.spark.sql.{Dataset, Row, SaveMode, SparkSession}

import scala.collection.mutable.ArrayBuffer
/**
  * Created by 56308 on 2017/11/28.
  */
object TestCase_DataSet {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("TestCase_DataSet")
      .getOrCreate()
//    val input: Dataset[String] =spark.read.textFile("")
    //    input.mapPartitions(x=>{
    //      val arr_result=new ArrayBuffer[(String,Int)]()
    //      while (x.hasNext) {
    //        val next: String =x.next()
    //      }
    //      arr_result.toIterator
    //    })


    spark.stop()
  }

}
