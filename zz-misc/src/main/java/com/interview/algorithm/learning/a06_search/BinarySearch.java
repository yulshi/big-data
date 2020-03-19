package com.interview.algorithm.learning.a06_search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 @author yulshi
 @create 2020/02/27 13:11
 */
public class BinarySearch {

  public static void main(String[] args) {

    int[] arr = {2, 4, 23, 23, 23, 23, 53, 67, 90, 123};

//    System.out.println(find(23, arr, 0, arr.length - 1));
    System.out.println(find(67, arr));

//    System.out.println(findAll(23, arr, 0, arr.length - 1));

  }

  private static int find(int findVal, int arr[], int left, int right) {

    if (left > right) return -1;

    // 首先确定中间位置的下标
    int mid = (left + right) / 2;
    if (findVal < arr[mid]) {
      // 左递归
      return find(findVal, arr, left, mid - 1);
    } else if (findVal > arr[mid]) {
      // 右递归
      return find(findVal, arr, mid + 1, right);
    } else {
      // 找到了
      return mid;
    }

  }

  private static List<Integer> findAll(int findVal, int[] arr, int left, int right) {

    if (left > right) Collections.emptyList();

    int mid = (left + right) / 2;
    if (findVal < arr[mid]) {
      // 向左递归
      return findAll(findVal, arr, left, mid - 1);
    } else if (findVal > arr[mid]) {
      // 向右递归
      return findAll(findVal, arr, mid + 1, right);
    } else {
      // 找到了第一个，需要分别向左和向右遍历查找是否有其他相同的值
      List<Integer> result = new ArrayList<>();
      result.add(mid);
      // 向左遍历
      int index = mid - 1;
      while (index >= 0 && arr[index] == arr[mid]) {
        result.add(index);
        index--;
      }
      // 向右遍历
      index = mid + 1;
      while (index <= arr.length - 1 && arr[index] == arr[mid]) {
        result.add(index);
        index++;
      }
      return result;
    }
  }

  /**
   * 使用非递归的方式查找指定值在数组中的位置
   */
  public static int find(int findVal, int[] arr) {
    int low = 0;
    int high = arr.length - 1;
    int mid = 0;
    while (low <= high) {
      mid = (low + high) / 2;
      if (findVal < arr[mid]) {
        high = mid - 1;
      } else if (findVal > arr[mid]) {
        low = mid + 1;
      } else {
        return mid;
      }
    }
    return -1;
  }

}


