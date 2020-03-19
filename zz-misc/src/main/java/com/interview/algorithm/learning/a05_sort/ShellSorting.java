package com.interview.algorithm.learning.a05_sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 希尔排序也是一种插入排序，是改进之后的一个更高效的版本，也称为缩小增量排序。
 *
 * 思路
 * 希尔排序是把记录按照下标的一定增量分组，对每组使用直接插入排序算法进行排序；
 * 随着增量逐渐减少，每组包含的关键词越来越多，当增量减少至1时，整个数组恰被分成一组，
 * 算法便终止。
 *
 * @author yulshi
 * @create 2020/02/26 12:00
 */
public class ShellSorting {

  public static void main(String[] args) {

//        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};

    int[] arr = new int[8000000];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = new Random().nextInt(800000);
    }

    long start = System.currentTimeMillis();

//        shellSwap(arr);
    shellMove(arr);

    long end = System.currentTimeMillis();
    long diff = (end - start);
    System.out.println("time spent: " + diff + " ms");
//        System.out.println(Arrays.toString(arr));

  }

  /**
   * 希尔排序也是一种插入排序，是改进之后的一个更高效的版本，也称为缩小增量排序。
   *
   * 思路
   * 希尔排序是把记录按照下标的一定增量分组，对每组使用直接插入排序算法进行排序；
   * 随着增量逐渐减少，每组包含的关键词越来越多，当增量减少至1时，整个数组恰被分成一组，
   * 算法便终止。
   *
   * 交换法
   *
   * @param arr
   */
  private static void shellSwap(int[] arr) {

    int temp = 0;
    // 分组：每次在前一次分组的基础上，除以2形成新的分组，也称为步长
    for (int gap = arr.length / 2; gap > 0; gap /= 2) {
      // 比较每组的元素
      for (int i = gap; i < arr.length; i++) {
        // 交换
        for (int j = i - gap; j >= 0; j -= gap) {
          if (arr[j] > arr[j + gap]) {
            temp = arr[j];
            arr[j] = arr[j + gap];
            arr[j + gap] = temp;
          }
        }
      }
    }

  }

  private static void shellMove(int[] arr) {

    // 分组：每次在前一次分组的基础上，除以2形成新的分组，也称为步长
    for (int gap = arr.length / 2; gap > 0; gap /= 2) {
      for (int i = gap; i < arr.length; i++) {
        int insertIndex = i - gap;
        int insertValue = arr[i];
        while (insertIndex >= 0 && arr[insertIndex] > insertValue) {
          arr[insertIndex + gap] = arr[insertIndex];
          insertIndex -= gap;
        }
        arr[insertIndex + gap] = insertValue;
      }
    }

  }
}
