package com.interview.algorithm.learning.a05_sort;

import java.util.Arrays;

/**
 @author yulshi
 @create 2020/02/26 08:52
 */
public class SelectSorting {

  public static void main(String[] args) {

    int[] arr = {3, 1, 6, 2, 4, 0, 8, -3};
    select(arr);

    System.out.println(Arrays.toString(arr));

  }

  /**
   * 选择排序：从待排序的数组中，按照指定的规则选出某一个元素，再让其他元素与之比较交换
   *
   * 思路：
   * 第一次，从arr[1]到arr[n-1]中选取一个最小值，与arr[0]比较交换
   * 第二次，从arr[2]到arr[n-1]中选取一个最小值，与arr[1]比较交换
   * 。。。
   * 第n-1次，让arr[n-1]与arr[n-2]比较交换
   *
   * @param arr
   */
  private static void select(int[] arr) {

    // 外层循环用于指定排序的次数
    for (int i = 0; i < arr.length -1; i++) {
      // 内层循环用于与i所在的元素进行比较交换
      for (int j = i + 1; j < arr.length; j++) {
        if (arr[i] > arr[j]) {
          int temp = arr[i];
          arr[i] = arr[j];
          arr[j] = temp;
        }
      }
      System.out.println("第" + (i + 1) + "趟排序的结果：" + Arrays.toString(arr));
    }

  }
}
