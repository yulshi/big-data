package com.interview.algorithm.learning.a04_recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 @author yulshi
 @create 2020/02/25 08:18
 */
public class RecursionTest {

  public static void main(String[] args) {

    RecursionTest arrange = new RecursionTest();
    List<Character> data = new ArrayList<Character>();
    data.add('a');
    data.add('b');
    data.add('c');
    data.add('d');

    //输出A(n,n)的全排列
    for (int i = 1; i <= data.size(); i++)
      arrange.arrangeSelect(data, new ArrayList<Character>(), i);
  }

  /**
   * 计算A(n,k)
   *
   * @param data
   * @param target
   * @param k
   */
  public <E> void arrangeSelect(List<E> data, List<E> target, int k) {
    List<E> copyData;
    List<E> copyTarget;
    if (target.size() == k) {
      for (E i : target)
        System.out.print(i);
      System.out.println();
    }

    for (int i = 0; i < data.size(); i++) {
      copyData = new ArrayList<E>(data);
      copyTarget = new ArrayList<E>(target);

      copyTarget.add(copyData.get(i));
      copyData.remove(i);

      arrangeSelect(copyData, copyTarget, k);
    }
  }


  /**
   * You have serveral identical balls that you wish to place in serveral baskets.
   * Each basket has the same maximum capacity. You are given an int baskets, the
   * number of baskets you have. You are given an int capacity, the maximum capacity
   * of each basket. Finally you are given an int balls, the number of balls to sort
   * into baskets. Return the number of ways you can divide the balls into baskets.
   * If this cannot be done without exceeding the capacity of the baskets, return 0.
   *
   * Each basket is distinct, but all balls are identical. Thus, if you have two balls
   * to place into two baskets, you could have (0, 2), (1, 1), (2, 0), so there would
   * be three ways to do this.
   *
   * @param baskets
   * @param capacity
   * @param balls
   * @return
   */
  private static int countWays(int baskets, int capacity, int balls) {

    return 0;

  }
}
