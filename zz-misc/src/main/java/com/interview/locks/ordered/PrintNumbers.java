package com.interview.locks.ordered;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Having 2 threads and each print a number one after another.
 * That said, thread 1 print odd numbers and thread 2 print even numbers in order
 */
public class PrintNumbers {

    public static void main(String[] args) {

//        SharedDataUsingSynchronize data = new SharedDataUsingSynchronize();
        SharedDataUsingLock data = new SharedDataUsingLock();

        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                data.printOdd();
            }
        }, "t1").start();

        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                data.printEven();
            }
        }, "t2").start();
    }
}

/**
 * Synchronized way:
 *  1. synchronized key word
 *  2. Object.wait
 *  3. Object.notify
 */
class SharedDataUsingSynchronize {
    private int number = 0;

    public synchronized void printOdd() {
        // 判断
        while (number % 2 != 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 干活
        number++;
        System.out.println(Thread.currentThread().getName() + ": " + number);
        // 通知
        this.notifyAll();
    }

    public synchronized void printEven() {
        // 判断
        while (number % 2 == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 干活
        number++;
        System.out.println(Thread.currentThread().getName() + ": " + number);
        // 通知
        this.notifyAll();
    }

}

/**
 * Lock way
 *  1. Lock.lock
 *  2. Condition.await
 *  3. Condition.signal
 */
class SharedDataUsingLock {

    private int number = 0;
    private final Lock lock = new ReentrantLock(true);
    Condition condition = lock.newCondition();

    public void printOdd() {
        // 获得锁
        lock.lock();
        try {
            // 判断
            while(number % 2 != 0) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 干活
            number++;
            System.out.println(Thread.currentThread().getName()+ ": " + number);
            // 通知
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void printEven() {
        lock.lock();
        try {
            while(number % 2 == 0) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            number++;
            System.out.println(Thread.currentThread().getName()+ ": " + number);
            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}
