package kafka_test;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ProducerToHBase {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers",
                "172.18.29.154:9092");//该地址是集群的子集，用来探测集群。
        props.put("acks", "all");// 记录完整提交，最慢的但是最大可能的持久化
        props.put("retries", 3);// 请求失败重试的次数
        props.put("batch.size", 16384);// batch的大小
        props.put("linger.ms", 1);// 默认情况即使缓冲区有剩余的空间，也会立即发送请求，设置一段时间用来等待从而将缓冲区填的更多，单位为毫秒，producer发送数据会延迟1ms，可以减少发送到kafka服务器的请求数据
        props.put("buffer.memory", 33554432);// 提供给生产者缓冲内存总量
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");// 序列化的方式，
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        //key可以为时间戳（rowkey），value为family|name|age
        producer.send(new ProducerRecord<String, String>("test_0314", "888", "cf1|zhangfei|39"));

        producer.close();
    }
}
