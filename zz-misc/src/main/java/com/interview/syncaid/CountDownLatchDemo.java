package com.interview.syncaid;

import java.util.concurrent.CountDownLatch;

/**
 * 教室的人都走光了，再锁门
 */
public class CountDownLatchDemo {

  public static void main(String[] args) {

    int latchCount = 5;
    CountDownLatch latch = new CountDownLatch(latchCount);

    // This thread should be running only if all of the other threads finish.
    new Thread(() -> {
      try {
        latch.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("===== End waiting =====");
    }).start();

    // Bring some threads doing their work and for each of them, once down, the latch will be
    // count down by 1.
    for (int i = 0; i < latchCount; i++) {
      new Thread(() -> {
        System.out.println("do something on " + Thread.currentThread().getName());
        latch.countDown();
      }, String.valueOf(i + 1)).start();
    }
  }
}
