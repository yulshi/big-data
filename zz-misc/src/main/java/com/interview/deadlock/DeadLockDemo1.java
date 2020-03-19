package com.interview.deadlock;

import java.util.concurrent.TimeUnit;

/**
 * @author yulshi
 * @create 2019/11/25 15:26
 */
public class DeadLockDemo1 {

    public static void main(String[] args) {

        A a = new A();
        B b = new B();

        new Thread(() -> {
            a.method1(b);
        }).start();

        new Thread(() -> {
            b.syncMethod1(a);
        }).start();
    }

    static class A {

        public synchronized void method1(B b) {
            System.out.println("A.method1");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            b.syncMethod2();
        }

        public synchronized void method2() {
            System.out.println("A.method2");
        }

    }

    static class B {

        public synchronized void syncMethod1(A a) {
            System.out.println("B.syncMethod1");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            a.method2();
        }

        public synchronized void syncMethod2() {
            System.out.println("B.syncMethod2");
        }
    }
}
