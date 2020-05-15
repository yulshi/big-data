package com.interview.algorithm.learning.b01_hanoitower;

/**
 @author yulshi
 @create 2020/03/02 22:48
 */
public class HanoiTowerDemo {

  public static void main(String[] args) {
    hanoiTower(3, 'A', 'B', 'C');
    System.out.println("需要的步数：" + counter);
  }

  private static int counter = 0;
  /**
   * 把a上面的num个盘子移动到c，中间会借用到b
   * 这些盘子要保证小的在上面，大的在下面
   *
   * @param num the number of plates
   * @param a the tower A
   * @param b the tower B
   * @param c the tower c
   */
  public static void hanoiTower(int num, char a, char b, char c) {

    if (num == 1) {
      // 如果只有一个盘，直接将其从a移动到c
      counter ++;
      System.out.println("第" + num + "个盘从 " + a + " => " + c);
    } else {
      // 如果有两个或两个以上的盘子
      // 第一步：先从a盘把最后一个盘子以上的所有盘子从a移动到b
      hanoiTower(num - 1, a, c, b);
      // 第二步：把最后一个盘子从a移动到c
      counter ++;
      System.out.println("第" + num + "个盘从 " + a + " => " + c);
      // 第三步：把b上的所有盘子移动到c
      hanoiTower(num - 1, b, a, c);
    }

  }

}
