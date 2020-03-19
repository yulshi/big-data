package com.interview.algorithm.learning.a06_search;

/**
 @author yulshi
 @create 2020/02/27 16:52
 */
public class InsertValueSearch {

  public static void main(String[] args) {

    int[] arr = new int[100];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = i*2 + 1;
    }

    System.out.println(find(11, arr, 0, arr.length - 1));

  }

  private static int find(int findVal, int[] arr, int left, int right) {
    if (left > right || findVal < arr[0] || findVal > arr[arr.length - 1]) return -1;
    // 自适应的开始查找位置
    int mid = left + (right - left) * (findVal - arr[left]) / (arr[right] - arr[left]);
    // 开始递归查找
    if (findVal < arr[mid]) {
      return find(findVal, arr, left, mid - 1);
    } else if (findVal > arr[mid]) {
      return find(findVal, arr, mid + 1, right);
    } else {
      return mid;
    }
  }

}
