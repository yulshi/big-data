package com.example.sparkcore

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author yulshi
 * @create 2020/03/09 19:42
 */
object WordCount {


  def main(args: Array[String]): Unit = {

    var config = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(config)

    val line = sc.textFile("/opt/modules/input")

    val words = line.flatMap(_.split(" "))

    val wordAndOne = words.map((_, 1))

    val wordAndCount = wordAndOne.reduceByKey(_ + _)

    wordAndCount.saveAsTextFile("/opt/modules/output")

  }

}
