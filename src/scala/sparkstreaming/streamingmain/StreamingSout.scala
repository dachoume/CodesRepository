package streamingmain

import org.apache.hadoop.hbase.client.Connection
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, TaskContext}
import utils.{HBaseManager, PropertiesManager}

object StreamingSout {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setAppName("StreamingSout").setMaster("local[*]")
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


                    partitionRecords.foreach(consumerRecord => {
                        val s: ConsumerRecord[String, String] = consumerRecord
                        println(s)
                        //具体的逻辑1
                    })
                    //具体的逻辑2

                    //检查已经记录的offset和o.fromOffset是否一致，如果一致，则
                    //将o.topic、o.partition、o.untilOffset-1 插入mysql记录偏移量的表中
                    //如果不一致则将却是的offset在另一张表记录下来
                    val o: OffsetRange = offsetRanges(TaskContext.get.partitionId)
                    println(s"The record from topic [${o.topic}] is in partition ${o.partition} which offset from ${o.fromOffset} to ${o.untilOffset}")
                    println(s"The record content is ${partitionRecords.toList.mkString}")

                }
                stream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
            }
        }


        ssc.start()
        ssc.awaitTermination()

    }

}
