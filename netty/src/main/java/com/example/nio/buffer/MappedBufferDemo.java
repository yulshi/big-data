package com.example.nio.buffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author yulshi
 * @create 2020/05/04 09:19
 */
public class MappedBufferDemo {

  public static void main(String[] args) throws IOException {

    RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/yushi/file01.txt", "rw");

    FileChannel channel = randomAccessFile.getChannel();

    MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 6);

    // Read the mapped content
    byte[] temp = new byte[5];
    mappedByteBuffer.get(temp);
    System.out.println(new String(temp));

    // update the mapped content;
    mappedByteBuffer.flip();
    mappedByteBuffer.put("Hello".getBytes("utf-8"));

    randomAccessFile.close();

  }

}
