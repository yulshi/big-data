package com.example.gathering;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yulshi
 * @create 2020/01/14 15:43
 */
public class GatheringAndScatteringDemo {

  public static void main(String[] args) throws IOException {

    final ExecutorService threadPool = Executors.newCachedThreadPool();

    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), 7000);
    serverSocketChannel.bind(address);

    while (true) {
      System.out.println("Waiting for new client to connect...");
      final SocketChannel socketChannel = serverSocketChannel.accept();
      threadPool.execute(() -> {
        echo(socketChannel);
      });
    }

  }

  private static void echo(SocketChannel socketChannel) {

    ByteBuffer[] buffers = new ByteBuffer[2];
    buffers[0] = ByteBuffer.allocate(5);
    buffers[1] = ByteBuffer.allocate(3);

    try {
      while (true) {
        // read
        long readLength = 0;

        while (readLength < 8) {
          readLength += socketChannel.read(buffers);
        }
        // flip
        Arrays.asList(buffers).stream().forEach(ByteBuffer::flip);
        // write
        long writeLenth = 0;
        while (writeLenth < 8) {
          writeLenth += socketChannel.write(buffers);
        }
        Arrays.asList(buffers).stream().forEach(ByteBuffer::clear);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
