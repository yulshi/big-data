package com.example.help;

import java.util.Arrays;

/**
 @author yulshi
 @create 2020/04/02 13:39
 */
public class Main {

  public static void main(String[] args) {

    int[] arr = {8, 4, 7, 0, 1, 6, 5};

    for (int i = 0; i < arr.length - 1; i++) {
      // 内层循环用于与i所在的元素进行比较交换
      System.out.println(i);
      for (int j = i + 1; j < arr.length; j++) {
        if (arr[i] > arr[j]) {
          int temp = arr[i];
          arr[i] = arr[j];
          arr[j] = temp;
        }
      }
    }

    System.out.println(Arrays.toString(arr));
  }

}

abstract class A {
  public A(String str) {

  }

  abstract void hello();
}

class B {
  public void method(A a) {
    a.hello();
  }
}
