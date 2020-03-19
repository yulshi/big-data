package com.interview.algorithm.learning.b03_kmp;

import java.util.Arrays;

/**
 @author yulshi
 @create 2020/03/03 14:25
 */
public class StringMatch {

  public static void main(String[] args) {

    String text = "I am Jimm, welcome Jimmy";
    String subStr = "Jimmy";

    System.out.println(forceMatch(text, subStr));
    System.out.println(kmpMatch(text, subStr));

  }

  /**
   * 使用暴力方法在text中匹配subStr
   * 思路：
   * 1）假设两个索引i，j。其中i指向text中的字符，j指向subStr中的字符
   * 2）如果当前字符匹配成功（即text[i]==subStr[j]），则i++，j++，继续匹配下一个字符
   * 3）如果匹配不成功，则令 i = i - (j -1), j = 0
   * @param text
   * @param subStr
   * @return
   */
  public static int forceMatch(String text, String subStr) {

    int i = 0, j = 0;
    while (i < text.length() && j < subStr.length()) {
      if (text.charAt(i) == subStr.charAt(j)) {
        i++;
        j++;
      } else {
        i = i - j + 1; // 让i回溯到第匹配subStr的第一个字符的下一个地方
        j = 0;
      }
    }
    // 如果subStr已经全部被遍历过了，那么就说明已经找到匹配了
    if (j == subStr.length()) {
      return i - j;
    } else {
      return -1;
    }

  }

  public static int kmpMatch(String text, String subStr) {

    int[] next = kmpNext(subStr);

    for (int i = 0, j = 0; i < text.length(); i++) {
      // 根据部分匹配表调整j的大小
      while (j > 0 && text.charAt(i) != subStr.charAt(j)) {
        j = next[j - 1];
      }

      if (text.charAt(i) == subStr.charAt(j)) {
        j++;
      }
      // 如果subStr遍历完所有的字符，表示匹配找到了
      if (j == subStr.length()) {
        return i - j + 1;
      }
    }
    return -1;
  }

  /**
   * 计算给定字符春的部分匹配表
   *
   * @param dest
   * @return
   */
  public static int[] kmpNext(String dest) {
    // 创建一个next数组，保存部分匹配表
    int[] next = new int[dest.length()];
    next[0] = 0; // 如果字符串长度为1，则部分匹配值就是0
    for (int i = 1, j = 0; i < dest.length(); i++) {
      while (j > 0 && dest.charAt(i) != dest.charAt(j)) {
        j = next[j - 1];
      }
      if (dest.charAt(i) == dest.charAt(j)) {
        j++;
      }
      next[i] = j;
    }
    return next;
  }

}
