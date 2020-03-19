package com.interview.deadlock;

import java.util.concurrent.TimeUnit;

/**
 * @author yulshi
 * @create 2019/11/25 22:01
 */
public class DeadLockDemo2 {

  public static void main(String[] args) {
    String lockA = "lockA";
    String lockB = "lockB";

    new Thread(new Task(lockA, lockB)).start();
    new Thread(new Task(lockB, lockA)).start();
  }

  static class Task implements Runnable {

    private String lockA;
    private String lockB;

    public Task(String strA, String strB) {
      this.lockA = strA;
      this.lockB = strB;
    }

    @Override
    public void run() {

      synchronized (lockA) {
        System.out.println("holding the lock of " + lockA);

        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        synchronized (lockB) {
          System.out.println("holding the lock of " + lockB + " inside of" + lockA);
        }
      }
    }
  }
}

