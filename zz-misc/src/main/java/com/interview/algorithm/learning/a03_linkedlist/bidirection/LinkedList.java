package com.interview.algorithm.learning.a03_linkedlist.bidirection;

/**
 * @author yulshi
 * @create 2020/02/23 20:49
 */
public class LinkedList {

  private final Node head = new Node(0);

  public void add(Node node) {

    Node temp = head;
    while (true) {
      if (temp.next == null) {
        break;
      }
      temp = temp.next;
    }

    temp.next = node;
    node.prev = temp;
  }

  public Node head() {
    head.next.prev = null;
    return head.next;
  }

}
