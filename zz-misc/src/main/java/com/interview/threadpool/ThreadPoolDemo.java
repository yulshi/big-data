package com.interview.threadpool;

import java.util.concurrent.*;

/**
 * @author yulshi
 * @create 2019/11/24 11:25
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        test();

    }

    private static void test() {
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        try {
            for (int i = 0; i < 20; i++) {
                final int tempInt = i;
                threadPool.execute(() -> {
                    System.out.println(String.valueOf(tempInt + 1) + "\tis doing work");
                });
            }
        } finally {
            threadPool.shutdown();
        }

    }
}
