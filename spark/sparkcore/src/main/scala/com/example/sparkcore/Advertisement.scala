package com.example.sparkcore

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author yulshi
 * @create 2020/03/11 09:56
 */
object Advertisement {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[*]").setAppName("Practice")

    val sc = new SparkContext(conf)

    // 读取数据，创建RDD
    val lines = sc.textFile("/path/to/file")

    // 切分，取出省份+广告 ((province,ad),1)
    val provinceAdAndOne = lines.map(x => {
      val fields = x.split(" ")
      ((fields(1), fields(4)), 1)
    })

    // 统计省份广告被点击的总次数 ((province,ad),16)
    val provinceAdAndCount = provinceAdAndOne.reduceByKey(_ + _)

    // 维度转换 (province,(ad,16))
    val provinceToAdAndCount = provinceAdAndCount.map(x => (x._1._1, (x._1._2, x._2)))

    // 将同一个省份不同广告做聚合 (province1, Iterable((ad1,16),(ad2,12))
    val provinceToAdGroup = provinceToAdAndCount.groupByKey()

    // 排序同时取前三
    val top3 = provinceToAdGroup.mapValues(iter => {
      iter.toList.sortWith((a, b) => a._2 > b._2).take(3)
    })

    // 打印最终结果
    top3.collect().foreach(println)

    // 关闭连接
    sc.stop()

  }

}
