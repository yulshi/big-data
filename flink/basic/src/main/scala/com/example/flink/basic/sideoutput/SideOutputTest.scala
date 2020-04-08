package com.example.flink.basic.sideoutput

import com.example.flink.basic.processfunc.TemperatureContinuousIncreaseAlert
import com.example.flink.basic.window.SensorReading
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector

/**
 * @author yulshi
 * @create 2020/04/02 17:05
 */
object SideOutputTest {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val sourceStream = env.readTextFile("/Users/yushi/work/bigdata/flink/basic/src/main/resources/sensor.txt")

    val dataStream = sourceStream.map(line => {
      val fields = line.split(",")
      SensorReading(fields(0).trim, fields(1).trim.toLong, fields(2).trim.toDouble)
    })

    dataStream.keyBy(_.id)
      .timeWindow(Time.seconds(60))

    // 把温度低于32F的数据输出到侧输出流
    val processedStream = dataStream
      .process(new FreezingAlert())

    processedStream
      .print("processed data")

    processedStream.getSideOutput(
      new OutputTag[String]("freezing"))
      .print("freezing")

    env.execute("process function")

  }

}

class FreezingAlert() extends ProcessFunction[SensorReading, SensorReading] {

  override def processElement(value: SensorReading,
                              ctx: ProcessFunction[SensorReading, SensorReading]#Context,
                              out: Collector[SensorReading]): Unit = {
    if (value.temperature < 32.0) {
      ctx.output(new OutputTag[String]("freezing"), "freezing for " + value.id)
    } else {
      out.collect(value)
    }
  }

}
