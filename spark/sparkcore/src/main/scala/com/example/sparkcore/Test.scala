package com.example.sparkcore

import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author yulshi
 * @create 2020/03/11 15:44
 */
object Test {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[*]").setAppName("Practice")
    val sc = new SparkContext(conf)

    val z = sc.parallelize(List("12", "23", "345", "4567"), 2)
    z.aggregate("1")((x, y) => Math.max(x.length, y.length).toString, (x, y) => x + y)
    z.aggregate("")((x, y) => Math.min(x.length, y.length).toString, (x, y) => x + y)

    val accumulator = sc.longAccumulator

    accumulator.value


  }

  class CustomizedAccumlator extends AccumulatorV2[Int, Int] {
    var sum = 0
    override def isZero: Boolean = sum == 0

    override def copy(): AccumulatorV2[Int, Int] = {
      val copied = new CustomizedAccumlator
      copied.sum = this.sum
      copied
    }

    override def reset(): Unit = sum = 0

    override def add(v: Int): Unit = sum += v

    override def merge(other: AccumulatorV2[Int, Int]): Unit = {
      sum += other.value
    }

    override def value: Int = sum
  }
}

case class People(name: String, age: Int);


