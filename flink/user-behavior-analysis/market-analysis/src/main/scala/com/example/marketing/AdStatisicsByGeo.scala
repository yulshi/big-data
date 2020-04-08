package com.example.marketing

import java.sql.Timestamp

import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.api.common.state.ValueStateDescriptor
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
 * @author yulshi
 * @create 2020/04/05 21:50
 */
object AdStatisicsByGeo {

  // 定义侧输出流的标签
  val blackListOutputTag: OutputTag[BlackListWarning] = new OutputTag[BlackListWarning]("blacklist")

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val resource = getClass.getResource("/AdClickLog.csv")

    val adEventStream = env.readTextFile(resource.getPath)
      .map(line => {
        val fields = line.split(",")
        AdClickEvent(fields(0).trim.toLong, fields(1).trim.toLong, fields(2).trim, fields(3).trim, fields(4).trim.toLong)
      })
      .assignAscendingTimestamps(_.timestamp * 1000)

    // 自定义process function，过滤大量刷点击的行为
    val filterBlackListStream = adEventStream
      .keyBy(data => (data.userId, data.adId))
      .process(new FilterBlackListUser(100))

    // 根据省份分组，开窗聚合
    val adCountStream = filterBlackListStream
      .keyBy(_.province)
      .timeWindow(Time.hours(1), Time.seconds(5))
      .aggregate(new AdCountAgg(), new AdCountResult())

    adCountStream.print()

    env.execute("ad statistics by province")

  }

  class FilterBlackListUser(maxCount: Int) extends KeyedProcessFunction[(Long, Long), AdClickEvent, AdClickEvent] {

    // 保存当前用户对当前广告的点击量
    lazy val countState = getRuntimeContext.getState[Long](new ValueStateDescriptor[Long]("count-state", classOf[Long]))

    // 保存是否发送过黑名单的状态
    lazy val isSentBlackList = getRuntimeContext.getState[Boolean](new ValueStateDescriptor[Boolean]("issent-state", classOf[Boolean]))

    // 保存定时器触发的时间戳
    lazy val resetTimer = getRuntimeContext.getState[Long](new ValueStateDescriptor[Long]("resettime-state", classOf[Long]))

    override def processElement(value: AdClickEvent,
                                ctx: KeyedProcessFunction[(Long, Long), AdClickEvent, AdClickEvent]#Context, out:
                                Collector[AdClickEvent]): Unit = {

      // 取出count的状态
      val currCount = countState.value()

      // 如果是第一次处理，注册定时器，每天00:00点触发
      if (currCount == 0) {
        // 得到第二天的00:00
        val ts = (ctx.timerService().currentProcessingTime() / (1000 * 60 * 60 * 24) + 1) * (1000 * 60 * 60 * 24)
        resetTimer.update(ts)
        ctx.timerService().registerProcessingTimeTimer(ts)
      }

      // 判断计数是否达到上限，达到则放入黑名单
      if(currCount >= maxCount) {
        // 判断是否发送过黑名单
        if(!isSentBlackList.value()) {
          // 输出到侧输出流
          ctx.output(blackListOutputTag, BlackListWarning(value.userId, value.adId, "Click over " + maxCount + " times today"))
          isSentBlackList.update(true)
        }
        return
      }

      // 计数状态加1，输出数据到主流
      countState.update(currCount + 1)
      out.collect(value)

    }

    //
    override def onTimer(timestamp: Long,
                         ctx: KeyedProcessFunction[(Long, Long), AdClickEvent, AdClickEvent]#OnTimerContext,
                         out: Collector[AdClickEvent]): Unit = {
      if(timestamp == resetTimer.value()) {
        resetTimer.clear()
        countState.clear()
        isSentBlackList.clear()
      }
    }
  }

}

// 输入的广告点击样例类
case class AdClickEvent(userId: Long, adId: Long, province: String, city: String, timestamp: Long)

case class CountByProvince(windowEnd: String, province: String, count: Long)

// 输出的黑名单的报警信息
case class BlackListWarning(userId: Long, adId: Long, msg: String)

class AdCountAgg() extends AggregateFunction[AdClickEvent, Long, Long] {
  override def createAccumulator(): Long = 0

  override def add(value: AdClickEvent, accumulator: Long): Long = accumulator + 1

  override def getResult(accumulator: Long): Long = accumulator

  override def merge(a: Long, b: Long): Long = a + b
}

class AdCountResult() extends WindowFunction[Long, CountByProvince, String, TimeWindow] {
  override def apply(key: String,
                     window: TimeWindow,
                     input: Iterable[Long],
                     out: Collector[CountByProvince]): Unit = {
    out.collect(CountByProvince(new Timestamp(window.getEnd).toString, key, input.iterator.next()))
  }
}