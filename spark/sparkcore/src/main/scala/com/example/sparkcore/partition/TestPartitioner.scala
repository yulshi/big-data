package com.example.sparkcore.partition

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author yulshi
 * @create 2020/03/11 21:28
 */
object TestPartitioner {

  var conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("TestPartitioner")

  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(conf)

    val words = sc.parallelize(Array("aa", "bb", "cc", "dd"), 2)

    val wordAndOne = words.map((_, 1))

    val wordWithIndex = wordAndOne.mapPartitionsWithIndex((index,items)=>items.map((index, _)))

    wordWithIndex.foreach(println)

    val partitioned = wordAndOne.partitionBy(new MyPartitioner(3))
    val partitionedWithIndex = partitioned.mapPartitionsWithIndex((index, items) => items.map((index, _)))

    partitionedWithIndex.foreach(println)

    sc.stop()

  }

}
