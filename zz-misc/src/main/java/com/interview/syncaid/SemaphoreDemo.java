package com.interview.syncaid;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 6个车抢3个车位
 */
public class SemaphoreDemo {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss ");
    public static void main(String[] args) {
        Semaphore available = new Semaphore(3);

        for (int i = 0; i < 6; i++) {

            new Thread(() -> {
                try {
                    available.acquire();
                    log(" got one pos");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    available.release();
                    log(" free this pos");
                }
            }, String.valueOf(i + 1)).start();

        }
    }

    private static void log(String msg) {
        System.out.println(sdf.format(new Date()) + Thread.currentThread().getName() + ": " + msg);
    }
}
