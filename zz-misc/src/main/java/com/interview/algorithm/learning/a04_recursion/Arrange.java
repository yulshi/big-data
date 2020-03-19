package com.interview.algorithm.learning.a04_recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * 排列
 *
 @author yulshi
 @create 2020/02/25 16:21
 */
public class Arrange {
  public static void main(String[] args) {
    Arrange arrange = new Arrange();
    List<Character> data = new ArrayList<Character>();
    data.add('a');
    data.add('b');
    data.add('c');
    data.add('d');

//        //输出A(n,n)的全排列
//        for (int i = 1; i <= data.size(); i++)
//            arrange.arrangeSelect(data, new ArrayList<Character>(), 4);

    arrange.arrangeSelect(data, new ArrayList<>(), data.size());

  }

  /**
   * 计算A(n,k)
   *
   * 思路：将元素存在List里（方便删除），遍历List，删除当前元素并把此元素加入结果，
   * 如果结果长度不为0，则输出，以此递归。
   *
   * @param data
   * @param target
   * @param k
   */
  public <E> void arrangeSelect(List<E> data, List<E> target, int k) {
    List<E> copyData;
    List<E> copyTarget;

    if (target.size() == k) {
      System.out.println(target);
    }

    for (int i = 0; i < data.size(); i++) {
      copyData = new ArrayList<E>(data);
      copyTarget = new ArrayList<E>(target);

      copyTarget.add(copyData.get(i));
      copyData.remove(i);

      arrangeSelect(copyData, copyTarget, k);
    }
  }
}
