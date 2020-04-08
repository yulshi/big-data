package com.example.networkflow

import java.sql.Timestamp
import java.text.SimpleDateFormat

import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.api.common.state.ListStateDescriptor
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

import scala.collection.mutable.ListBuffer

/**
 * @author yulshi
 * @create 2020/04/05 15:33
 */
object NetworkFlow {

  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    env.readTextFile("")
      .map(data => {
        val sdf = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss")
        val dataArray = data.split(" ")
        ApacheLogEvent(
          dataArray(0).trim,
          dataArray(1).trim,
          sdf.parse(dataArray(3).trim).getTime,
          dataArray(5).trim,
          dataArray(6).trim)
      })
      .assignTimestampsAndWatermarks(
        new BoundedOutOfOrdernessTimestampExtractor[ApacheLogEvent](Time.seconds(1)) {
          override def extractTimestamp(element: ApacheLogEvent): Long = {
            element.eventTime
          }
        })
      .keyBy(_.url)
      .timeWindow(Time.minutes(10), Time.seconds(5))
      .allowedLateness(Time.seconds(60))
      .aggregate(new CountAgg(), new WindowResult())
      .keyBy(_.windowEnd)
      .process(new TopNHotUrls(5))
      .print()

    env.execute()
  }
}

case class ApacheLogEvent(ip: String,
                          userId: String,
                          eventTime: Long,
                          method: String,
                          url: String)

// 窗口聚合结果样例类
case class UrlViewCount(url: String, windowEnd: Long, count: Long)

class CountAgg() extends AggregateFunction[ApacheLogEvent, Long, Long] {
  override def createAccumulator(): Long = 0L

  override def add(value: ApacheLogEvent, accumulator: Long): Long = accumulator + 1

  override def getResult(accumulator: Long): Long = accumulator

  override def merge(a: Long, b: Long): Long = a + b
}

class WindowResult() extends WindowFunction[Long, UrlViewCount, String, TimeWindow] {
  override def apply(key: String,
                     window: TimeWindow,
                     input: Iterable[Long],
                     out: Collector[UrlViewCount]): Unit = {
    out.collect(UrlViewCount(key, window.getEnd, input.iterator.next()))
  }
}

class TopNHotUrls(hotCount: Int) extends KeyedProcessFunction[Long, UrlViewCount, String] {

  lazy val urlState = getRuntimeContext.getListState(new ListStateDescriptor[UrlViewCount]("url-state", classOf[UrlViewCount]))

  override def processElement(value: UrlViewCount,
                              ctx: KeyedProcessFunction[Long, UrlViewCount, String]#Context,
                              out: Collector[String]): Unit = {
    urlState.add(value)
    ctx.timerService().registerEventTimeTimer(value.windowEnd + 1)
  }

  override def onTimer(timestamp: Long,
                       ctx: KeyedProcessFunction[Long, UrlViewCount, String]#OnTimerContext,
                       out: Collector[String]): Unit = {

    val allUrlViews = new ListBuffer[UrlViewCount]

    val iterator = urlState.get().iterator()
    while (iterator.hasNext) {
      allUrlViews += iterator.next()
    }

    urlState.clear()

    val sortedUrlViews = allUrlViews.sortWith(_.count > _.count).take(hotCount)

    val result = new StringBuilder()
    result.append("Time: ").append(new Timestamp(timestamp - 1)).append("\n")

    for (i <- sortedUrlViews.indices) {
      val currentUrlView = sortedUrlViews(i)
      result.append("NO").append(i + 1).append(":")
        .append(" URL=").append(currentUrlView.url)
        .append(" Count=").append(currentUrlView.count)
        .append("\n")
    }
    result.append("======================")

    Thread.sleep(1000)

    out.collect(result.toString())

  }
}
