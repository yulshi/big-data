package com.example.reactor.douglee;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author yulshi
 * @create 2020/05/05 16:21
 */
@Slf4j
public class Handler implements Runnable {

  static final int MAXIN = 512;
  static final int MAXOUT = 512;

  static final int READING = 0;
  static final int SENDING = 1;

  final SocketChannel socketChannel;
  final SelectionKey sk;

  ByteBuffer input = ByteBuffer.allocate(MAXIN);
  ByteBuffer output = ByteBuffer.allocate(MAXOUT);

  int state = READING;

  public Handler(Selector selector, SocketChannel c) throws IOException {
    socketChannel = c;
    socketChannel.configureBlocking(false);
    sk = socketChannel.register(selector, SelectionKey.OP_READ, this);
    selector.wakeup();
  }

  @Override
  public void run() {
    try {
      if (state == READING) {
        read();
      } else if (state == SENDING) {
        send();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void read() throws IOException {
    socketChannel.read(input);
    if (inputIsComplete()) {
      process();
      state = SENDING;
      sk.interestOps(SelectionKey.OP_WRITE);
    }
  }


  void send() throws IOException {
    output.clear();
    output.put("~~~~~\n".getBytes());
    output.flip();
    socketChannel.write(output);
    if (outputIsComplete()) {
      sk.cancel();
    }
  }

  private void process() throws UnsupportedEncodingException {
    input.flip();
    byte[] temp = new byte[input.remaining()];
    input.get(temp);
    log.info("Got: " + new String(temp, "utf-8"));
  }

  private boolean inputIsComplete() {
    return true;
  }

  private boolean outputIsComplete() {
    return true;
  }

}
