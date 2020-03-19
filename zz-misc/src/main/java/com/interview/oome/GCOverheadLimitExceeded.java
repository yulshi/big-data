package com.interview.oome;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yulshi
 * @create 2019/11/27 13:04
 */
public class GCOverheadLimitExceeded {

  public static void main(String[] args) {

    int i = 0;
    List<String> list = new ArrayList<>();
    try {
      while (true) {
        list.add(i + "");
      }
    } finally {
      System.out.println("=====" + i);
    }
  }
}
