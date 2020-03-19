package com.example.algorithm

/**
 *
 * Find out the index of the a sub string inside of a specified string
 *
 * @author yulshi
 * @create 2020/02/22 12:35
 */
object SubStringDemo {

  def main(args: Array[String]): Unit = {

    val text = "I'm JimJimy, Jimy welcome"
    val str = "Jimy"

    println(substrWithForce(text, str))

  }

  /**
   * This way is simple but not efficient
   *
   * @param text
   * @param sub
   * @return
   */
  def substrWithForce(text: String, sub: String): Int = {

    import util.control.Breaks._

    var i = 0
    var firstMatchIndex = -1

    while (i < text.length) {
      var fullyMatched = true;
      breakable {
        for (j <- 0 until (sub.length)) {
          // found the index that matches the first charactor of sub
          if (text.charAt(i) == sub.charAt(j)) {
            if (j == 0) {
              firstMatchIndex = i
            }
            i += 1
          } else {
            fullyMatched = false
            if(j == 0) { // If the first Char in sub dose not match a Char from text
              i += 1     // i is incremented by 1
            } else {     // otherwise, let i be back to the next position where firstMatchIndex was
              i = firstMatchIndex + 1
            }
            break()
          }
        }
        if (fullyMatched) {
          return firstMatchIndex
        }
      }
    }
    return -1
  }

}
