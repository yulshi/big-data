package com.interview.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLockDemo {

    public static void main(String[] args) {

        SpinkLock lock = new SpinkLock();

        new Thread(() -> {
            lock.lock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }, "t1").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }, "t2").start();
    }
}

class SpinkLock {

    private AtomicReference<Thread> atomicThread = new AtomicReference<>();

    public void lock() {
        while(!atomicThread.compareAndSet(null, Thread.currentThread())) {
            // keep looping
        }
        System.out.println(Thread.currentThread().getName() + ": locking");
    }

    public void unlock() {
        atomicThread.compareAndSet(Thread.currentThread(), null);
        System.out.println(Thread.currentThread().getName() + ": unlocked");
    }

}
