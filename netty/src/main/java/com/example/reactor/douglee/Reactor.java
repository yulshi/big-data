package com.example.reactor.douglee;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Take advantage of SelectionKey#attachement to dispatch
 * every selected SelectionKey instance
 *
 * @author yulshi
 * @create 2020/05/05 13:54
 */
@Slf4j
public class Reactor implements Runnable {

  final Selector selector;
  final ServerSocketChannel serverSocketChannel;

  public Reactor(int port) throws IOException {
    selector = Selector.open();
    serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress(port));
    serverSocketChannel.configureBlocking(false);
    SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    sk.attach(new Acceptor());
  }

  @Override
  public void run() {

    try {

      while (!Thread.interrupted()) {
        selector.select();
        Iterator<SelectionKey> it = selector.selectedKeys().iterator();
        while (it.hasNext()) {
          SelectionKey key = it.next();
          it.remove();
          dispatch(key);
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void dispatch(SelectionKey key) {
    Runnable r = (Runnable) key.attachment();
    if (r != null) {
      r.run();
    }
  }

  class Acceptor implements Runnable {

    public Acceptor() {
      log.info("Creating Acceptor ...");
    }

    @Override
    public void run() {
      try {
        SocketChannel c = serverSocketChannel.accept();
        if (c != null) {
          log.info("A new client comes in");
          new Handler(selector, c);
        }

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
