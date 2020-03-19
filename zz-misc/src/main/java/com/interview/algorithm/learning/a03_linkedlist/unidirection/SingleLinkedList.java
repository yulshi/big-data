package com.interview.algorithm.learning.a03_linkedlist.unidirection;

/**
 * @author yulshi
 * @create 2020/02/23 08:40
 */
public class SingleLinkedList {

  private final Node head = new Node(0);

  /**
   * Add node by order according to Node.no
   *
   * @param node
   */
  public void addInOrder(Node node) {

    Node prev = head;
    Node next = head;
    while (true) {
      if (next == null) {
        prev.next = node;
        break;
      }
      if (next.data < node.data) {
        prev = next;
        next = next.next;
      } else if (next.data > node.data) {
        node.next = next;
        prev.next = node;
        break;
      } else {
        System.out.println("The data has existed");
        break;
      }
    }

  }

  public void add(Node node) {
    Node temp = head;
    while (temp.next != null) {
      temp = temp.next;
    }
    temp.next = node;
    node.next = null;
  }

  public Node head() {
    return head != null ? head.next : head;
  }


  public void show() {
    if (head.next == null) {
      return;
    }
    Node temp = head;
    while (temp.next != null) {
      temp = temp.next;
      System.out.println(temp);
    }

  }

  /************* 面试题 *****************/
  /**
   * 求单链表的有效节点的个数
   *
   * @return
   */
  public int getNodesNum() {
    int count = 0;
    Node temp = head;
    while (temp.next != null) {
      count++;
      temp = temp.next;
    }
    return count;
  }

  /**
   * 查找单链表中的倒数第k个节点
   *
   * @param k
   * @return
   */
  public Node getLastIndex(int k) {

    // 1） 遍历一次，得到所有有效节点的个数
    int total = 0;
    Node temp = head;
    while (temp.next != null) {
      total++;
      temp = temp.next;
    }

    // 2）得到正数的index
    int index = total - k + 1;
    if (index < 1) {
      System.out.println("the input 'k' is not valid");
      return null;
    }

    // 3） 再遍历一次，直到index
    temp = head;
    for (int i = 0; i < index; i++) {
      temp = temp.next;
    }
    return temp;

  }

}
