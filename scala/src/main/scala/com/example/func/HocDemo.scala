package com.example.func

import com.example.pkg.DB

/**
 * @author yulshi
 * @create 2020/02/21 17:46
 */
object HocDemo {

  def main(args: Array[String]): Unit = {

    //    val list = List(Some(1), Some(2), Some(4), Some(7), None, Some(9), None)
    //    println(list.collect { case Some(x) => x }.reduce(_ + _))
    //
    //    println(swap(List(1, 2, 4, 5, 5)))

    println(largest(x => 10 * x, List(2, 4, 2, 6, 3, 6)))

  }

  def largest(func: (Int)=>Int, inputs: Seq[Int]): Int = {

    inputs.foldLeft[Int](1)((stage, next) => {
      val temp = func(next)
      if(temp > stage) temp else stage
    })

  }

  def max(arr: Array[Int]): Int = {
    //    arr.reduce((stage, next) => {
    //      if(stage > next) stage else next
    //    })
    arr.reduce(math.max(_, _))
  }

  def factorial(n: Int): Int = {
    1 to n reduce (_ * _)
  }

  def swap(list: List[Int]) = {
    list match {
      case List(x, y, rest@_*) => List(y, x) ++ rest
      case _ => list
    }
  }

  def values(fun: (Int) => Int, low: Int, high: Int): List[(Int, Int)] = {
    val seq = for (i <- low to high) yield (i, fun(i))
    seq.toList
  }

  def compose(f1: Double => Option[Double], f2: Double => Option[Double]): Double => Option[Double] = {

    x: Double => {
      val res1 = f1(x)
      val res2 = f2(x)
      if (res1 == None || res2 == None) {
        None
      } else {
        Some(res1.get + res2.get)
      }
    }

  }

  def f(x: Double): Option[Double] = if (x > 0) Some(math.sqrt(x)) else None

  def g(x: Double): Option[Double] = if (x != 1) Some(1 / (x - 1)) else None

}
