package com.example.reactor.multithread;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author yulshi
 * @create 2020/05/06 08:39
 */
@Slf4j
public class Processer implements Runnable {

  private final Selector selector;
  private final SelectionKey key;
  private final String username;
  private final byte[] data;

  private final ByteBuffer buffer = ByteBuffer.allocate(512);

  public Processer(Selector selector, SelectionKey key, String username, byte[] data) {
    this.selector = selector;
    this.key = key;
    this.username = username;
    this.data = data;
  }

  @Override
  public void run() {

    try {
      String message = new String(data, "utf-8");
      log.info("[RECEIVED] " + message);

      // process
      String retMsg = String.format("[%s] %s", username, message);

      broadcast(retMsg);

    } catch (IOException ioe) {
      ioe.printStackTrace();
    }

  }

  private void broadcast(String message) throws IOException {

    log.info("broadcasting from processor ...");

    for (SelectionKey selectionKey : selector.keys()) {
      if (selectionKey.isValid() && key != selectionKey) {
        SelectableChannel ch = selectionKey.channel();
        if (ch instanceof SocketChannel) {
          SocketChannel channel = (SocketChannel) ch;
          if (channel.isOpen()) {
            channel.write(ByteBuffer.wrap(message.getBytes("utf-8")));
          }
        }
      }
    }

    if (key.isValid()) {
      key.interestOps(SelectionKey.OP_READ);
      selector.wakeup();
    }
  }


}
