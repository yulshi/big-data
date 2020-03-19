package com.example.controlabstract

/**
 * @author yulshi
 * @create 2020/02/22 08:38
 */
object ControlDemo {

  def main(args: Array[String]): Unit = {

    var x = 10
    until(x == 0) {
      x -=1
      println("x = " + x)
    }

  }

  // 控制抽象
  // 利用控制抽象可以实现类似while循环的控制语句，scala里面的breakable就是一个控制抽象
  // 去掉condition：后面的括号，编程抽象控制
  def until(condition: => Boolean)(block: => Unit): Unit = {
    if(!condition) {
      block
      until(condition)(block)
    }
  }

}
