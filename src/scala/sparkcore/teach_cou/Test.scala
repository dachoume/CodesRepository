package test

import org.apache.spark.sql.SparkSession

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * Created by Administrator on 2017/11/10.
  */
object Test_Cou {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "D:\\SoftWare\\winutils\\lib")

    val spark = SparkSession
      .builder()
      .appName("cou")
      .getOrCreate()

    val input_feature = spark.read.textFile("C:\\Users\\Administrator\\Desktop\\features").rdd

    val l_feature: List[String] = input_feature.mapPartitions(x => {
      val arr_t = new ArrayBuffer[String]()
      while (x.hasNext) {
        val next: String = x.next().split(" ", -1)(1)
        if (next != "") {
          arr_t += next
        }
      }
      arr_t.toIterator
    }).countByValue().keys.toList

    val test = input_feature.map(x => {
      val a = x.split(" ", -1)
      (a(1), a(0))
    }).countByValue()

    val hashmap = new mutable.HashMap[String, Int]()
    test.map(x => {
      hashmap += (x._1._1 -> x._1._2.toInt)
    })

    //    println(hashmap.size)


    val input_data = spark.read.textFile("C:\\Users\\Administrator\\Desktop\\BW").rdd


    input_data.repartition(1).mapPartitions(x => {
      val arr_d = new ArrayBuffer[String]()
      while (x.hasNext) {
        val arr_tmp = new ArrayBuffer[(String, String)]()

        val list = x.next().split(" ", -1).toList.filter(_ != "")

        val list_row = list.drop(1)

        val tmp: List[String] = l_feature.diff(list_row)

        //        arr_tmp += list.take(1).head
        for (i <- list_row) {

          arr_tmp += ((i, ":1"))
        }
        for (j <- tmp) {
          arr_tmp += ((j, ":0"))
        }

        //        arr_d ++= arr_tmp
        val arr_d_tmp = arr_tmp.toList.map(x => {
          (hashmap.getOrElse(x._1, 0), x._2)
        }).sortBy(_._1).map(x => {
          x._1.toString + x._2
        })

        val tmp_t: List[String] = list.take(1).head :: arr_d_tmp

        arr_d += tmp_t.mkString(" ")

      }
      arr_d.toIterator
    })//.saveAsTextFile("C:\\Users\\Administrator\\Desktop\\test")


    spark.stop()
  }

}
