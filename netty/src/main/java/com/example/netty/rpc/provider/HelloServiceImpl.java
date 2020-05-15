package com.example.netty.rpc.provider;

import com.example.netty.rpc.common.HelloService;

/**
 * @author yulshi
 * @create 2020/01/27 11:33
 */
public class HelloServiceImpl implements HelloService {
  @Override
  public String hello(String name) {
    return "Aloha, " + name;
  }
}
