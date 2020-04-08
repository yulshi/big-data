package com.example.flink.basic.sink

import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.connectors.redis.RedisSink
import org.apache.flink.streaming.connectors.redis.common.config.{FlinkJedisConfigBase, FlinkJedisPoolConfig}
import org.apache.flink.streaming.connectors.redis.common.mapper.{RedisCommand, RedisCommandDescription, RedisMapper}

/**
 * @author yulshi
 * @create 2020/04/01 15:52
 */
object RedisSinkTest {


  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val inputStream = env.readTextFile("/Users/yushi/work/bigdata/flink/basic/src/main/resources/sensor.txt")

    val dataStream = inputStream.map(line => {
      val fields = line.split(",")
      SensorRecording(fields(0).trim, fields(1).trim.toLong, fields(2).trim.toDouble)
    })

    val conf: FlinkJedisConfigBase = new FlinkJedisPoolConfig.Builder()
      .setHost("localhost")
      .setPort(6379)
      .build()

    dataStream.addSink(new RedisSink(conf, new RedisMapper[SensorRecording] {

      override def getCommandDescription: RedisCommandDescription = {
        new RedisCommandDescription(RedisCommand.HSET, "sensor-temperature")
      }

      override def getKeyFromData(data: SensorRecording): String = data.id

      override def getValueFromData(data: SensorRecording): String = data.temperature.toString

    }))


    env.execute("sink")

  }

}


case class SensorRecording(id: String, timestamp: Long, temperature: Double)