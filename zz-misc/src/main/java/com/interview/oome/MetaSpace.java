package com.interview.oome;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author yulshi
 * @create 2019/11/27 15:47
 */
public class MetaSpace {

  public static void main(String[] args) {
    int i = 0;
    try {
      while (true) {
        i++;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(MyClass.class);
        enhancer.setUseCache(false);
        enhancer.setCallback(new MethodInterceptor() {
          @Override
          public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            return proxy.invokeSuper(obj, args);
          }
        });
        enhancer.create();
      }
    } catch (Throwable e){
      System.out.println("after " + i + " times, error happens");
      e.printStackTrace();
    }
  }

  static class MyClass {}
}
