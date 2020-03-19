package com.interview.syncaid;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 人都到齐了再开会
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {

        CyclicBarrier barrier = new CyclicBarrier(5, () -> {
            System.out.println("##### Barrier is broken");
        });

        for (int i = 0; i < 5; i++) {
            final int tempInt = i;
            System.out.println("finished " + (tempInt + 1) + " barrier");
            new Thread(() -> {
                try {
                    for (int j = 0; j < 3; j++) {
                        barrier.await();
                        System.out.println("do something...");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i + 1)).start();

        }
    }
}
