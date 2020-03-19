package com.interview.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * There is a demo that uses Lock/Condition way to implement the same functionality
 * See also {@link com.interview.locks.ordered.ProduceConsumeOneDemo}
 */
public class ProduceConsumeOneDemo {

    public static void main(String[] args) {

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
        SharedData data = new SharedData(queue);

        new Thread(() -> {
                data.produce();
        }, "producer >>").start();

        new Thread(() -> {
                data.consume();
        }, "consumer <<").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        data.setFlag(false);

    }

    static class SharedData {

        private volatile boolean flag = true;
        private AtomicInteger number = new AtomicInteger();
        private final BlockingQueue<Integer> queue;

        SharedData(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }


        public void produce() {
            while (flag) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    if(!queue.offer(number.incrementAndGet(), 2L, TimeUnit.SECONDS)) {
                        System.out.println("can not produce more...");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void consume() {
            while(flag) {
                try {
                    Integer result = queue.poll(2L, TimeUnit.SECONDS);
                    if (result == null) {
                        //flag = true;
                        System.out.println("Stop consuming ...");
                        break;
                    } else {
                        System.out.println(result);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }
}
