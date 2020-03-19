package com.interview.algorithm.learning.a03_linkedlist.bidirection;

/**
 * @author yulshi
 * @create 2020/02/23 20:46
 */
public class Node {

  public final int data;
  public Node prev;
  public Node next;

  public Node(int data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "Node{" +
            "data=" + data +
            ", prev=" + (prev != null ? prev.data : "null") +
            ", next=" + (next != null ? next.data : "null") +
            '}';
  }
}
