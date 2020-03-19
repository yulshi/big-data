package com.interview.collection;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class ContainerNotSafeDemo {

    public static void main(String[] args) {

        List<String> list = new CopyOnWriteArrayList<>();
//        List<String> list = new ArrayList<>();
//        List<String> list = new LinkedList<>();

        for (int i = 0; i < 3000; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0, 8));
                list.forEach((e) -> {});
            }).start();
        }

        while(Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println(list.size());

    }
}
