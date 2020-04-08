package com.example.networkflow

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.AllWindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
 * @author yulshi
 * @create 2020/04/05 16:50
 */
object UniqueVisitor {

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
      .timeWindowAll(Time.hours(1))
      .apply(new UvCountByWindow())

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