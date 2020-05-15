package com.example.reactor.multithread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * @author yulshi
 * @create 2020/05/05 17:58
 */
public class Reactor implements Runnable {

  private final Selector selector;
  private final ServerSocketChannel serverSocketChannel;

  public Reactor(int port) throws IOException {
    selector = Selector.open();
    serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.configureBlocking(false);
    serverSocketChannel.bind(new InetSocketAddress(port));
    SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    sk.attach(new AcceptorHandler(sk));
  }

  @Override
  public void run() {

    try {

      while (!Thread.interrupted()) {
        selector.select();

        Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
        while (iter.hasNext()) {
          SelectionKey key = iter.next();
          iter.remove();
          dispatch(key);
        }
      }

    } catch (IOException ioe) {
      ioe.printStackTrace();
    }

  }

  private void dispatch(SelectionKey key) {

    Object attachment = key.attachment();
    if (attachment instanceof Handler) {
      Handler handler = (Handler) attachment;
      handler.handle();
    }

  }
}
