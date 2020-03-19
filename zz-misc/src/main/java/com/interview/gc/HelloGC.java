package com.interview.gc;

import java.util.WeakHashMap;

/**
 * @author yulshi
 * @create 2019/11/26 13:27
 */
public class HelloGC {

  public static void main(String[] args) throws InterruptedException {
    long xms = Runtime.getRuntime().totalMemory();
    long xmx = Runtime.getRuntime().maxMemory();

    System.out.println("-Xms=" + (xms /1024/1024));
    System.out.println("-Xmx=" + (xmx /1024/1024));

    String str = "test";
    str.intern();
    //Thread.sleep(Integer.MAX_VALUE);
  }
}
