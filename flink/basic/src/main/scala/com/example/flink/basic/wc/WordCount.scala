package com.example.flink.basic.wc

import org.apache.flink.api.scala._
import org.apache.flink.api.scala.ExecutionEnvironment

/**
 * 批处理word count
 */
object WordCount {

  def main(args: Array[String]): Unit = {

    // 创建一个执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 从文件中取数据
    val inputPath = "/Users/yushi/work/bigdata/flink/basic/src/main/resources/hello.txt"

    val inputData = env.readTextFile(inputPath)

    // 切分数据，得到word，转换成一个二元组
    val wordCountDataSet = inputData
      .flatMap(_.split(" "))
      .map((_, 1))
      .groupBy(0)
      .sum(1)

    wordCountDataSet.print()

  }

}
