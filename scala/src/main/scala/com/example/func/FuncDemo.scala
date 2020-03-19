package com.example.func

/**
 * @author yulshi
 * @create 2020/02/21 16:11
 */
object FuncDemo {

  def main(args: Array[String]): Unit = {

    val list = List(1, 2, 3, 5, "abc")

    val addOne = new PartialFunction[Any, Int] {
      override def isDefinedAt(x: Any): Boolean = {
        if (x.isInstanceOf[Int]) true else false
      }

      override def apply(v1: Any): Int = {
        v1.asInstanceOf[Int] + 1
      }
    }

    // val res = list.collect(addOne)
    // 简化方式，一个case语句就是一个偏函数
    val res = list.collect { case x: Int => x + 1 }

    println("res = " + res)


  }

  def f(list: List[Any]) = {
    list.filter(c => c.isInstanceOf[Int]).map(c => c.asInstanceOf[Int]).map(i => i + 1)
  }

}
