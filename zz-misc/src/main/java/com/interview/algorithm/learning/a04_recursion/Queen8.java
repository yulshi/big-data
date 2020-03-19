package com.interview.algorithm.learning.a04_recursion;

/**
 @author yulshi
 @create 2020/02/25 17:03
 */
public class Queen8 {

  // 定义一个max表示共有多少个皇后
  int max = 8;

  // 定义数组，保存皇后放置的结果
  int[] array = new int[max];

  public static void main(String[] args) {

    Queen8 queen8 = new Queen8();
    queen8.check(0);

  }

  // 写一个方法，可以将皇后摆放的位置输出
  private void print() {
    for (int i = 0; i < array.length; i++) {
      System.out.print(array[i] + " ");
    }
    System.out.println();
  }

  /**
   * 查看当我们放置第n个皇后，就去检测该皇后是否和前面已经放置的皇后冲突
   * @param n 第n个皇后
   * @return
   */
  private boolean judge(int n) {
    for (int i = 0; i < n; i++) {
      if (array[i] == array[n] // 在同一列
              || Math.abs(n - i) == Math.abs(array[n] - array[i])) // 在同一个斜线上
      {
        return false;
      }
    }
    return true;
  }

  /**
   * 放置第n个皇后
   *
   * @param n
   */
  private void check(int n) {
    if (n == max) { // end
      print();
      return;
    }

    // 依次放置皇后，并检查是否冲突
    for (int i = 0; i < max; i++) {
      // 先把第n个皇后，放到第一列
      array[n] = i;
      if (judge(n)) {
        // 如果不冲突，则接着放置第n+1个皇后，即开始递归
        check(n + 1);
      }
      // 如果冲突就继续执行去尝试下一列
    }
  }
}
