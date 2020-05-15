package com.example.nio.selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author yulshi
 * @create 2020/05/04 11:22
 */
@Slf4j
public class EchoServer implements Runnable {

  private static final int bufferSize = 512;

  private final int port;

  private final ServerSocketChannel serverSocketChannel;
  private final Selector selector;
  private final ByteBuffer buff;

  public EchoServer(int port) throws IOException {

    this.port = port;
    this.selector = Selector.open();
    this.serverSocketChannel = ServerSocketChannel.open();
    this.buff = ByteBuffer.allocate(bufferSize);

    serverSocketChannel.configureBlocking(false);
    serverSocketChannel.bind(new InetSocketAddress(port));
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
  }


  @Override
  public void run() {

    while (!Thread.interrupted()) {

      try {

        int selects = selector.select();
        log.info("There are " + selects + " events happened");

        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
        while (keyIterator.hasNext()) {
          SelectionKey key = keyIterator.next();
          keyIterator.remove();

          if (key.isAcceptable()) {
            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = serverChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            log.info("A new client is connected");
          }

          if (key.isReadable()) {

            SocketChannel channel = (SocketChannel) key.channel();

            int len = channel.read(buff);

            if (len == -1) {
              System.out.println("a client is disconnected");
              key.cancel();
              channel.close();
            } else {
              buff.flip();
              channel.write(buff);
              buff.clear();
            }
          }
        }

      } catch (IOException e) {
        e.printStackTrace();
      }

    }

  }

  public static void main(String[] args) throws IOException, InterruptedException {
    Thread boss = new Thread(new EchoServer(7777));
    boss.start();
    boss.join();
  }
}
