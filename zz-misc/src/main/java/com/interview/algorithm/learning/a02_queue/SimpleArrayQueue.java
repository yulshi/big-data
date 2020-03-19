package com.interview.algorithm.learning.a02_queue;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的使用数据实现队列功能，但是此种实现方法有重大的缺陷，即，已经取出数据的数组位置浪费掉了
 * 为了更好的解决这个问题，需要使用数组模拟环形队列
 *
 * @author yulshi
 * @create 2020/02/22 18:27
 */
public class SimpleArrayQueue<E> implements Queue<E> {

  private final Object[] arr;

  private final int maxSize;
  private int front = -1;  // 指向队列头的前一个位置
  private int rear = -1;   // 指向队列尾部的位置

  public SimpleArrayQueue(int maxSize) {
    this.maxSize = maxSize;
    this.arr = new Object[maxSize];
  }

  @Override
  public void enqueue(E e) {
    if (isFull()) {
      throw new RuntimeException("The queue is full");
    }
    arr[++rear] = e;
  }

  @Override
  public E dequeue() {
    if (isEmpty()) {
      throw new RuntimeException("The queue is empty");
    }
    return (E) arr[++front];
  }

  @Override
  public int capacity() {
    return maxSize;
  }

  @Override
  public List<E> available() {
    List<E> left = new ArrayList<>();
    int count = rear - front;
    if (count > 0) {
      for (int i = 0; i < count; i++) {
        left.add((E) arr[i + front + 1]);
      }
      return left;
    }
    return left;

  }

  @Override
  public E peek() {
    if (isEmpty()) {
      throw new RuntimeException("No data");
    }
    return (E) arr[front + 1];
  }

  @Override
  public int size() {
    return rear - front;
  }

  private boolean isFull() {
    return rear == maxSize - 1;
  }

  private boolean isEmpty() {
    return rear == front;
  }
}
