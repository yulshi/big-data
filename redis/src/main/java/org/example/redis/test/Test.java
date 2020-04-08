package org.example.redis.test;

/**
 @author yulshi
 @create 2020/03/20 22:37
 */
public class Test {

  public static void main(String[] args) {
    StringBuilder codeBuilder = new StringBuilder();
    for (int i = 0; i < 6; i++) {
      codeBuilder.append((int) (Math.random() * 10));
    }
    String code = codeBuilder.toString();
    System.out.println(code);
  }
}
