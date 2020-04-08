package com.example.help;

/**
 @author yulshi
 @create 2020/04/02 13:39
 */
public class Main {

  public static void main(String[] args) {

    B b = new B();
    b.method(new A("dd") {
      @Override
      public void hello() {
        System.out.println("Hello");
      }
    });

  }

}

abstract class A {
  public A(String str) {

  }
  abstract void hello();
}

class B {
  public void method(A a) {
    a.hello();
  }
}
