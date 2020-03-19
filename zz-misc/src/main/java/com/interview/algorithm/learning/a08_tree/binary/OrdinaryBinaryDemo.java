package com.interview.algorithm.learning.a08_tree.binary;

/**
 @author yulshi
 @create 2020/02/27 22:40
 */
public class OrdinaryBinaryDemo {

  public static void main(String[] args) {

    BinaryTree binaryTree = new BinaryTree(new Node(8));

    Node node8 = new Node(8);
    Node node6 = new Node(6);
    Node node2 = new Node(2);
    Node node3 = new Node(3);
    Node node5 = new Node(5);
    Node node1 = new Node(1);
    Node node4 = new Node(4);
    Node node7 = new Node(7);

    binaryTree.root.left = node6;
    binaryTree.root.right = node1;
    node6.left = node2;
    node6.right = node3;
    node3.left = node5;
    node1.left = node4;
    node1.right = node7;

    binaryTree.infixTranverse();
//    System.out.println(binaryTree.delete(6));
//    binaryTree.prefixTraverse();
  }

  private static class BinaryTree {

    private final Node root;

    private int searchCounter = 0;

    public BinaryTree(Node root) {
      this.root = root;
    }

    /**
     * 前序遍历
     */
    public void prefixTraverse() {
      prefixTraverseInternal(root);
    }

    private void prefixTraverseInternal(Node node) {

      if (node == null) return;
      System.out.printf("%d ", node.data);

      if (node.left != null) {
        prefixTraverseInternal(node.left);
      }
      if (node.right != null) {
        prefixTraverseInternal(node.right);
      }

    }

    /**
     * 中序遍历
     */
    public void infixTranverse() {
      infixTranverseInternal(root);
    }

    private void infixTranverseInternal(Node node) {

      if (node == null) return;

      if (node.left != null) {
        infixTranverseInternal(node.left);
      }

      System.out.printf("%d ", node.data);

      if (node.right != null) {
        infixTranverseInternal(node.right);
      }
    }

    /**
     * 后序遍历
     */
    public void postfixTraverse() {
      postfixTraverseInternal(root);
    }

    private void postfixTraverseInternal(Node node) {

      if (node == null) return;

      if (node.left != null) {
        postfixTraverseInternal(node.left);
      }

      if (node.right != null) {
        postfixTraverseInternal(node.right);
      }

      System.out.printf("%d ", node.data);

    }

    /**
     * 前序查找
     *
     * @param key
     * @return
     */
    public Node prefixSearch(int key) {
      this.searchCounter = 0;
      return prefixSearchInternal(root, key);
    }

    private Node prefixSearchInternal(Node node, int key) {

      if (node == null) return null;

      this.searchCounter++;
      if (node.data == key) {
        return node;
      }

      Node resultNode = null;
      if (node.left != null) {
        resultNode = prefixSearchInternal(node.left, key);
      }
      if (resultNode != null) {
        return resultNode;
      }

      if (node.right != null) {
        resultNode = prefixSearchInternal(node.right, key);
      }
      return resultNode;

    }

    /**
     * 中序查找
     * @param key
     * @return
     */
    public Node infixSearch(int key) {
      this.searchCounter = 0;
      return infixSearchInternal(root, key);
    }

    private Node infixSearchInternal(Node node, int key) {

      if (node == null) return null;

      Node resultNode = null;
      if (node.left != null) {
        resultNode = infixSearchInternal(node.left, key);
      }
      if (resultNode != null) {
        return resultNode;
      }

      this.searchCounter++;
      if (node.data == key) {
        return node;
      }

      if (node.right != null) {
        resultNode = infixSearchInternal(node.right, key);
      }
      return resultNode;

    }

    /**
     * 后序查找
     * @param key
     * @return
     */
    public Node postfixSearch(int key) {
      this.searchCounter = 0;
      return postfixSearchInternal(root, key);
    }

    private Node postfixSearchInternal(Node node, int key) {

      if (node == null) return null;

      Node resultNode = null;
      if (node.left != null) {
        resultNode = postfixSearchInternal(node.left, key);
      }
      if (resultNode != null) {
        return resultNode;
      }

      if (node.right != null) {
        resultNode = postfixSearchInternal(node.right, key);
      }
      if (resultNode != null) {
        return resultNode;
      }

      this.searchCounter++;
      if (node.data == key) {
        return node;
      }

      return null;

    }

    /**
     * 删除节点
     * @param key
     * @return
     */
    public boolean delete(int key) {
      return deleteInternal(root, key);
    }

    private boolean deleteInternal(Node node, int key) {

      if (node.left != null && node.left.data == key) {
        Node deletingNode = node.left;
        // 待删除的节点是叶子节点，直接删除
        if(deletingNode.left == null && deletingNode.right == null) {
          node.left = null;
          return true;
        }
        // 待删除的节点有一个子节点，直接把子节点上移
        if(deletingNode.left == null) {
          node.left = deletingNode.right;
          return true;
        }
        if(deletingNode.right == null) {
          node.left = deletingNode.left;
        }
        // 待删除的节点有两个子节点，把左子节点上移
        node.left = deletingNode.left;
        node.left.right = deletingNode.right;
        return true;
      }
      if(node.right != null && node.right.data == key) {
        node.right = null;
        return true;
      }

      boolean result = false;
      if (node.left != null) {
        result = deleteInternal(node.left, key);
      }
      if(result) {
        return true;
      }

      if (node.right != null) {
        result = deleteInternal(node.right, key);
      }

      return result;


    }

  }

  private static class Node {

    private final int data;
    public Node left;
    public Node right;

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

}

