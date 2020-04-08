package com.example.flink.basic.sink

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer
import org.apache.kafka.clients.producer.KafkaProducer

/**
 * @author yulshi
 * @create 2020/04/01 15:26
 */
object KafkaSinkTest {


  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val inputStream = env.readTextFile("/Users/yushi/work/bigdata/flink/basic/src/main/resources/sensor.txt")

    val dataStream = inputStream.map(_.trim)

    dataStream.addSink(
      new FlinkKafkaProducer[String](
        "localhost:9092",
        "sensor",
        new SimpleStringSchema()))


    env.execute("sink")

  }

}
