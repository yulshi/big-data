package com.example.reactor.multithread;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author yulshi
 * @create 2020/05/05 18:01
 */
public class AcceptorHandler implements Handler {

  private final SelectionKey key;

  public AcceptorHandler(SelectionKey key) {
    this.key = key;
  }

  @Override
  public void handle() {

    try {

      ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

      SocketChannel socketChannel = serverSocketChannel.accept();
      socketChannel.configureBlocking(false);
      SelectionKey key = socketChannel.register(this.key.selector(), SelectionKey.OP_READ);
      new GroupChatHandler(key);

    } catch (IOException ioException) {
      ioException.printStackTrace();
    }

  }
}
