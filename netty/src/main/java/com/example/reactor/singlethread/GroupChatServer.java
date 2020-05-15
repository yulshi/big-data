package com.example.reactor.singlethread;

import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/05/05 18:48
 */
public class GroupChatServer {

  public static void main(String[] args) throws IOException, InterruptedException {

    Thread boss = new Thread(new Reactor(7777));
    boss.start();
    boss.join();

  }

}
