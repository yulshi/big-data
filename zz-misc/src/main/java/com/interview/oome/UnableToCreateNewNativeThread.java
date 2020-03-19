package com.interview.oome;

import java.util.concurrent.TimeUnit;

/**
 * To check out how many threads a process allows to create, we can use the command
 * ulimit -u
 *
 * @author yulshi
 * @create 2019/11/27 13:45
 */
public class UnableToCreateNewNativeThread {

  public static void main(String[] args) {
    for(int i = 1; ; i++) {
      System.out.println("***** i = " + i);
      new Thread(()->{
        try {
          TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }).start();
    }
  }
}
