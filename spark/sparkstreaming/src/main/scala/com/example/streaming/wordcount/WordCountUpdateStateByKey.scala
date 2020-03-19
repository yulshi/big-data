package com.example.streaming.wordcount

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author yulshi
 * @create 2020/03/13 19:00
 */
object WordCountUpdateStateByKey {

  def main(args: Array[String]): Unit = {

    // 初始化StreamingContext
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val ssc = new StreamingContext(sparkConf, Seconds(3))

    ssc.sparkContext.setCheckpointDir("./ck")

    // 创建DStream对象
    val lineDStream = ssc.socketTextStream("localhost", 9999)

    // 执行计算
    val wordDStream = lineDStream.flatMap(_.split(" "))
    val wordAndOne = wordDStream.map((_, 1))

    val wordAndCount = wordAndOne.updateStateByKey(
      (values: Seq[Int], status: Option[Int]) => {
        val lastStatus = status.getOrElse(0)
        Some(values.sum + lastStatus)
      })

    // 打印输出
    wordAndCount.print

    // 开始流处理并等待
    ssc.start()
    ssc.awaitTermination()

  }

}
