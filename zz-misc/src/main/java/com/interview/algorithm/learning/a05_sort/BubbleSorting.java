package com.interview.algorithm.learning.a05_sort;

import java.util.Arrays;

/**
 @author yulshi
 @create 2020/02/26 08:08
 */
public class BubbleSorting {

  public static void main(String[] args) {

    int[] arr = {3, 1, 6, 2, 4, 0, 8, -3};
    bubbleSorting(arr);

    System.out.println(Arrays.toString(arr));

  }

  private static void bubbleSorting(int[] arr) {
    // 外层循环用户控制排序的次数
    for (int i = 0; i < arr.length - 1; i++) {
      boolean flag = false; // 判断是否一次交换都没有发生过
      // 内层循环用于执行交换
      for (int j = 0; j < arr.length - i - 1; j++) {
        if (arr[j] > arr[j + 1]) {
          flag = true;
          int temp = arr[j];
          arr[j] = arr[j + 1];
          arr[j + 1] = temp;
        }
      }
      System.out.println("第" + (i + 1) + "趟排序的结果：" + Arrays.toString(arr));
      if (!flag) {
        break;
      }
    }

  }

}
