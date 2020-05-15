package com.example.kafka.producer;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 @author yulshi
 @create 2020/03/18 14:36
 */
public class TimeCountIntercepotr implements ProducerInterceptor<String, String> {

  int successCount = 0;
  int failureCount = 0;

  @Override
  public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
    String oldValue = record.value();
    String newValue = System.currentTimeMillis() + "_" + oldValue;
    return new ProducerRecord<>(record.topic(),
            record.partition(), record.timestamp(),
            record.key(), newValue);
  }

  @Override
  public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
    if (exception == null)
      successCount++;
    else
      failureCount++;
  }

  @Override
  public void close() {
    System.out.println("Succeded: " + successCount);
    System.out.println("Failed: " + failureCount);
  }

  @Override
  public void configure(Map<String, ?> configs) {

  }
}
