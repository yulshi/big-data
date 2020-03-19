package com.interview.algorithm.learning.a03_linkedlist.unidirection;

/**
 * @author yulshi
 * @create 2020/02/23 08:31
 */
public class Node {

  public final int data;
  public Node next;

  public Node(int data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "Node{" +
            "data=" + data +
            ", next=" + (next != null ? next.data : "null") +
            '}';
  }
}
