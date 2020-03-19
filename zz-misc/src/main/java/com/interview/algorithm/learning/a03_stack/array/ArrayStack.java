package com.interview.algorithm.learning.a03_stack.array;

/**
 * 使用数组实现一个栈
 *
 * 思路：
 * 1）使用top变量指向数组中刚刚被压入的数据的位置
 * 2）push的时候top++，赋值
 * 3）pop的时候，取值，然后pop--
 *
 * @author yulshi
 * @create 2020/02/24 10:18
 */
public class ArrayStack<E> {

  private final Object[] stack;
  private final int maxSize;

  private int top = -1;

  public ArrayStack(int maxSize) {
    this.maxSize = maxSize;
    this.stack = new Object[maxSize];
  }

  public boolean isFull() {
    return top == maxSize - 1;
  }

  public boolean isEmpty() {
    return top == -1;
  }

  public void push(E e) {

    if (isFull()) {
      throw new RuntimeException("The stack is full");
    }

    stack[++top] = e;

  }

  public E pop() {

    if (isEmpty()) {
      throw new RuntimeException("The stack is empty");
    }

    E e = (E) stack[top--];
    return e;
  }

  public E peek() {
    if (isEmpty()) {
      throw new RuntimeException("The stack is empty");
    }
    return (E) stack[top];
  }

  public void list() {
    if (isEmpty()) {
      throw new RuntimeException("The stack is empty");
    }
    for (int i = top; i > -1; i--) {
      System.out.println(stack[i]);
    }
  }

}
