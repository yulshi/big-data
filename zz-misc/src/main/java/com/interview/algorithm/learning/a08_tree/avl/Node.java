package com.interview.algorithm.learning.a08_tree.avl;

/**
 @author yulshi
 @create 2020/03/01 22:09
 */
public class Node {

  public int value;
  public Node left;
  public Node right;

  public Node(int value) {
    this.value = value;
  }

  /**
   * 以当前节点为根节点的树的高度
   *
   * @return
   */
  public int height() {
    return Math.max(this.left == null ? 0 : this.left.height(),
            this.right == null ? 0 : this.right.height()) + 1;
  }

  public int leftHeight() {
    return this.left == null ? 0 : this.left.height();
  }

  public int rightHeight() {
    return this.right == null ? 0 : this.right.height();
  }

  /**
   * 向平衡二叉搜索树中添加节点
   *
   * @param node
   */
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

    // 当当前节点的左子树的高度比右子树的高度大于1，则进行右旋转
    if (this.leftHeight() - this.rightHeight() > 1) {
      if (this.left != null && this.left.rightHeight() > this.left.leftHeight()) {
        // 当左子树的右子树比左子树的左子树高，则先进行左旋转
        this.left.leftRotate();
      }
      this.rightRotate();
      return;
    }

    // 当当前节点的右子树比左子树的高度大于1，则进行左旋转
    if (this.rightHeight() - this.leftHeight() > 1) {
      // 当当前右子树的左子树高于右子树的右子树，则先进行右旋转
      if (this.right != null && this.right.leftHeight() > this.right.rightHeight()) {
        this.right.rightRotate();
      }
      this.leftRotate();
    }

  }

  /**
   * 以当前节点为根节点进行左旋转
   */
  public void leftRotate() {
    if (this.right == null) return;
    // 创建一个新的节点，并给值当前节点的值
    Node newNode = new Node(this.value);
    // 把新节点的左子树设置成当前节点的做子树
    newNode.left = this.left;
    // 把新节点的右子树设置成当前节点的右子树的左子树
    newNode.right = this.right.left;
    // 把当前节点的值设置成右子节点的值
    this.value = this.right.value;
    // 把当前节点的右子树设置成右子树的右子树
    this.right = this.right.right;
    // 把当前节点的左子节点设置成新节点
    this.left = newNode;
  }

  /**
   * 以当前节点为根节点进行右旋转
   */
  public void rightRotate() {
    if(this.left == null) return;
    // 创建一个新的节点，并给值当前节点的值
    Node newNode = new Node(this.value);
    // 把新节点的右子树设置成当前节点的右子树
    newNode.right = this.right;
    // 把新节点的左子树设置成当前节点的左子树的右子树
    newNode.left = this.left.right;
    // 把当前节点的值设置成左子节点的值
    this.value = this.left.value;
    // 把当前节点的左子树设置成左子树的左子树
    this.left = this.left.left;
    // 把当前节点的右子树设置成新的节点
    this.right = newNode;
  }

  /**
   * 中序遍历
   */
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
