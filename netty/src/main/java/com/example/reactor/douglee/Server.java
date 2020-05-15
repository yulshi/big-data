package com.example.reactor.douglee;

import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/05/05 16:23
 */
public class Server {

  public static void main(String[] args) throws IOException, InterruptedException {

    Reactor reactor = new Reactor(7777);

    Thread boss = new Thread(reactor);
    boss.start();
    boss.join();

  }

}
