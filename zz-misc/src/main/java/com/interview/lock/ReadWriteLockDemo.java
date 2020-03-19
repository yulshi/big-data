package com.interview.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

    public static void main(String[] args) {
        SimpleCache cache = new SimpleCache();

        for (int i = 0; i < 5; i++) {
            final int tempInt = i + 1;
            new Thread(() -> {
                cache.put(Integer.toString(tempInt), Integer.toString(tempInt));
            }, Integer.toString(i + 1)).start();
        }

        for (int i = 0; i < 5; i++) {
            final int tempInt = i + 1;
            new Thread(() -> {
                cache.get(Integer.toString(tempInt));
            }, Integer.toString(i + 1)).start();
        }

    }
}

class SimpleCache {

    private volatile Map<String, Object> map = new HashMap<>();

    // Lock lock = new ReentrantLock();
    ReadWriteLock lock = new ReentrantReadWriteLock();

    public void put(String key, Object value) {
        lock.writeLock().lock();
        try {
            log("---> start writing: " + key);
            sleep();
            map.put(key, value);
            log("---> end writing: " + key);
        } finally {
            lock.writeLock().unlock();
        }
        System.out.println("SimpleCache.put");
    }

    public Object get(String key) {
        lock.readLock().lock();
        try {
            log("start reading...");
            sleep();
            Object res = map.get(key);
            log("end reading: " + res);
            return res;
        } finally {
            lock.readLock().unlock();
        }
    }

    private void log(String msg) {
        System.out.println(Thread.currentThread().getName() + ": " + msg);
    }

    private void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
