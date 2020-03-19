package com.example

/**
 * @author yulshi
 * @create 2020/02/17 12:29
 */

import scala.language.postfixOps
import scala.collection.mutable.Set

object FuncTest {

  def main(args: Array[String]): Unit = {
    val arr = Array(1, 7, 0, 2, 5, 5, 5,5)
    bubbleSort(arr)
    arr.foreach(e => {
      println(e)
    })
    println("----------------")
    var result = Set[Int]()
    println(binarySearch(arr, 5, 0, arr.length - 1))
    println(result)
  }

  @throws()
  def sum(arg1: Int, args: Int*): Int = {
    var sum = arg1
    for (item <- args) {
      sum += item
    }
    sum
  }

  /**
   *
   * f(n) = f(n-1) + f(n-2)
   *
   * f(1) = 1
   * f(2) = 1
   * f(3) = f(1) + f(2) = 2
   * f(4) = f(2) + f(3) = 3
   * f(5) = f(3) + f(4) = 5
   * f(6) = (f4) + f(5) = 8
   * f(7) = f(5) + f(6) = 13
   * f(8) = f(6) + f(7) = 21
   *
   * @param n
   * @return
   */
  def fab(n: Int): Int = {

    var res = 0;

    if (n == 1 || n == 2) {
      res = 1
    } else {
      res = fab(n - 2) + fab(n - 1)
    }

    return res
  }

  def multiplyChart(n: Int): Unit = {

    for (i <- 1 to n) {
      for (j <- 1 to i) {
        printf("%d x %d = %d\t", j, i, j * i);
      }
      println()
    }
  }

  def pyramid(n: Int) = {

    for (i <- 1 to n) {
      print(" " * (n - i))
      for (j <- 1 to i * 2 - 1) {
        print("*")
      }
      print(" " * (n - i))
      println()
    }

  }

  def countDown(n: Int) = {
    // Use reverse keyword to count down
    for (i <- 0 to n reverse) {
      printf("%d ", i)
    }
    println()
    // Use Range to count down
    for (j <- Range(n, -1, -1)) {
      printf("%d ", j)
    }
    println()
  }

  def calculateUnicode(str: String): Long = {

    if (str.length == 1) {
      str.charAt(0).toLong
    } else {
      str.take(1).charAt(0).toLong * calculateUnicode(str.drop(1))
    }

  }

  def exponent(x: Double, n: Int): Double = {

    if (n == 0) 1
    else if (n > 0) {
      x * exponent(x, n - 1)
    } else {
      1.0 / exponent(x, -n)
    }

  }

  /**
   * 冒泡排序就是把最大的那个向后面甩
   *
   * @param arr
   * @param ascending
   */
  def bubbleSort(arr: Array[Int], ascending: Boolean = true): Unit = {

    import scala.util.control.Breaks._

    breakable {
      for (i <- 0 until arr.length - 1) { // 外层循环负责遍历

        // 如果内层循环一次交换都没有，则直接退出
        var swapHapped = false
        for (j <- 0 until (arr.length - 1 - i)) { // 内层循环负责交换
          if ((ascending && arr(j) > arr(j + 1))
            || !ascending && arr(j) < arr(j + 1)) {
            swapHapped = true
            val temp = arr(j)
            arr(j) = arr(j + 1)
            arr(j + 1) = temp
          }
        }

        if (!swapHapped) {
          break()
        }
      }
    }

  }

  /**
   * 思想
   * 两个循环，每次内部循环都找出一个最小值
   *
   * @param arr
   */
  def selectSort(arr: Array[Int]): Unit = {

    for (i <- 0 until arr.length - 1) {
      for (j <- i + 1 until arr.length) {
        if (arr(j) < arr(i)) {
          val temp = arr(i)
          arr(i) = arr(j)
          arr(j) = temp
        }
      }
    }

  }



  /**
   * Found out all of the occurrences of the specified value
   *
   * @param arr
   * @param value
   * @param leftIndex
   * @param rightIndex
   * @return the array of the index
   */
  def binarySearch(arr: Array[Int], value: Int, leftIndex: Int, rightIndex: Int, result: Set[Int]): Unit = {

    if(leftIndex > rightIndex) {
      return
    }

    var midIndex = (leftIndex + rightIndex) / 2
    if( value < arr(midIndex)) {
      binarySearch(arr, value, leftIndex, midIndex - 1, result)
    } else if(value > arr(midIndex)) {
      binarySearch(arr, value, midIndex + 1, rightIndex, result)
    } else {
      binarySearch(arr, value, leftIndex, midIndex - 1, result)
      binarySearch(arr, value, midIndex + 1, rightIndex, result)
      result.add(midIndex)
    }

  }

  /**
   * Found out the occurrence of the specified value
   * 只找到一个就返回
   *
   * @param arr
   * @param value
   * @param leftIndex
   * @param rightIndex
   * @return the array of the index
   */
  def binarySearch(arr: Array[Int], value: Int, leftIndex: Int, rightIndex: Int): Int = {

    if(leftIndex > rightIndex) {
      return Int.MinValue
    }

    var midIndex = (leftIndex + rightIndex) / 2
    if( value < arr(midIndex)) {
      binarySearch(arr, value, leftIndex, midIndex - 1)
    } else if(value > arr(midIndex)) {
      binarySearch(arr, value, midIndex + 1, rightIndex)
    } else {
      return midIndex
    }

  }

}
