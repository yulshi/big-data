package com.example.reactor.singlethread;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
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
        process();
        key.interestOps(SelectionKey.OP_WRITE);
        selector.wakeup();
      }
    }
  }

  /**
   * Process the received data
   *
   * decode -> process -> encode
   */
  private void process() throws IOException {

    // decode
    byte[] temp = new byte[input.remaining()];
    input.get(temp);
    String message = new String(temp, "utf-8");
    input.clear();
    log.info("[RECEIVED] " + message);

    // process
    String retMsg = String.format("[%s] %s", username, message);

    // encode
    output.clear();
    output.put(retMsg.getBytes("utf-8"));

    broadcast();
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
    input.flip();
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
