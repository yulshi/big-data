package com.interview.algorithm.learning.a08_tree.bst;

/**
 @author yulshi
 @create 2020/03/01 09:31
 */
public class BinarySortTreeDemo {

  public static void main(String[] args) {

    int[] arr = {7, 3, 10, 12, 5, 1, 9};
    BinarySortTree bst = new BinarySortTree(arr);

    bst.add(new Node(2));
    bst.infixTraverse();
    System.out.println();

//    bst.delete(2);
//    bst.delete(5);
//    bst.delete(9);
//    bst.delete(12);
    bst.delete(7);
//    bst.delete(3);
//    bst.delete(10);
//    bst.delete(1);

    bst.infixTraverse();

  }
}

class BinarySortTree {

  private Node root;

  public BinarySortTree(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      add(new Node(arr[i]));
    }
  }

  public Node getRoot() {
    return root;
  }

  public void add(Node node) {
    if (root == null) {
      root = node;
    } else {
      root.add(node);
    }
  }

  /**
   * 删除值为targetValue的节点
   *
   * @param targetValue
   */
  public void delete(int targetValue) {
    if (root == null) return;
    if (root.left == null && root.right == null && root.value == targetValue) {
      root = null;
      return;
    }
    Node targetNode = find(root, targetValue);
    Node parentNode = findParent(root, targetValue);

    System.out.println("准备删除节点：\t\t" + targetNode);
    System.out.println("准备删除的父节点：\t" + parentNode);

    // 待删除的节点是叶子节点
    if (targetNode.left == null && targetNode.right == null) {
      if (targetNode.value < parentNode.value) {
        parentNode.left = null;
      } else {
        parentNode.right = null;
      }
      return;
    }

    // 待删除的节点只有一棵左子树
    if (targetNode.left != null && targetNode.right == null) {
      if (parentNode == null) {
        root = targetNode.left;
        return;
      }
      if (targetNode.value < parentNode.value) {
        parentNode.left = targetNode.left;
      } else {
        parentNode.right = targetNode.left;
      }
      return;
    }

    // 待删除的节点只有一棵右子树
    if (targetNode.right != null && targetNode.left == null) {
      if (parentNode == null) {
        root = targetNode.right;
        return;
      }
      if (targetNode.value < parentNode.value) {
        parentNode.left = targetNode.right;
      } else {
        parentNode.right = targetNode.right;
      }
      return;
    }

    // 待删除的节点既有左子树也有右子树
    // 从右子树中找出一个最小值
    Node minNode = targetNode.right;
    while (minNode.left != null) {
      minNode = minNode.left;
    }
    int minValue = minNode.value;

    delete(minValue);
    targetNode.value = minValue;
    return;

  }

  /**
   * 查找值为targetValue的节点
   *
   * @param node
   * @param targetValue
   * @return
   */
  public Node find(Node node, int targetValue) {

    if (node == null) return null;
    if (node.value == targetValue) return node;

    if (node.left != null && targetValue < node.value) {
      return find(node.left, targetValue);
    }

    if (node.right != null && targetValue > node.value) {
      return find(node.right, targetValue);
    }

    return null;

  }

  /**
   * 查找值为targetValue的节点的父节点
   *
   * @param node
   * @param targetValue
   * @return
   */
  public Node findParent(Node node, int targetValue) {
    if (node == null) return null;
    if ((node.left != null && node.left.value == targetValue)
            || (node.right != null && node.right.value == targetValue)) {
      return node;
    }
    if (node.left != null && targetValue < node.value) {
      return findParent(node.left, targetValue);
    }

    if (node.right != null && targetValue > node.value) {
      return findParent(node.right, targetValue);
    }
    return null;
  }

  public void infixTraverse() {
    if (root != null) {
      root.infixTraverse();
    }
  }

}

class Node {

  public int value;
  public Node left;
  public Node right;

  public Node(int value) {
    this.value = value;
  }

  public void add(Node node) {
    if (node == null) {
      return;
    }
    if (node.value < this.value) {
      if (this.left == null) {
        this.left = node;
      } else {
        this.left.add(node);
      }
    } else {
      if (this.right == null) {
        this.right = node;
      } else {
        this.right.add(node);
      }
    }
  }

  public void infixTraverse() {
    if (this.left != null) {
      this.left.infixTraverse();
    }
    System.out.println(this);
    if (this.right != null) {
      this.right.infixTraverse();
    }
  }

  @Override
  public String toString() {
    return "Node{" +
            "value=" + value +
            ", left=" + (left != null ? left.value : " ") +
            ", right=" + (right != null ? right.value : " ") +
            '}';
  }
}