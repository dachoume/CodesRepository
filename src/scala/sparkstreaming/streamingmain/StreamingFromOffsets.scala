package streamingmain

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.{SparkConf, TaskContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Assign
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.{CanCommitOffsets, HasOffsetRanges, KafkaUtils, OffsetRange}
import utils.PropertiesManager

/**
  * 从指定位置开始消费
  */
object StreamingFromOffsets {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setAppName("StreamingSout").setMaster("local[2]")
        val ssc = new StreamingContext(sparkConf, Seconds(10))

        val kafkaParams: collection.Map[String, Object] = scala.collection.Map[String, Object](
            "bootstrap.servers" -> PropertiesManager.getPropertiesManager.getProperties("kafka.properties", "bootstrap.servers"),
            "key.deserializer" -> classOf[StringDeserializer],
            "value.deserializer" -> classOf[StringDeserializer],
            "group.id" -> PropertiesManager.getPropertiesManager.getProperties("kafka.properties", "group.id"),
            "auto.offset.reset" -> PropertiesManager.getPropertiesManager.getProperties("kafka.properties", "auto.offset.reset"),
            "partition.assignment.strategy" -> PropertiesManager.getPropertiesManager.getProperties("kafka.properties", "partition.assignment.strategy"),
            "enable.auto.commit" -> (false: java.lang.Boolean)
        )

        //创建一个map用来存储想要开始消费的位置
        val fromOffsets: collection.Map[TopicPartition, Long] = scala.collection.Map[TopicPartition, Long](
            new TopicPartition("test_0314", 0) -> 13 //topics,partitions   offsets
        )

        val stream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
            ssc,
            PreferConsistent,
            Assign[String, String](fromOffsets.keys.toList, kafkaParams, fromOffsets)
        )

        stream.foreachRDD { rdd =>
            if (!rdd.isEmpty()) {
                val offsetRanges: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges

                rdd.foreachPartition { iter =>
                    //        val x: Iterator[ConsumerRecord[String, String]] = item

                    iter.foreach(consumerRecord => {
                        val s: ConsumerRecord[String, String] = consumerRecord
                        println(s)
                    })
                    val o: OffsetRange = offsetRanges(TaskContext.get.partitionId)
                    println(s"The record from topic [${o.topic}] is in partition ${o.partition} which offset from ${o.fromOffset} to ${o.untilOffset}")
                    println(s"The record content is ${iter.toList.mkString}")


                }
                stream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
            }
        }


        ssc.start()
        ssc.awaitTermination()

    }

}
