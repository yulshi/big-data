package com.example.networkflow

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * @author yulshi
 * @create 2020/04/05 16:32
 */
object PageView {

  /**
   * 输出每小时pv的数量，不考虑一个用户多次点击一个页面
   *
   * @param args
   */
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
      .map(data => ("pv", 1))
      .keyBy(_._1)
      .timeWindow(Time.hours(1))
      .sum(1)

    dataStream.print("pv count")

  }

}

// 定义输入数据的样例类
case class UserBehavior(userId: Long, itemId: Long, categoryId: Int, behavior: String, timestamp: Long)

// 定义窗口聚合样例类
case class ItemViewCount(itemId: Long, windowEnd: Long, count: Long)
