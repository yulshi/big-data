package com.interview.algorithm.learning.a08_tree.avl;

/**
 @author yulshi
 @create 2020/03/01 22:15
 */
public class AVLTree {

  private Node root;

  public AVLTree(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      add(new Node(arr[i]));
    }
  }

  private void add(Node node) {
    if (root == null) {
      root = node;
    } else {
      root.add(node);
    }
  }

  public void infixTraverse() {
    this.root.infixTraverse();
  }

  public Node getRoot() {
    return root;
  }
}
