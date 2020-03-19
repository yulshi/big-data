package com.interview.algorithm.learning.a05_sort;

import java.util.Arrays;

/**
 @author yulshi
 @create 2020/02/27 12:07
 */
public class RadixSorting {

  public static void main(String[] args) {

    int[] arr = {53, 3, 542, 748, 14, 214};

    radix(arr);

    System.out.println(Arrays.toString(arr));

  }

  private static void radix(int[] arr) {

    // 定义一个二维数组作为桶
    int[][] buckets = new int[10][arr.length];
    // 定义一个数组，存放每个桶中的当前索引
    int[] bucketPos = new int[buckets.length];

    // 找出数组中最大的值，并取得该值的长度
    int max = -1;
    for (int i = 0; i < arr.length; i++)
      if (max < arr[i]) max = arr[i];
    int maxLen = (max + "").length();

    for (int i = 0; i < maxLen; i++) {
      // 第一步：找出位数对应的数值，并放入桶中
      for (int j = 0; j < arr.length; j++) {
        int radix = arr[j] / ((int) Math.pow(10, i)) % 10;
        buckets[radix][bucketPos[radix]++] = arr[j];
      }
      // 第二步：从桶中取出值放回原数组中
      int index = 0;
      for (int j = 0; j < bucketPos.length; j++) {
        if (bucketPos[j] > 0) {
          // 对于每一个有值的桶，依次从中取出元素放回到原数组中
          for (int k = 0; k < bucketPos[j]; k++) {
            arr[index++] = buckets[j][k];
          }
        }
        // 重置每个桶中的当前索引
        bucketPos[j] = 0;
      }
    }

  }
}
