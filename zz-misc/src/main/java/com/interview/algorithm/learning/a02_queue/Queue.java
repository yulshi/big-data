package com.interview.algorithm.learning.a02_queue;

import java.util.List;

/**
 * @author yulshi
 * @create 2020/02/22 18:39
 */
public interface Queue<E> {

  void enqueue(E e);

  E dequeue();

  int capacity();

  int size();

  List<E> available();

  E peek();

}
