package com.example.flink.basic.transform

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

import scala.collection.Seq

/**
 * @author yulshi
 * @create 2020/04/01 10:17
 */
object TransformTest {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    env.disableOperatorChaining()

    val streamFromFile = env.readTextFile("/Users/yushi/work/bigdata/flink/basic/src/main/resources/sensor.txt")

    val dataStream = streamFromFile.map(line => {
      val fields = line.split(",")
      SensorRecording(fields(0).trim, fields(1).trim.toLong, fields(2).trim.toDouble)
    })

//    val aggStream = dataStream.keyBy(_.id)
//      .reduce((a, b) => if (a.temperature > b.temperature) a else b)
//    aggStream.print("agg")

    val splitStream = dataStream.split(sr => {
      if (sr.temperature > 30) Seq("high") else Seq("low")
    })

    val high = splitStream.select("high")
    val low = splitStream.select("low")
    val all = splitStream.select("high", "low")

//    high.print("high")
//    low.print("low")
//    all.print("all")

    val warningStream = high.map(data => (data.id, data.temperature, "warning"))
    val normalSteam = low.map(data => (data.id, data.temperature))

    val connectedStream = warningStream.connect(normalSteam)
    val mapedStream = connectedStream.map(
      warning => (warning._1, warning._2, "warning"),
      normal => (normal._1, normal._2)
    )
    mapedStream.print("connect-map")


    env.execute("transform-test")

  }

}

case class SensorRecording(id: String, timestamp: Long, temperature: Double)
