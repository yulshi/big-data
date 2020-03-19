package com.interview.algorithm.learning.a02_queue;

import java.util.ArrayList;
import java.util.List;

/**
 * 循环队列
 *
 * 通过取模的方式让front和rear指针的值始终小于maxSize，从而在数组中循环
 *
 * @author yulshi
 * @create 2020/02/22 21:37
 */
public class CircleArrayQueue<E> implements Queue<E> {

  private final Object[] arr;

  private final int maxSize;
  private int front = 0; // 指向队列的头元素位置
  private int rear = 0;  // 指向队列的尾元素的下一个位置

  public CircleArrayQueue(int maxSize) {
    this.maxSize = maxSize + 1; // 让尾元素的下一个位置为保留位置
    arr = new Object[this.maxSize];
  }

  @Override
  public void enqueue(E e) {
    if (isFull()) {
      throw new RuntimeException("The queue is full");
    }
    // 让rear指针的当前位置保存新元素
    arr[rear] = e;
    // rear指向向后移动一位
    rear++;
    // 对rear取模
    rear = rear % maxSize;
  }

  @Override
  public E dequeue() {
    if (isEmpty()) {
      throw new RuntimeException("The queue is empty");
    }
    E e = (E) arr[front];
    front++;
    front = front % maxSize;
    return e;
  }

  @Override
  public int capacity() {
    return maxSize;
  }

  @Override
  public List<E> available() {
    List<E> left = new ArrayList<>();
    for (int i = front; i < front + size(); i++) {
      left.add((E) arr[i % maxSize]);
    }
    return left;
  }

  @Override
  public E peek() {
    if (isEmpty()) throw new RuntimeException("The queue is empty");
    return (E) arr[front];
  }

  public int size() {
    return (rear + maxSize - front) % maxSize;
  }

  private boolean isFull() {
    // 当尾指针的下一个位置正好是头指针的位置的时候，就表示队列已满
    return (rear + 1) % maxSize == front;
  }

  private boolean isEmpty() {
    return rear == front;
  }

}
