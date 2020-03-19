package com.interview.algorithm.learning.a02_queue;

/**
 * @author yulshi
 * @create 2020/02/22 18:24
 */
public class QueueMain {

  public static void main(String[] args) {

    Queue<String> q = new CircleArrayQueue<>(5);
    q.enqueue("abc");
    q.enqueue("def");
    q.enqueue("hgi");
    q.enqueue("jkl");
    q.enqueue("mno");


    q.available().forEach(System.out::println);

    System.out.println("~~~~~~~~~~~~~~~~~~~");
    System.out.println(q.dequeue());
    System.out.println(q.dequeue());
    System.out.println(q.dequeue());

    q.enqueue("pqr");

    System.out.println("~~~~~~~~~~~~~~~~~~~");
    q.available().forEach(System.out::println);
    System.out.println("The current head: " + q.peek());
  }
}
