package com.interview.algorithm.learning.a06_search;

import java.util.Arrays;

/**
 @author yulshi
 @create 2020/02/27 17:37
 */
public class FibonacciSearch {

  public static void main(String[] args) {

    int[] arr = {1, 8, 10, 89, 1000, 1234};
    System.out.println(find(arr, 10));

  }

  // 非递归的方式得到一个斐波那契数列
  private static int[] fib(int maxSize) {
    int[] f = new int[maxSize];
    f[0] = f[1] = 1;
    for (int i = 2; i < maxSize; i++) {
      f[i] = f[i - 1] + f[i - 2];
    }
    return f;
  }

  /**
   * 使用非递归的方式实现斐波那契查找算法
   * mid = low + fib(k-1) - 1
   *
   * @param arr
   * @param key
   */
  private static int find(int[] arr, int key) {
    int low = 0;
    int high = arr.length - 1;
    int k = 0;    // 斐波那契分割数值的下标
    int mid = 0;
    int f[] = fib(arr.length * 2); // 获取斐波那契数列
    // 获取斐波那契分割数值的下标
    while (f[k] - 1 < high) {
      k++;
    }
    // 因为f[k]的值可能大于arr的长度，因此需要一个新的数组
    int[] temp = Arrays.copyOf(arr, f[k]);
    // 使用arr[high]的值填充temp
    for (int i = high + 1; i < temp.length; i++) {
      temp[i] = arr[high];
    }
    // 开始查找...
    while (low <= high) {
      mid = low + f[k - 1] - 1;
      if (key < temp[mid]) {
        high = mid - 1; //
        k--; // 左边部分的新的分割点的索引
      } else if (key > temp[mid]) {
        low = mid + 1;
        k -= 2; // 右边部分的新的分割点的索引
      } else {
        return mid <= high ? mid : high;
      }
    }
    return -1;
  }

}
