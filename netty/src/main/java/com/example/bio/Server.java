package com.example.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 @author yulshi
 @create 2020/05/03 17:04
 */
@Slf4j
public class Server implements Runnable {

  private final int port;
  private final ServerSocket serverSocket;

  private final ExecutorService threadPool = new ThreadPoolExecutor(
          1, 5, 0, TimeUnit.SECONDS,
          new LinkedBlockingQueue<>(2));

  public Server(int port) throws IOException {
    this.port = port;
    this.serverSocket = new ServerSocket(port);
  }

  @Override
  public void run() {

    log.info("Server is started at " + port);

    while (!Thread.interrupted()) {

      try {
        log.info("preparing to accept a new connection");
        Socket socket = serverSocket.accept();

        log.info("a new client is connected");
        Handler handler = new Handler(socket);
        threadPool.execute(handler);

      } catch (IOException e) {
        e.printStackTrace();
      }

    }

  }

  public static class Handler implements Runnable, Closeable {

    private static final int bufferSize = 512;

    private final Socket socket;
    private final InputStream in;
    private final OutputStream out;

    public Handler(Socket socket) throws IOException {
      this.socket = socket;
      this.in = socket.getInputStream();
      this.out = socket.getOutputStream();
    }

    @Override
    public void run() {

      log.info("start reading data from client...");
      try {

        byte[] buff = new byte[bufferSize];
        int len;
        while ((len = in.read(buff)) != -1) {
          log.info("writing data to the client...");
          out.write(buff, 0, len);
          out.flush();
        }

      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

    }

    @Override
    public void close() throws IOException {
      log.info("closing client connection");
      if (in != null) {
        in.close();
      }
      if (out != null) {
        out.close();
      }
      socket.close();
    }
  }

  public static void main(String[] args) throws IOException, InterruptedException {

    Server server = new Server(7777);
    Thread thread = new Thread(server);
    thread.start();

    thread.join();

  }

}
