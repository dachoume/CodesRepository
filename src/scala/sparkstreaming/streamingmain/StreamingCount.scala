package streamingmain

import java.sql.Connection

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, TaskContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.{CanCommitOffsets, HasOffsetRanges, KafkaUtils, OffsetRange}
import utils.{MySQLPoolManager, PropertiesManager}

/**
  * ./spark-submit --master yarn --deploy-mode cluster --class StreamingMain.StreamingCount  /sjn/StreamingProj-1.0-SNAPSHOT-jar-with-dependencies.jar --driver-class-path /sjn/tooljar/mysql-connector-java.jar /sjn/tooljar/c3p0-0.9.1.2.jar
  */
object StreamingCount {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setAppName("StreamingCount")
        //.setMaster("local[*]")
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

        val topics = Array("test_0410") //指定主题

        val stream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
            ssc,
            PreferConsistent,
            Subscribe[String, String](topics, kafkaParams)
        ) //创建流

        stream.foreachRDD { rdd =>
            if (!rdd.isEmpty()) {
                val offsetRanges: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
                val result: RDD[(String, Int)] = rdd.map(x => {
                    (x.value(), 1)
                }).reduceByKey(_ + _)

                result.foreachPartition { partitionRecords =>
                    //        val x: Iterator[ConsumerRecord[String, String]] = partitionRecords

                    val conn: Connection = MySQLPoolManager.getMysqlPoolManager.getConnection //创建数据库连接

                    conn.setAutoCommit(false)

                    val sql = "insert into sjn_test_StreamingCount (value,count) values(?,?)"
                    val ps = conn.prepareStatement(sql)

                    var i = 0

                    while (partitionRecords.hasNext) {
                        val next: (String, Int) = partitionRecords.next()
                        ps.setString(1, next._1)
                        ps.setInt(2, next._2)
                        ps.addBatch()
                        i = i + 1

                        if (i > 0 && i % 500 == 0) {
                            ps.executeBatch()
                            conn.commit()
                            i = 0
                        }
                    }

                    ps.executeBatch()
                    MySQLPoolManager.getMysqlPoolManager.closeStat(ps)

                    conn.commit()
                    conn.setAutoCommit(true)

                    /*                    //这里是一条数据塞一次
                                        partitionRecords.foreach(Record => {
                                            val sql = "insert into sjn_test_StreamingCount (value,count) values(?,?)"
                                            val ps = conn.prepareStatement(sql)
                                            ps.setString(1, Record._1)
                                            ps.setInt(2, Record._2)
                                            ps.executeUpdate()

                                            MySQLPoolManager.getMysqlPoolManager.closeStat(ps)


                                        })*/

                    MySQLPoolManager.getMysqlPoolManager.closeConn(conn) //关闭Mysql连接池
                }
                stream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
            }
        }


        ssc.start()
        ssc.awaitTermination()

    }


}
