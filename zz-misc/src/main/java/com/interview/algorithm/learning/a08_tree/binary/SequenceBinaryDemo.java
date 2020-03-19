package com.interview.algorithm.learning.a08_tree.binary;

/**
 *
 * 第n个元素的左子节点为 2 * n + 1
 * 第n个元素的右子节点为 2 * n + 2
 * 第n个元素的父节点为   (n - 1) / 2
 *
 @author yulshi
 @create 2020/02/28 16:40
 */
public class SequenceBinaryDemo {

  public static void main(String[] args) {
//    int[] arr = {1, 2, 3, 4, 5, 6, 7};
    int[] arr = {1, 3, 6, 8, 10, 14};

    SequenceBinaryTree sequenceBinaryTree = new SequenceBinaryTree(arr);
    sequenceBinaryTree.infixTraverse();

  }

  private static class SequenceBinaryTree {

    private final int[] arr;

    public SequenceBinaryTree(int[] arr) {
      this.arr = arr;
    }

    /**
     * 前序遍历
     */
    public void prefixTraverse() {
      prefixTraverseInternal(0);
    }

    private void prefixTraverseInternal(int n) {
      System.out.printf("%d ", arr[n]);
      if ((2 * n + 1) < arr.length) {
        prefixTraverseInternal(2 * n + 1);
      }
      if ((2 * n + 2) < arr.length) {
        prefixTraverseInternal(2 * n + 2);
      }
    }

    /**
     * 中序遍历
     */
    public void infixTraverse() {
      infixTraverseInternal(0);
    }

    private void infixTraverseInternal(int n) {

      if ((2 * n + 1) < arr.length) {
        infixTraverseInternal(2 * n + 1);
      }
      System.out.printf("%d ", arr[n]);
      if ((2 * n + 2) < arr.length) {
        infixTraverseInternal(2 * n + 2);
      }
    }

    /**
     * 后序遍历
     */
    public void postfixTraverse() {
      postfixTraverseInternal(0);
    }

    private void postfixTraverseInternal(int n) {
      if ((2 * n + 1) < arr.length) {
        postfixTraverseInternal(2 * n + 1);
      }
      if ((2 * n + 2) < arr.length) {
        postfixTraverseInternal(2 * n + 2);
      }
      System.out.printf("%d ", arr[n]);
    }

  }

}
