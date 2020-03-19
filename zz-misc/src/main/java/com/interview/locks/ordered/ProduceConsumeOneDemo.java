package com.interview.locks.ordered;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Having two threads and one produces and one consumes in order.
 */
public class ProduceConsumeOneDemo {

    public static void main(String[] args) {

        SharedDataUsingLock data = new SharedDataUsingLock();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.produce();
            }
        }, "producer >>").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.consume();
            }
        }, "consumer <<").start();
    }

    /**
     * A shared data object implemented using Lock
     */
    static class SharedDataUsingLock {

        private int number = 0;
        private final Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        public void produce() {
            lock.lock();
            try {
                while (number != 0) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                number++;
                System.out.println(Thread.currentThread().getName() + ": " + number);
                condition.signal();
            } finally {
                lock.unlock();
            }
        }

        public void consume() {
            lock.lock();
            try {
                while (number == 0) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                number--;
                System.out.println(Thread.currentThread().getName() + ": " + number);
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}

