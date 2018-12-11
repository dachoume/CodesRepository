package dao

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Result, Scan}
import org.apache.hadoop.hbase.filter.{CompareFilter, RowFilter, SubstringComparator}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.protobuf.ProtobufUtil
import org.apache.hadoop.hbase.util.{Base64, Bytes}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import utils.PropertiesManager

object ScanHBase {
    def main(args: Array[String]): Unit = {
        val spark = SparkSession
          .builder()
          .appName("ScanHBase")
          .getOrCreate()

        val sc = spark.sparkContext

        val conf = HBaseConfiguration.create()
        conf.set("hbase.zookeeper.quorum", PropertiesManager.getPropertiesManager.getProperties("hbase.properties", "hbase.zookeeper.quorum"))
        conf.set("hbase.zookeeper.property.clientPort", PropertiesManager.getPropertiesManager.getProperties("hbase.properties", "hbase.zookeeper.property.clientPort"))
        conf.set(TableInputFormat.INPUT_TABLE, "test1")


        //    设置开始行键与结束行键，不包含结束行键
        //        conf.set(TableInputFormat.SCAN_ROW_START, "2")
        //        conf.set(TableInputFormat.SCAN_ROW_STOP, "6")

        val scan = new Scan()

        //设置行键过滤器，SubstringComparator表示contains
        val rowfilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator("6"))
        scan.setFilter(rowfilter)

        val proto = ProtobufUtil.toScan(scan)
        val scanToString = Base64.encodeBytes(proto.toByteArray())
        conf.set(TableInputFormat.SCAN, scanToString)

        //Result为数据
        val hbaseRdd: RDD[(ImmutableBytesWritable, Result)] = sc.newAPIHadoopRDD(conf,
            classOf[TableInputFormat],
            classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
            classOf[org.apache.hadoop.hbase.client.Result])

        hbaseRdd.map(_._2).map(result => {
            (Bytes.toString(result.getRow),
              Bytes.toString(result.getValue(Bytes.toBytes("cf1"), Bytes.toBytes("age"))),
              Bytes.toString(result.getValue(Bytes.toBytes("cf1"), Bytes.toBytes("name"))))
        }).foreach(println)

        sc.stop()
        spark.stop()
    }

}
