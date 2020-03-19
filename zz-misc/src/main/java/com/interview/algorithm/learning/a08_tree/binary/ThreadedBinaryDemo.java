package com.interview.algorithm.learning.a08_tree.binary;

/**
 @author yulshi
 @create 2020/02/28 17:38
 */
public class ThreadedBinaryDemo {

  public static void main(String[] args) {

    Node node8 = new Node(8);
    Node node6 = new Node(6);
    Node node2 = new Node(2);
    Node node3 = new Node(3);
    Node node5 = new Node(5);
    Node node1 = new Node(1);
    Node node4 = new Node(4);
    Node node7 = new Node(7);

    node8.left = node6;
    node8.right = node1;
    node6.left = node2;
    node6.right = node3;
    node3.left = node5;
    node1.left = node4;
    node1.right = node7;

    ThreadedBinaryTree binaryTree = new ThreadedBinaryTree(node8);
    binaryTree.traverse();

  }

  public static class ThreadedBinaryTree {

    private final Node root;
    private Node prevNode = null;

    public ThreadedBinaryTree(Node root) {
      this.root = root;
      threaded(root);
    }

    /**
     * 按照中序遍历的方式线索化该二叉树
     *
     * @param node
     */
    private void threaded(Node node) {

      // 第一步：递归左子树
      if (node.left != null) {
        threaded(node.left);
      }
      // 第二步：处理本节点
      if (node.left == null) {
        // 如果当前节点的左子节点为null，则让它指向prevNode，同时指定指针类型为"前驱"
        node.left = prevNode;
        node.leftPointerType = PointerType.PrevNode;
      }

      if (prevNode != null && prevNode.right == null) {
        // 如果prevNode的右子节点为null，则让它指向当前节点，同时指定指针类型为"后继"
        prevNode.right = node;
        prevNode.rightPointerType = PointerType.NextNode;
      }

      prevNode = node;

      // 第三步：递归右子树
      if (node.right != null) {
        threaded(node.right);
      }
    }

    public void traverse() {

      Node node = root;

      while (node != null) {
        // 找到左子节点指针类型为"前驱"的节点
        while (node.leftPointerType != PointerType.PrevNode) {
          node = node.left;
        }
        System.out.printf("%d ", node.data);

        // 如果当前节点的右子节点的类型为后继节点，就一直输出
        while (node.rightPointerType == PointerType.NextNode) {
          node = node.right;
          System.out.printf("%d ", node.data);
        }
        // 略过非"后继"节点
        node = node.right;
      }
    }


  }

  public static class Node {

    private final int data;
    public Node left;
    public Node right;

    // 线索化后，一个节点的左子节点可能指向一个左子树，也可能指向前驱节点
    public PointerType leftPointerType = PointerType.SubTree;
    // 线索化后，一个节点的右子节点可能指向一个右子树，也可能指向后继节点
    public PointerType rightPointerType = PointerType.SubTree;

    public Node(int data) {
      this.data = data;
    }

    @Override
    public String toString() {
      return "Node{" +
              "data=" + data +
              ", left=" + (left != null ? left.data : " ") +
              ", right=" + (right != null ? right.data : " ") +
              '}';
    }
  }

  public static enum PointerType {
    SubTree,
    PrevNode,
    NextNode;
  }
}
