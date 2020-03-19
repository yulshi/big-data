package com.interview.algorithm.learning.a03_linkedlist.circle;

import com.interview.algorithm.LinkedListDemo;
import com.sun.org.apache.xml.internal.security.Init;

import java.util.HashSet;
import java.util.List;

/**
 * @author yulshi
 * @create 2020/02/23 21:58
 */
public class Main {

  public static void main(String[] args) {

    Node head = createCircle();
    System.out.println("Has circle? " + hasCircle2(head));
    show(head);

    System.out.println("~~~~~~~~~~~~~~");
    josephu(head, 5, 1, 2);

  }

  /**
   * 创建环状单向链表
   *
   * 思路
   * 1）先创建一个head
   * 2）创建一个prev指针，指向head
   * 3）每次新创建node的时候，让prev的next指向它
   * 4）最后一个node的next指向head
   *
   * @return
   */
  private static Node createCircle() {

    Node head = new Node(1);
    Node prev = head;

    for (int i = 2; i < 6; i++) {
      Node curr = new Node(i);
      prev.next = curr;
      prev = curr;
    }

    prev.next = head;

    return head;
  }

  /**
   * Josephu（约瑟夫）问题
   * <p>
   * 编号1之n个人围坐在一起，第k（1<=k<=n）个人从1开始报数，数到m的那个人出列，
   * 然后他的下一位又从1开始报数，数到m的那个人再出列，直到所有人出列为止。
   * 问：出列顺序
   *
   * 思路：
   * 1）创建一个prev和一个curr指针
   * 2）数到m的那个节点要被删除，即prev.next=curr.next
   * 3）当只剩一个节点的时候，就表示所有都已经出列了
   *
   * 也可以使用递归来解决。
   *
   * @param head
   * @return
   */
  private static void josephu(Node head, int n, int k, int m) {

    Node prev = null;
    Node curr = head;

    // Find out the first k
    for (int i = 1; i < k; i++) {
      prev = curr;
      curr = curr.next;
    }

    while (true) {
      if (curr == prev) {
        System.out.println(curr.data);
        break;
      }

      // move forward by m - 1
      for (int i = 1; i < m; i++) {
        prev = curr;
        curr = curr.next;
      }

      // remove the the node that count m
      System.out.println(curr.data);
      prev.next = curr.next;
      curr = curr.next;

    }

  }

  /**
   * 遍历整个单向链表，该链表有可能有环
   *
   * 注意：如果该链表是一个完全环状，即首尾相连，则没有必要使用HashSet，
   * 直接在遍历的时候判断当前指针是否等于head即可。
   *
   * @param head
   */
  private static void show(Node head) {

    HashSet<Integer> set = new HashSet<>();

    Node curr = head;
    while (curr != null) {
      if (set.contains(curr.hashCode())) {
        break;
      }
      System.out.println(curr);
      set.add(curr.hashCode());
      curr = curr.next;
    }

  }

  /**
   * 判断是否有环，方式一
   * <p>
   * 使用HashSet保存遍历过的值，如果有相同的值，说明有环
   *
   * @param head
   * @return
   */
  private static boolean hasCircle1(Node head) {

    HashSet<Integer> set = new HashSet<>();
    Node curr = head;
    while (curr != null) {
      if (set.contains(curr.hashCode())) {
        return true;
      }
      set.add(curr.hashCode());
      curr = curr.next;
    }
    return false;


  }

  /**
   * 判断是否有环，方式二
   * <p>
   * 使用一个快指针，一个慢指针实现，又称为"龟兔赛跑"
   * 要点：
   * 1）开始的时候，慢指针指向第一个节点，快指针指向第二个节点
   * 2）当快指针和慢指针指向同一个节点的时候，该节点就是交叉节点
   *
   * @param head
   * @return
   */
  private static boolean hasCircle2(Node head) {

    if (head == null || head.next == null) {
      return false;
    }
    Node slow = head;
    Node fast = head.next;
    while (slow != fast) {
      if (fast == null || fast.next == null) {
        return false;
      }
      slow = slow.next;
      fast = fast.next.next;
    }
    return true;
  }

}
