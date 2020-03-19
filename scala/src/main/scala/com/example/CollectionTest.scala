package com.example

import java.util

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * @author yulshi
 * @create 2020/02/19 13:59
 */
object CollectionTest {

  def main(args: Array[String]): Unit = {

    import scala.collection.mutable

    //    for(amt <- Array(Doller(10), Currency(40, '#'), NoAmount, Square)) {
    //      val res = amt match {
    //        case Doller(a) => "$" + a
    //        case Currency(a, u) => u + "" + a
    //        case NoAmount => ""
    //        case _ => "Nothing"
    //      }
    //      println("res=" + res)
    //    }

  }


  abstract class Amount

  case class Doller(amt: Double) extends Amount

  case class Currency(amt: Double, unit: Char) extends Amount

  case class NoAmount()

  object Square {

    def unapply(arg: Double): Option[Double] = Some(math.sqrt(arg))

    def apply(d: Double) = {
      d * d
    }
  }

  object Email {
    def apply(name: String, postfix: String) = {
      name + "@" + postfix
    }

    def unapply(emailAddr: String): Option[(String, String)] = {
      println("emailAddr=" + emailAddr)
      val fields = emailAddr split "@"
      if (fields.length > 1) {
        Some((fields(0), fields(1)))
      } else {
        None
      }
    }
  }

  object Names {
    def apply(seq: String*) = {
      val sb = new StringBuilder()
      for (str <- seq) {
        sb.append(str).append(",")
      }
      sb.toString().substring(0, sb.length() - 1)
    }

    def unapplySeq(names: String): Option[Seq[String]] = {
      if (names.contains(",")) {
        Some(names split ",")
      } else {
        None
      }
    }
  }

  class Pair[T, S](val t: T, val s: S) {
    def swap(implicit env: T =:= S) = {
      println("env=" + env)
      new Pair(s, t)
    }
  }


  def swapNext(arr: Array[Int]): Unit = {
    var temp: Int = 0
    for (i <- 0 until(arr.length, 2)) {
      if (i < arr.length - 1) {
        temp = arr(i)
        arr(i) = arr(i + 1)
        arr(i + 1) = temp
      }
    }
  }

  def minMax(arr: Array[Int]) = {
    (arr.min, arr.max)
  }

}

