package com.interview.algorithm.learning.a05_sort;

import java.util.Arrays;

/**
 @author yulshi
 @create 2020/02/29 09:46
 */
public class HeapSorting {

  public static void main(String[] args) {
    int[] arr = {4, 6, 8, 5, 9, 20, 0, 2, 59, 1};
    heapSort(arr);
    System.out.println(Arrays.toString(arr));
  }

  /**
   * 使用大顶堆进行升序排序
   *
   * @param arr
   */
  public static void heapSort(int[] arr) {

    int temp = 0; // 交换的时候使用
    // 把数组调整成大顶堆
    for (int i = arr.length / 2 - 1; i >= 0; i--) {
      toMaxHeap(arr, i, arr.length);
    }
    // 依次找出堆顶的元素并将之放入数组的末尾
    for (int j = arr.length - 1; j > 0; j--) {
      temp = arr[0];
      arr[0] = arr[j];
      arr[j] = temp;
      toMaxHeap(arr, 0, j);
    }
  }

  /**
   * 把指定的数组在第i个位置调整成一个大顶堆
   *
   * @param arr 给定的数组
   * @param i 调整大顶堆的当前索引
   * @param len 需要调整的数据长度
   */
  public static void toMaxHeap(int[] arr, int i, int len) {

    int temp = arr[i]; // 记录下当前位置的值，以待交换

    // k是当前节点的第一个左子节点
    // 之所以使用循环，是因为在当前子节点下面可能还有左子树，
    // 虽然这个左子树已经被调整成大顶堆，但是这个子树可能所有节点的值
    // 都大于当前节点的值，这种情况下，需要把当前节点的值一直下沉
    for (int k = i * 2 + 1; k < len; k = k * 2 + 1) {
      // 让k指向左右子节点中值大的那个索引
      if (k + 1 < len && arr[k] < arr[k + 1]) {
        k++;
      }

      if (arr[k] > temp) {
        // 如果子节点的值大于当前节点的值，则交换，并把i置成k，以便
        // 下沉当前的值到子树的子树
        arr[i] = arr[k];
        i = k;
      } else {
        // 如果子节点的值小于当前节点的值，则直接退出循环
        // 这是因为子树已经是大顶堆了
        break;
      }

    }
    arr[i] = temp;
  }

}
