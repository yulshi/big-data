package com.example.flink.basic.source

import java.util.Properties
import java.util.concurrent.TimeUnit

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

import scala.util.Random

/**
 * @author yulshi
 * @create 2020/04/01 08:01
 */
object Sensor {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val stream1 = env.fromCollection(List(
      SensorRecording("1", 42233433, 222.0033),
      SensorRecording("2", 22233433, 223.0033)
    ))

    //stream1.print("stream1").setParallelism(6)

    // 从Kafka中读取数据
    val props: Properties = new Properties
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("group.id", "consumer-group")
//    props.put("enable.auto.commit", "true")
//    props.put("auto.commit.interval.ms", "1000")
    props.put("auto.offset.reset", "latest")

    val kafkaStream = env.addSource(new FlinkKafkaConsumer[String]("sensor", new SimpleStringSchema(), props))

    kafkaStream.print("kafka")

//    val udStream = env.addSource(new SensorRecordSource())
//    udStream.print("user-defined").setParallelism(1)


    env.execute("source test")

  }

}

case class SensorRecording(id: String, timestamp: Long, temperature: Double)

class SensorRecordSource extends SourceFunction[SensorRecording] {

  var running = true

  override def run(ctx: SourceFunction.SourceContext[SensorRecording]): Unit = {

    val rand = new Random()

    val initTemps = 1.to(10).map(i => {
      ("sensor-" + i, 60 + rand.nextGaussian() * 20)
    })

    while(running) {

      initTemps.foreach(t=> {
        val recording = SensorRecording(
          t._1,
          System.currentTimeMillis(),
          t._2 + rand.nextGaussian())
        ctx.collect(recording)
      })

      TimeUnit.SECONDS.sleep(1)

    }

  }

  override def cancel(): Unit = {
    running = false
  }

}