package com.interview.algorithm.learning.a08_tree.avl;

/**
 @author yulshi
 @create 2020/03/01 22:20
 */
public class Main {

  public static void main(String[] args) {
//    int[] arr = {4,3,6,5,7,8};
//    int[] arr = {10,12,8,9,7,6};
    int[] arr = {10,11,7,6,8,9};
//    int[] arr = {7, 6, 8, 9};
    AVLTree avlTree = new AVLTree(arr);
    avlTree.infixTraverse();

    System.out.println(avlTree.getRoot().height());
    System.out.println(avlTree.getRoot().left.height());
    System.out.println(avlTree.getRoot().right.height());



  }



}
