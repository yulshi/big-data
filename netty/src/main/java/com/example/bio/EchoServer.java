package com.example.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yulshi
 * @create 2020/01/13 22:18
 */
public class EchoServer {

  private final Executor threadPool = new ThreadPoolExecutor(5,
          20, 30, TimeUnit.SECONDS,
          new LinkedBlockingQueue<>(20));

  public void start() throws IOException {
    ServerSocket serverSocket = new ServerSocket(6666);
    while (true) {
      log("waiting for client...");
      final Socket socket = serverSocket.accept();
      log("A client connection comes in");
      threadPool.execute(() -> {
        echo(socket);
      });
    }

  }

  public void echo(Socket socket) {
    try {
      InputStream inputStream = socket.getInputStream();
      PrintWriter out = new PrintWriter(socket.getOutputStream());
      byte[] buff = new byte[1024];
      while (true) {
        log("waiting for data to be read...");
        int len = inputStream.read(buff);
        if (len == -1) {
          break;
        }
        out.println("<< " + new String(buff, 0, len));
        out.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  public static void main(String[] args) throws IOException {
    new EchoServer().start();

  }

  public static void log(String msg) {
    System.out.println(Thread.currentThread().getName() + ": " + msg);
  }
}
