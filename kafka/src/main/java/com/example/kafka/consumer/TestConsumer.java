package com.example.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 @author yulshi
 @create 2020/03/18 08:46
 */
public class TestConsumer {

  public static void main(String[] args) {

    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("group.id", "test_consumer_group");
    props.put("enable.auto.commit", "true");
    props.put("auto.commit.interval.ms", 1000);
    // 创建消费者
    KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
    // 订阅主题
    consumer.subscribe(Arrays.asList("first"));

    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
      records.forEach(System.out::println);
    }

  }
}
