package com.interview.algorithm.learning.a08_tree.huffman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 @author yulshi
 @create 2020/02/29 12:51
 */
public class HuffmanDemo {

  public static void main(String[] args) {

    int[] arr = {13, 7, 8, 3, 29, 6, 1};

    HuffmanTree huffmanTree = new HuffmanTree(arr);
    huffmanTree.prefixTraverse();

//    int num  = 256;
//
//    StringBuilder sb = new StringBuilder();
//    for (int i = 7; i >= 0; i--) {
//      sb.append((num >> i) & 0x01);
//    }
//
//    System.out.println(sb.toString());

  }


}

class HuffmanTree {

  private final Node root;

  public HuffmanTree(int[] arr) {
    this.root = toHuffmanTree(arr);
  }

  /**
   * 把数组中的数据转换成霍夫曼树
   *
   * @param arr
   */
  private Node toHuffmanTree(int[] arr) {

    // Put all the elements in the array to an ArrayList and sort it
    List<Node> list = new ArrayList<>(arr.length);
    for (int ele : arr) {
      list.add(new Node(ele));
    }

    while (list.size() > 1) {
      // 对list中的Node进行排序
      Collections.sort(list);
      // 取出两个最小的节点，创建一个父节点，它的值就是这两个节点的值的和
      Node leftNode = list.get(0);
      Node rightNode = list.get(1);
      Node parentNode = new Node(leftNode.value + rightNode.value);
      parentNode.left = leftNode;
      parentNode.right = rightNode;
      // 删除两个最小的，然后加入他们的parent
      list.remove(0);
      list.remove(0);
      list.add(parentNode);
    }
    return list.get(0);

  }

  public void prefixTraverse() {
    prefixTraverseInternal(root);
  }

  private void prefixTraverseInternal(Node node) {
    System.out.printf("%d ", node.value);
    if (node.left != null) {
      prefixTraverseInternal(node.left);
    }
    if (node.right != null) {
      prefixTraverseInternal(node.right);
    }
  }

  public Node getRoot() {
    return root;
  }
}

class Node implements Comparable<Node> {
  public final int value;
  public Node left;
  public Node right;

  public Node(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "Node{" +
            "value=" + value +
            ", left=" + (left != null ? left.value : " ") +
            ", right=" + (right != null ? right.value : " ") +
            '}';
  }

  @Override
  public int compareTo(Node o) {
    return this.value - o.value;
  }

}
