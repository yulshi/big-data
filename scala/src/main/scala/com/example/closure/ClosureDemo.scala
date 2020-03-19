package com.example.closure

/**
 * @author yulshi
 * @create 2020/02/21 22:04
 */
object ClosureDemo {

  def main(args: Array[String]): Unit = {

    val a = multiply(10)(4)
    println(a)



  }

  def makeSuffix(suffix: String) = {
    // 这个匿名函数+suffix共同构成一个整体，我们称之为闭包（closure）
    (name: String) => {
      if (name endsWith suffix) {
        name
      } else {
        name + suffix
      }
    }
  }

  def multiply(x: Int)(y:Int) = {
    x * y
  }

}
