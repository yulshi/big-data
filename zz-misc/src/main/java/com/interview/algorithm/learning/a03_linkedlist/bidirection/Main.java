package com.interview.algorithm.learning.a03_linkedlist.bidirection;

import sun.jvm.hotspot.debugger.windbg.DLL;

/**
 * @author yulshi
 * @create 2020/02/23 20:49
 */
public class Main {

  public static void main(String[] args) {

    LinkedList list = new LinkedList();
    list.add(new Node(1));
    list.add(new Node(2));
    list.add(new Node(3));
    list.add(new Node(4));
    list.add(new Node(5));

    Node head = list.head();
    show(head);

    System.out.println("~~~~~~~~~~~~~~~");
    head = delete(head, 1);
    show(head);

  }

  private static void show(Node head) {
    Node temp = head;
    while (temp != null) {
      System.out.println(temp);
      temp = temp.next;
    }
  }

  private static Node delete(Node head, int data) {

    if (head.data == data) {
      head = head.next;
      return head;
    }

    Node temp = head;
    while (temp != null) {
      if (temp.data == data) {
        if (temp.prev != null) {
          temp.prev.next = temp.next;
        }
        if (temp.next != null) {
          temp.next.prev = temp.prev;
        }
        break;
      }
      temp = temp.next;
    }
    return head;
  }


}


