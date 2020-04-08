package com.example.networkflow

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.{AllWindowFunction, ProcessWindowFunction}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.triggers.{Trigger, TriggerResult}
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector
import redis.clients.jedis.Jedis

/**
 * @author yulshi
 * @create 2020/04/05 18:30
 */
// 布隆过滤器
object UvWithBloom {
  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    // 用相对路径定义数据源
    val resource = getClass.getResource("/user-behavior.csv")

    val dataStream = env.readTextFile(resource.getPath)
      .map(line => {
        val fields = line.split(",")
        UserBehavior(
          fields(0).trim.toLong,
          fields(1).trim.toLong,
          fields(2).trim.toInt,
          fields(3).trim,
          fields(4).trim.toLong)
      })
      .assignAscendingTimestamps(_.timestamp * 1000)
      .filter(_.behavior == "pv")
      .map(data => ("uv", data.userId))
      .keyBy(_._1)
      .timeWindow(Time.hours(1))
      .trigger(new MyTrigger())
      .process(new UvCountWithBloom())

    dataStream.print()

    env.execute("UV job")
  }
}

case class UvCount(windowEnd: Long, count: Long)

class UvCountByWindow() extends AllWindowFunction[UserBehavior, UvCount, TimeWindow] {
  override def apply(window: TimeWindow, input: Iterable[UserBehavior], out: Collector[UvCount]): Unit = {
    // 定义一个Scala set，由于去重
    var idSet = Set[Long]()
    // 把当前窗口所有数据的id收集到set中
    for (userBehavior <- input) {
      idSet += userBehavior.userId
    }
    out.collect(UvCount(window.getEnd, idSet.size))
  }
}

// 自定义窗口触发器
class MyTrigger() extends Trigger[(String, Long), TimeWindow] {
  override def onElement(element: (String, Long), timestamp: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = TriggerResult.CONTINUE

  override def onProcessingTime(time: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = TriggerResult.CONTINUE

  override def onEventTime(time: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = {
    // 每来一条数据，就直接触发窗口操作，并清空所有窗口状态
    TriggerResult.FIRE_AND_PURGE
  }

  override def clear(window: TimeWindow, ctx: Trigger.TriggerContext): Unit = {}
}

// 定义一个布隆过滤器
class Bloom(size: Long) extends Serializable {
  // 位图的总大小
  private val cap = if (size > 0) size else 1 << 27

  // 定义hash函数
  def hash(value: String, seed: Int): Long = {
    var result = 0L
    for (i <- 0 until value.length) {
      result = result * seed + value.charAt(i)
    }
    result & (cap - 1)
  }
}

class UvCountWithBloom() extends ProcessWindowFunction[(String, Long), UvCount, String, TimeWindow] {

  // 定义Jedis连接
  lazy val jedis = new Jedis("localhost", 6379)
  lazy val bloom = new Bloom(1 << 29)

  override def process(key: String,
                       context: Context,
                       elements: Iterable[(String, Long)],
                       out: Collector[UvCount]): Unit = {

    // 位图的存储方式，每个窗口都应该有一个位图
    // 所以key是windowEnd，value是bitmap

    val storeKey = context.window.getEnd.toString
    var count = 0L

    // 把每个窗口的UV count也存入redis
    val countAsString = jedis.hget("uv-count", storeKey)
    if (countAsString != null) {
      count = countAsString.toLong
    }

    val offset = bloom.hash(elements.last._2.toString, 61)
    // 检查并存入位图
    val exist = jedis.getbit(storeKey, offset)
    if (!exist) {
      jedis.setbit(storeKey, offset, true)
      jedis.hset("uv-count", storeKey, (count + 1).toString)
    }

  }
}