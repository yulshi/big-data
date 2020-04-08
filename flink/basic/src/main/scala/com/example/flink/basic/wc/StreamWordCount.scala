package com.example.flink.basic.wc

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
 * @author yulshi
 * @create 2020/03/31 15:49
 */
object StreamWordCount {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 接收一个socket文本流
    val dataStream = env.socketTextStream("localhost", 7777)

    // 对每条数据进行处理
    val wordCountDataStream = dataStream
      .flatMap(_.split(" "))
      .filter(_.nonEmpty)
      .map((_, 1))
      .keyBy(0)
      .sum(1)

    wordCountDataStream.print()

    // 启动executor
    env.execute("stream word count")

  }

}
