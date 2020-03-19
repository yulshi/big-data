package com.interview.algorithm.learning.a05_sort;

import java.util.Arrays;
import java.util.Random;

/**
 @author yulshi
 @create 2020/02/26 22:27
 */
public class MergeSorting {

  public static void main(String[] args) {

//        int[] arr = {3, 6, 8, 2, 1, 9, 20, 34};
    int[] arr = new int[8000000];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = new Random().nextInt(8000000);
    }
    int[] temp = new int[arr.length];

    long start = System.currentTimeMillis();
    mergeSorting(arr, 0, arr.length - 1, temp);

    long end = System.currentTimeMillis();
    long diff = (end - start);
    System.out.println("time spent: " + diff + " ms");

//        System.out.println(Arrays.toString(arr));

  }

  private static void mergeSorting(int[] arr, int left, int right, int[] temp) {
    if (left < right) {
      // 获取中间位置的索引
      int mid = (left + right) / 2;
      // 向左递归
      mergeSorting(arr, left, mid, temp);
      // 向右递归
      mergeSorting(arr, mid + 1, right, temp);
      // 合并
      merge(arr, left, mid, right, temp);
    }

  }

  private static void merge(int[] arr, int left, int mid, int right, int[] temp) {

    int i = left;     // 左边有序列表的初始索引
    int j = mid + 1;  // 右边有序序列的初始索引
    int t = 0;        // 临时数组的初始索引

    // 第一步：把左右两部分的有序序列依次比较，把小的放入temp数组
    while (i <= mid && j <= right) {
      if (arr[i] <= arr[j]) {
        temp[t] = arr[i];
        t++;
        i++;
      } else {
        temp[t] = arr[j];
        j++;
        t++;
      }
    }

    // 第二步：把左右两部分中剩余的元素拷贝到temp数组
    while (i <= mid) {
      temp[t] = arr[i];
      t++;
      i++;
    }
    while (j <= right) {
      temp[t] = arr[j];
      t++;
      j++;
    }

    // 第三步：把temp数组中的数据拷回到arr中
    t = 0;
    for (int m = left; m <= right; m++) {
      arr[m] = temp[t++];
    }

  }
}
