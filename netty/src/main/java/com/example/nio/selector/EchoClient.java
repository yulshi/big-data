package com.example.nio.selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author yulshi
 * @create 2020/05/04 17:12
 */
@Slf4j
public class EchoClient implements Runnable {

  private static final int bufferSize = 512;

  private final String host;
  private final int port;

  private final SocketChannel channel;
  private final Selector selector;
  private final ByteBuffer buff;

  public EchoClient(String host, int port) throws IOException {
    this.host = host;
    this.port = port;
    this.selector = Selector.open();
    this.channel = SocketChannel.open();
    this.buff = ByteBuffer.allocate(bufferSize);
  }


  @Override
  public void run() {

    try {
      channel.configureBlocking(false);
      channel.connect(new InetSocketAddress(host, port));
      channel.register(selector, SelectionKey.OP_CONNECT);

      // connected
      while (!Thread.interrupted()) {

        int selects = selector.selectNow();

        Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
        while (keyIterator.hasNext()) {

          SelectionKey selectionKey = keyIterator.next();
          keyIterator.remove();

          if (selectionKey.isConnectable()) {
            while (!channel.finishConnect()) {
              System.out.println("Connecting ...");
            }
            System.out.println("Connected");
            buff.put(("Hello from " + this).getBytes("utf-8"));
            channel.write(buff);
            selectionKey.interestOps(SelectionKey.OP_READ);
          }

          if (selectionKey.isReadable()) {
            buff.clear();
            channel.read(buff);
            buff.flip();
            byte[] temp = new byte[buff.remaining()];
            buff.get(temp);
            System.out.println("[echo] " + new String(temp));
            selectionKey.interestOps(SelectionKey.OP_WRITE);
          }

          if (selectionKey.isWritable()) {
            buff.clear();
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextLine()) {
              String line = scanner.nextLine();
              buff.clear();
              buff.put(line.getBytes("utf-8"));
              buff.flip();
              channel.write(buff);
            }
            selectionKey.interestOps(SelectionKey.OP_READ);
          }

        }
      }

    } catch (IOException e) {
      log.error("Error occurred while connecting to the server at " + host + ":" + port);
      e.printStackTrace();
    }

  }

  public static void main(String[] args) throws IOException, InterruptedException {

    Thread client = new Thread(new EchoClient("localhost", 7777));

    client.start();
    client.join();

  }

}
