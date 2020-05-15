package com.example.nio.zerocopy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author yulshi
 * @create 2020/01/14 12:46
 */
public class FileChannelDemo {

  public static void main(String[] args) {
    copy();
    //write();
    //read();
  }

  private static void copy() {

    try {
      FileChannel srcChannel = FileChannel.open(Paths.get("/Users/yushi/file01.txt"),
              StandardOpenOption.READ);
      FileChannel destChannel = FileChannel.open(Paths.get("/Users/yushi", "file02.txt"),
              StandardOpenOption.CREATE, StandardOpenOption.WRITE);
      srcChannel.transferTo(0, srcChannel.size(), destChannel);
//            ByteBuffer buff = ByteBuffer.allocate(5);
//            int readLen = -1;
//            while ((readLen = srcChannel.read(buff)) != -1) {
//                buff.flip();
//                destChannel.write(buff);
//                buff.clear();
//            }
      srcChannel.close();
      destChannel.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  private static void read() {
    try {
      FileInputStream fileInputStream = new FileInputStream("/Users/yushi/file01.txt");
      FileChannel fileChannel = fileInputStream.getChannel();

      ByteBuffer buff = ByteBuffer.allocate(64);

      int len = -1;
      while ((len = fileChannel.read(buff)) != -1) {
        byte[] data = new byte[len];
        buff.flip();
        buff.get(data);
        System.out.println(new String(data, Charset.forName("utf-8")));
        buff.clear();
      }

      fileInputStream.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void write() {
    try (FileChannel fileChannel = FileChannel.open(Paths.get("/Users/yushi", "file01.txt"),
            StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
      ByteBuffer buff = ByteBuffer.allocate(64);
      buff.put("Hello, 史玉龙".getBytes(Charset.forName("utf-8")));
      buff.flip();
      fileChannel.write(buff);
      System.out.println(buff.position());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
