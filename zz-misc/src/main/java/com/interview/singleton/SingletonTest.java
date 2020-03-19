package com.interview.singleton;

public class SingletonTest {
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                SingletonLazy singleton = SingletonLazy.getInstance();
                SingletonEager singletonEager = SingletonEager.getInstance();
                SingletonInner singletonInner = SingletonInner.getInstance();
            }).start();
        }

    }

}

/**
 * A singleton using DCL (Double Check Lock) and Volatile
 *
 * volatile can avoid instruction re-ordering
 */
class SingletonLazy {


    private static volatile SingletonLazy instance = null;

    private SingletonLazy() {
        System.out.println("Constructing SingletonLazy instance ...");
    }

    public static SingletonLazy getInstance() {
        if (instance == null) {
            synchronized (SingletonLazy.class) {
                if (instance == null) {
                    instance = new SingletonLazy();
                }
            }
        }
        return instance;
    }
}

/**
 * A thread safe singleton that is initialized at the time loading class
 */
class SingletonEager {

    private static final SingletonEager instance = new SingletonEager();

    private SingletonEager() {
        System.out.println("Constructing SingleEager instance ...");
    }

    public static SingletonEager getInstance() {
        return instance;
    }
}

/**
 * A thread safe singleton that takes advantage of Static Inner class
 */
class SingletonInner {

    private SingletonInner() {
        System.out.println("Contructing an instance of SingletonInner ...");
    }

    /**
     * 静态内部类有两个特点
     * 1、只有在使用的时候才会被加载
     * 2、在加载时，不会被终端（线程安全）
     */
    private static class Inner {
        static {
            System.out.println("static inner class is being loaded...");
        }
        private static final SingletonInner instance = new SingletonInner();
    }

    public static SingletonInner getInstance() {
        return Inner.instance;
    }

    static {
        System.out.println("SingletonInner is loaded.");
    }
    public static void test() {
        System.out.println("testing..");
    }

}
