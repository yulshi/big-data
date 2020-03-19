package com.interview.oome;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * @author yulshi
 * @create 2019/11/27 13:21
 */
public class DirectBufferMemory {
  public static void main(String[] args) {
    System.out.println("Configured MaxDirectMemorySize: " + (sun.misc.VM.maxDirectMemory() / 1024 / 1024) + "M");
    try {
        TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    ByteBuffer bb = ByteBuffer.allocateDirect(6 * 1024 * 1024);
  }
}
