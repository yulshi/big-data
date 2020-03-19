package com.interview.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

    public static void main(String[] args) {

        Phone phone = new Phone();

        new Thread(() -> {
            phone.sendSMS();
        }, "t1").start();

        new Thread(() -> {
            phone.sendSMS();
        }, "t2").start();

        new Thread(() -> {
            phone.sendSMS();
        }, "t3").start();
    }
}

class Phone {

    Lock lock = new ReentrantLock();

    public void sendSMS() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + ": sendSMS is invoked");
            sendEmail();
        } finally {
            lock.unlock();
        }
    }

    private void sendEmail() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + ": sendEmail is invoked");
        } finally {
            lock.unlock();
        }
    }
}
