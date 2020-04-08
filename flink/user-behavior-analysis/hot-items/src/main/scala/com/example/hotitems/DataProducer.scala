package com.example.hotitems

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
 * @author yulshi
 * @create 2020/04/05 15:15
 */
object DataProducer {


  def main(args: Array[String]): Unit = {
    sendData("hot-items")
  }


  def sendData(topic: String): Unit = {

    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("acks", "1")

    val producer = new KafkaProducer[String, String](props)

    val source = io.Source.fromFile("user-behavior.csv")
    for (line <- source.getLines()) {
      val record = new ProducerRecord[String, String](topic, line)
      producer.send(record)
    }

    producer.close()

  }

}
