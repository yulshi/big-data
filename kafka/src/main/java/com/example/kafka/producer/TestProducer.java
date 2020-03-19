package com.example.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 @author yulshi
 @create 2020/03/17 21:45
 */
public class TestProducer {

  public static void main(String[] args) {


    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("acks", "1");
    props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,
            Arrays.asList("com.example.kafka.producer.TimeCountIntercepotr"));

    // 增加分区类配置
    //props.put("partitioner.class", "com.example.kafka.producer.MyPartitioner");

    // 创建生产者
    try (Producer<String, String> producer = new KafkaProducer<String, String>(props)) {
      // 准备数据
      ProducerRecord record = new ProducerRecord("first", "Hello Kafka!!!");
      // 发送数据
      producer.send(record, (metadata, e)-> {
        if(e!= null) {
          System.out.println(metadata.partition());
          System.out.println(metadata.offset());
        }
      });
    }

  }

}
