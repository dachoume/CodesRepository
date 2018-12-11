package dao

import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.client.{Connection, Put}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.sql.{Dataset, SparkSession}
import utils.HBaseManager

object Write2HBase {
    def main(args: Array[String]): Unit = {
        val spark = SparkSession
          .builder()
          .appName("write2hbase")
          .getOrCreate()

        val sc = spark.sparkContext
        import spark.implicits._
        val input: Dataset[String] = spark.read.textFile("/Users/dxdou/IdeaProjects/StreamingProj/src/resources/hbasefile.txt")

        input.map(x => {
            val arr = x.split("\\|")
            (arr(0), arr(1), arr(2))
        }).foreachPartition(x => {
            val hbaseconnection: Connection = HBaseManager.getHBaseManager.getConnection //创建HBaseConnection
            x.foreach(result => {
                val tableName = TableName.valueOf("test1")
                val t = hbaseconnection.getTable(tableName)

                try {
                    val put = new Put(Bytes.toBytes(result._1)) // row key
                    // column, qualifier, value
                    put.addColumn("cf1".getBytes, "name".getBytes, result._2.getBytes)
                    put.addColumn("cf1".getBytes, "age".getBytes, result._3.getBytes)
                    t.put(put)
                    //                    Try(t.put(put)).getOrElse(t.close())
                    // do some log（显示在worker上）
                } catch {
                    case e: Exception =>

                        println(result)
                        // log error
                        e.printStackTrace()
                } finally {
                    t.close()
                }
            })
            hbaseconnection.close()
        })


        spark.stop()
    }

}
