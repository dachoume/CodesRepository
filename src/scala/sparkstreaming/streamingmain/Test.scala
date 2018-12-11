package streamingmain

import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Put}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, TaskContext}
import org.apache.spark.streaming.{Milliseconds, Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.{CanCommitOffsets, HasOffsetRanges, KafkaUtils, OffsetRange}
import utils.HBaseManager

object Test {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setAppName("StreamingSout_Test")
        val ssc = new StreamingContext(sparkConf, Seconds(10)) //Milliseconds(500)

        val kafkaParams = scala.collection.Map[String, Object](
            "bootstrap.servers" -> "172.18.29.154:9092",
            "key.deserializer" -> classOf[StringDeserializer],
            "value.deserializer" -> classOf[StringDeserializer],
            "group.id" -> "test_1",
            "auto.offset.reset" -> "earliest",
            "partition.assignment.strategy" -> "org.apache.kafka.clients.consumer.RangeAssignor",
            "enable.auto.commit" -> (false: java.lang.Boolean)
        )

        val topics = Array("test_0314") //指定主题

        val stream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
            ssc,
            PreferConsistent,
            Subscribe[String, String](topics, kafkaParams)
        ) //创建流

        stream.foreachRDD { rdd =>
//          val x: RDD[ConsumerRecord[String, String]] = rdd
            if (!rdd.isEmpty()) {
                val offsetRanges: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges

                rdd.foreachPartition { partitionRecords =>
                    //        val x: Iterator[ConsumerRecord[String, String]] = partitionRecords

                    val conf = HBaseConfiguration.create()
                    conf.set("hbase.zookeeper.quorum", "172.18.29.154")
                    conf.set("hbase.zookeeper.property.clientPort", "2181")
                    conf.set("hbase.znode.parent", "/hbase")
                    val hbaseconnection = ConnectionFactory.createConnection(conf)


                    partitionRecords.foreach(consumerRecord => {
                        val tableName = TableName.valueOf("test1")
                        val t = hbaseconnection.getTable(tableName)


                        val put = new Put(Bytes.toBytes(consumerRecord.key())) // row key
                        val value: String = consumerRecord.value()
                        val tmp: Array[String] = value.split("\\|")
                        // column, qualifier, value
                        put.addColumn(tmp(0).getBytes, "name".getBytes, tmp(1).getBytes)
                        put.addColumn(tmp(0).getBytes, "age".getBytes, tmp(2).getBytes)
                        //                            Try(t.put(put)).getOrElse(t.close())
                        t.put(put)
                        // do some log（显示在worker上）

                        t.close()


                    })
                    //具体的逻辑2

                    //检查已经记录的offset和o.fromOffset是否一致，如果一致，则
                    //将o.topic、o.partition、o.untilOffset-1 插入mysql记录偏移量的表中
                    //如果不一致则将却是的offset在另一张表记录下来
                    //                    val o: OffsetRange = offsetRanges(TaskContext.get.partitionId)
                    //                    println(s"The record from topic [${o.topic}] is in partition ${o.partition} which offset from ${o.fromOffset} to ${o.untilOffset}")
                    //                    println(s"The record content is ${partitionRecords.toList.mkString}")

                    hbaseconnection.close() //关闭HBaseConnection
                }
                stream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
            }
        }


        ssc.start()
        ssc.awaitTermination()

    }

}
