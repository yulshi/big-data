package com.example.kafka.test;

import java.lang.reflect.Field;

/**
 @author yulshi
 @create 2020/03/17 14:25
 */
public class Test {

  public static void main(String[] args) throws Exception {

    String s = "abc";

    Class<? extends String> cls = s.getClass();
    Field field = cls.getDeclaredField("value");
    field.setAccessible(true);
    char[] value = (char[]) field.get(s);
    value[1] = '-';

    System.out.println(s);


  }

//  public static void test(byte b) {
//    System.out.println("byte");
//  }

  public static void test(int i) {
    System.out.println("int");
  }

  public static void test(short s) {
    System.out.println("short");
  }

  public static void test(char c) {
    System.out.println("char");
  }

  public static void test(A a) {
    System.out.println("a");
  }

  public static void test(B b) {
    System.out.println("b");
  }

  public static void test(C c) {
    System.out.println("c");
  }

}

class A {
}

class B extends A {
}

class C extends B {
}
