package com.interview.algorithm.learning.a05_sort;

import java.util.Arrays;
import java.util.Random;

/**
 @author yulshi
 @create 2020/02/26 14:45
 */
public class QuickSorting {

  public static void main(String[] args) {

//        int[] arr = {2, 10, 8, 22, 11, 5, 12, 28, 21, 11};
//        System.out.println(Arrays.toString(arr));
    int[] arr = new int[8000000];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = new Random().nextInt(800000);
    }

    long start = System.currentTimeMillis();
    quick(arr, 0, arr.length - 1);

    long end = System.currentTimeMillis();
    long diff = (end - start);
    System.out.println("time spent: " + diff + " ms");

//        System.out.println(Arrays.toString(arr));
  }

  private static void quick(int[] arr, int left, int right) {

    int l = left;
    int r = right;
    int pivot = arr[(l + r) / 2]; // 找到一个基准值

    // 把比基准值小的放在左边，比基准值大的放在右边
    while (l < r) {
      // 从左边找到一个比基准值大的位置
      while (arr[l] < pivot) {
        l++;
      }
      // 从右边找到一个比基准值小的位置
      while (arr[r] > pivot) {
        r--;
      }
      // 如果左边的索引值大于或等于右边的索引值，则退出循环
      if (l >= r) {
        break;
      }
      // 交换左右两边的值
      int temp = arr[l];
      arr[l] = arr[r];
      arr[r] = temp;
      // 如果待排序的列表有重复的值，下面的代码是必须的，否则会抛StackOverflowError
      if (arr[l] == pivot) {
        r--;
      }
      if (arr[r] == pivot) {
        l++;
      }
    }
    // 避免无限递归
    if (l == r) {
      l++;
      r--;
    }
    // 向左递归
    if (left < r) {
      quick(arr, left, r);
    }
    // 向右递归
    if (right > l) {
      quick(arr, l, right);
    }
  }
}
