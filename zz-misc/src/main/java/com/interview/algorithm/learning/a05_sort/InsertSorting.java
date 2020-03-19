package com.interview.algorithm.learning.a05_sort;

import java.util.Arrays;

/**
 @author yulshi
 @create 2020/02/26 10:17
 */
public class InsertSorting {

  public static void main(String[] args) {

    int[] arr = {101, 34, 119, 1, -1, 89};

    insert(arr);
    System.out.println(Arrays.toString(arr));

  }

  private static void insert(int[] arr) {

    for (int i = 1; i < arr.length; i++) {

      int insertValue = arr[i]; // 待插入的元素
      int insertIndex = i - 1;  // 尝试插入的第一个索引
      while (insertIndex >= 0 && arr[insertIndex] > insertValue) {
        arr[insertIndex + 1] = arr[insertIndex]; // 让待插入位置的元素后移一位
        insertIndex--;
      }
      arr[insertIndex + 1] = insertValue;  // 插入待插入的元素到指定的位置

    }

  }
}
