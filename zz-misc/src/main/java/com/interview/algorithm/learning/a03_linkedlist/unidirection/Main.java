package com.interview.algorithm.learning.a03_linkedlist.unidirection;

import java.util.Stack;

/**
 * @author yulshi
 * @create 2020/02/23 08:40
 */
public class Main {

  public static void main(String[] args) {

    SingleLinkedList list = new SingleLinkedList();
    list.add(new Node(1));
    list.add(new Node(2));
//    list.add(new Node(3));
//    list.add(new Node(4));
    list.show();

    System.out.println("~~~~~~~~~~~~~~");
    Node head = list.head();
    System.out.println("Head = " + head);

    System.out.println("====================");
    show(reverse1(head));
//    reversePrint2(head);
  }

  /**
   * 面试题：反转一个单向链表，方式1
   * <p>
   * 方式1的思路
   * 1）先创建一个Node，作为反转以后链表的头节点（reverseNode）
   * 2）遍历链表的每个节点
   * 2.1）把第一个节点接续到reverseNode的后边
   * 2.2）把以后的每个节点放到reverseNode的后边，其他节点的前边
   * 3）返回reverseNode的下一个节点
   *
   * @param head
   * @return
   */
  private static Node reverse1(Node head) {

    if (head.next == null) {
      return head;
    }

    Node reverseHead = new Node(-1);

    Node curr = head;
    Node next = null;

    while (curr != null) {
      next = curr.next;
      curr.next = reverseHead.next;
      reverseHead.next = curr;
      curr = next;
    }

    return reverseHead.next;

  }

  /**
   * 面试题：反转一个单向列表，方式2
   * <p>
   * 思路
   * 1）创建三个指针，prev、curr、next，都指向head
   * 2）创建一个新的Node作为反转以后的头节点（reverseNode）
   * 3）遍历链表的每一个节点，让curr指向prev，然后同时向前启动三个指针
   * 4）如果curr的下一个为null，说明到了最后一个节点，将之赋值给reverseNode并返回
   *
   * @param head
   * @return
   */
  private static Node reverse2(Node head) {

    Node prev = null;
    Node curr = head;
    Node next = null;

    Node reversed = null;

    while (curr != null) {

      // Keep the next node in a temporary variable
      next = curr.next;

      if (curr.next == null) {
        reversed = curr;
      }

      // move prev and curr forward by one node
      curr.next = prev;
      prev = curr;
      curr = next;
    }

    return reversed;
  }

  /**
   * 面试题：从尾到头打印单向链表，方式1：递归调用
   *
   * @param head
   */
  private static void reversePrint1(Node head) {
    if (head != null) {
      reversePrint1(head.next);
      System.out.println(head.data);
    }
  }

  /**
   * 面试题：从尾到头打印单向链表，方式1：Stack栈
   * <p>
   * 思路
   * 1）创建一个栈
   * 2）遍历链表，并把每一个元素放入栈中
   * 3）从栈中一次pop出每一个元素
   *
   * @param head
   */
  private static void reversePrint2(Node head) {

    Stack<Integer> stack = new Stack<>();

    Node curr = head;
    while (curr != null) {
      stack.push(curr.data);
      curr = curr.next;
    }

    while (!stack.empty()) {
      System.out.println(stack.pop());
    }

  }

  /**
   * 合并两个有序单向链表，要求合并之后的链表依然有序
   * <p>
   * 思路：
   * 1）两层循环，外层循环遍历第一个链表，内层循环遍历第二个链表
   * 2）外层循环中，两个指针curr和next
   * 3）在内层循环中，如发现node的值大于curr的值但是小于next的值，则继续判断下一个节点的值是否也是如此
   * 3.1）如果是，则继续判断下一个节点
   * 3.2）如果不是，则把前一个节点的next指向外层循环的next
   *
   * @param head1
   * @param head2
   * @return
   */
  private static Node combine(Node head1, Node head2) {

//        Node curr1 = head1;
//        Node next1 = head1;
//
//        Node curr2 = head2;
//        Node next2 = head2;
//
//        while (curr1 != null) {
//            next1 = curr1.next;
//
//            while (curr2 != null) {
//                next2 = curr2.next;
//                if (next1 != null && curr2.data > next1.data) {
//                    curr1 = next1;
//                    next1 = next1.next;
//                    curr1.next = curr2;
//                } else if (curr2.data > curr1.data) {
//                    curr1.next = curr2;
//                    curr2 = next2;
//                    next2 = next2.next;
//                } else if (curr2.data < curr1.data) {
//
//                }
//            }
//        }
    return null;

  }


  private static void show(Node head) {
    Node temp = head;
    while (temp != null) {
      System.out.println(temp);
      temp = temp.next;
    }
  }

}
