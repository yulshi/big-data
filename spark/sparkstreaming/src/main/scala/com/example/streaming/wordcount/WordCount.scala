package com.example.streaming.wordcount

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}

/**
 * @author yulshi
 * @create 2020/03/13 19:00
 */
object WordCount {

  def main(args: Array[String]): Unit = {

    // 初始化StreamingContext
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val ssc = new StreamingContext(sparkConf, Seconds(3))

    // 创建DStream对象
    val lineDStream = ssc.socketTextStream("localhost", 9999)

    // 执行计算
    val wordDStream = lineDStream.flatMap(_.split(" "))
    val wordAndOne = wordDStream.map((_, 1))
    val wordAndCount = wordAndOne.reduceByKey(_ + _)

    // 打印输出
    wordAndCount.print

    // 开始流处理并等待
    ssc.start()
    ssc.awaitTermination()

  }

}
