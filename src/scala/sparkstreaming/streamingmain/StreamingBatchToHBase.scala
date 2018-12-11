package streamingmain

import java.util

import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.client.{Connection, Put, Table}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.{SparkConf, TaskContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.{CanCommitOffsets, HasOffsetRanges, KafkaUtils, OffsetRange}
import utils.{HBaseManager, HBaseUtils, PropertiesManager}

import scala.collection.mutable.ArrayBuffer
import scala.collection.JavaConverters._

/**
  * 按分区插入数据
  */
object StreamingBatchToHBase {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setAppName("StreamingBatchToHBase").setMaster("local[2]")
        val ssc = new StreamingContext(sparkConf, Seconds(10))

        val kafkaParams = scala.collection.Map[String, Object](
            "bootstrap.servers" -> PropertiesManager.getPropertiesManager.getProperties("kafka.properties", "bootstrap.servers"),
            "key.deserializer" -> classOf[StringDeserializer],
            "value.deserializer" -> classOf[StringDeserializer],
            "group.id" -> PropertiesManager.getPropertiesManager.getProperties("kafka.properties", "group.id"),
            "auto.offset.reset" -> PropertiesManager.getPropertiesManager.getProperties("kafka.properties", "auto.offset.reset"),
            "partition.assignment.strategy" -> PropertiesManager.getPropertiesManager.getProperties("kafka.properties", "partition.assignment.strategy"),
            "enable.auto.commit" -> (false: java.lang.Boolean)
        )

        val topics = Array("test_0314") //指定主题

        val stream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
            ssc,
            PreferConsistent,
            Subscribe[String, String](topics, kafkaParams)
        ) //创建流

        stream.foreachRDD { rdd =>
            if (!rdd.isEmpty()) {
                val offsetRanges: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges

                rdd.foreachPartition { partitionRecords =>
                    //        val x: Iterator[ConsumerRecord[String, String]] = partitionRecords

                    val hbaseConnection: Connection = HBaseManager.getHBaseManager.getConnection //创建HBaseConnection
                val table: Table = hbaseConnection.getTable(TableName.valueOf("test1"))


                    var list: ArrayBuffer[Put] = new ArrayBuffer[Put]()

                    while (partitionRecords.hasNext) {
                        //                            val nextRecord: ConsumerRecord[String, String] = partitionRecords.next()
                        //                            val put = new Put(Bytes.toBytes(nextRecord.key()))
                        //                            val arr_value: Array[String] = nextRecord.value().split("\\|")
                        //                            //可以加入对于arr_value的长度的判别
                        //                            put.addColumn(arr_value(0).getBytes, "name".getBytes, arr_value(1).getBytes)
                        //                            put.addColumn(arr_value(0).getBytes, "age".getBytes, arr_value(2).getBytes)
                        //                            list += put
                        val nextRecord: ConsumerRecord[String, String] = partitionRecords.next()
                        val arr_value: Array[String] = nextRecord.value().split("\\|")
                        HBaseUtils.putListData(list, nextRecord.key(), arr_value(0), "name", arr_value(1))
                        HBaseUtils.putListData(list, nextRecord.key(), arr_value(0), "age", arr_value(2))
                    }

                    table.put(list.asJava) //这里需将list转换为java格式的

                    table.close()
                    hbaseConnection.close()


                    //具体的逻辑2
                    //检查已经记录的offset和o.fromOffset是否一致，如果一致，则
                    //将o.topic、o.partition、o.untilOffset-1 插入mysql记录偏移量的表中
                    //如果不一致则将却是的offset在另一张表记录下来
                    val o: OffsetRange = offsetRanges(TaskContext.get.partitionId)
                    println(s"The record from topic [${o.topic}] is in partition ${o.partition} which offset from ${o.fromOffset} to ${o.untilOffset}")
                    println(s"The record content is ${partitionRecords.toList.mkString}")

                }
                //提交偏移量
                stream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
            }
        }


        ssc.start()
        ssc.awaitTermination()

    }

}
