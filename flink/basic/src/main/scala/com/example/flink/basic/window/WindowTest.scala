package com.example.flink.basic.window

import org.apache.flink.api.common.restartstrategy.RestartStrategies
import org.apache.flink.streaming.api.environment.CheckpointConfig.ExternalizedCheckpointCleanup
import org.apache.flink.streaming.api.{CheckpointingMode, TimeCharacteristic}
import org.apache.flink.streaming.api.functions.{AssignerWithPeriodicWatermarks, AssignerWithPunctuatedWatermarks, KeyedProcessFunction}
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.assigners.{TumblingEventTimeWindows, WindowAssigner}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector

/**
 * @author yulshi
 * @create 2020/04/02 11:43
 */
object WindowTest {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    env.setParallelism(1)

    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val sourceStream = env.socketTextStream("localhost", 7777)

    val dataStream = sourceStream.map(line => {
      val fields = line.split(",")
      SensorReading(fields(0).trim, fields(1).trim.toLong, fields(2).trim.toDouble)
    })

    // 使用自定义的AssignerWithPeriodicWatermarks
    val timedStreamPeriod = dataStream.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks[SensorReading] {

      val bound = 1 * 1000
      var maxTs = Long.MinValue

      override def getCurrentWatermark: Watermark = {
        new Watermark(maxTs - bound)
      }

      override def extractTimestamp(element: SensorReading, previousElementTimestamp: Long): Long = {
        maxTs = maxTs.max(element.timestamp * 1000)
        element.timestamp * 1000
      }
    })

    // 使用自定义的AssignerWithPunctuatedWatermarks
    val timedStreamPunc = dataStream.assignTimestampsAndWatermarks(new AssignerWithPunctuatedWatermarks[SensorReading] {
      override def checkAndGetNextWatermark(lastElement: SensorReading, extractedTimestamp: Long): Watermark = {
        if (!lastElement.id.startsWith("sensor-1")) {
          new Watermark(lastElement.timestamp)
        } else {
          null
        }
      }

      override def extractTimestamp(element: SensorReading, previousElementTimestamp: Long): Long = {
        element.timestamp * 1000
      }
    })

    // 使用BoundedOutOfOrdernessTimestampExtractor
    val timedStreamOutorder = dataStream
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[SensorReading](Time.seconds(1)) {
        override def extractTimestamp(element: SensorReading): Long = element.timestamp * 1000
      })

    timedStreamPeriod
      .keyBy(_.id)
      //      .window(TumblingEventTimeWindows.of(Time.seconds(5)))
      .timeWindow(Time.seconds(5))
      .reduce((a, b) => {
        if (a.temperature < b.temperature) a else b
        //SensorReading(a.id, if(a.temperature<b.temperature) a.timestamp else b.timestamp, a.temperature.min(b.temperature))
      }).print("min~~~")

    dataStream.keyBy(_.id)
        .process(new KeyedProcessFunction[String, SensorReading, String] {
          override def processElement(value: SensorReading,
                                      ctx: KeyedProcessFunction[String, SensorReading, String]#Context,
                                      out: Collector[String]): Unit = {
            ctx.timerService().registerEventTimeTimer(2000)
          }

          override def onTimer(timestamp: Long,
                               ctx: KeyedProcessFunction[String, SensorReading, String]#OnTimerContext,
                               out: Collector[String]): Unit =  {

          }
        })

    dataStream.print("socket")

    env.execute("window")

  }

}

case class SensorReading(id: String, timestamp: Long, temperature: Double)