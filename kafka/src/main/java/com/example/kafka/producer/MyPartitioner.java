package com.example.kafka.producer;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 @author yulshi
 @create 2020/03/17 22:53
 */
public class MyPartitioner implements Partitioner {
  @Override
  public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
    return value.hashCode() % 4;
  }
  @Override
  public void close() {}
  @Override
  public void configure(Map<String, ?> configs) {}
}
