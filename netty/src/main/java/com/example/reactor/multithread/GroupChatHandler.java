package com.example.reactor.multithread;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * 把业务处理交给一个单独的线程去执行
 *
 * @author yulshi
 * @create 2020/05/05 18:09
 */
@Slf4j
public class GroupChatHandler implements Handler {

  private final Selector selector;
  private final SocketChannel socketChannel;
  private final SelectionKey key;

  private ByteBuffer input = ByteBuffer.allocate(512);
  private ByteBuffer output = ByteBuffer.allocate(512);

  private final String username;

  static ExecutorService threadpool = Executors.newCachedThreadPool();

  /**
   * A handler that process a client's request
   *
   * @param selectionKey
   * @throws IOException
   */
  public GroupChatHandler(SelectionKey selectionKey) throws IOException {

    log.info("A new client comes in");

    this.key = selectionKey;
    this.selector = key.selector();
    this.socketChannel = (SocketChannel) key.channel();
    key.attach(this);
    selector.wakeup();
    this.username = "Client-" + this.hashCode();

    output.clear();
    output.put((this.username + " is online\n").getBytes());
    broadcast();

  }

  @Override
  public void handle() {

    try {

      if (key.isReadable()) {
        read();
      }

      if (key.isValid() && key.isWritable()) {
        send();
      }

    } catch (IOException ioException) {
      ioException.printStackTrace();
    }

  }

  /**
   * Read input data to process
   *
   * @throws IOException
   */
  private void read() throws IOException {
    int len = socketChannel.read(input);
    if (len == -1) {
      // the client is disconnected
      disconnect();
      return;
    }

    if (len > 0) {
      if (inputIsComplete()) {
        input.flip();
        byte[] temp = new byte[input.remaining()];
        input.get(temp);
        input.clear();
        threadpool.execute(new Processer(selector, key, username, temp));
//        new Thread(new Processer(selector, key, username, temp)).start();
      }
    }
  }


  /**
   * Send data to client
   */
  private void send() throws IOException {
    output.clear();
    output.put("~~~~~~~~~~~~\n".getBytes());
    output.flip();
    socketChannel.write(output);
    if (key.isValid()) {
      key.interestOps(SelectionKey.OP_READ);
      selector.wakeup();
    }
  }

  private void broadcast() throws IOException {

    log.info("broadcasting ...");
    for (SelectionKey selectionKey : selector.keys()) {
      if (selectionKey.isValid() && key != selectionKey) {
        SelectableChannel ch = selectionKey.channel();
        if (ch instanceof SocketChannel) {
          SocketChannel channel = (SocketChannel) ch;
          if (channel.isOpen()) {
            output.flip();
            channel.write(output);
          }
        }
      }
    }

    if (key.isValid()) {
      key.interestOps(SelectionKey.OP_READ);
      selector.wakeup();
    }
  }

  private boolean inputIsComplete() {
    return true;
  }

  private void disconnect() throws IOException {

    log.info(username + " is disconnected from server");
    key.cancel();
    socketChannel.close();

    output.clear();
    output.put((username + " is offline\n").getBytes());
    broadcast();

  }

}
