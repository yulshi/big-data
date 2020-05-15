package com.example.nio.buffer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * @author yulshi
 * @create 2020/05/04 08:55
 */
public class ReadonlyBuffer {

  public static void main(String[] args) throws UnsupportedEncodingException {

    ByteBuffer buffer = ByteBuffer.allocate(64);

    buffer.putInt(Integer.MAX_VALUE).put("Hello".getBytes("utf-8"));

    // make the buffer readonly
    ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
    readOnlyBuffer.flip();

    // read data from it
    int num = readOnlyBuffer.getInt();
    System.out.println(num);
    byte[] temp = new byte[readOnlyBuffer.remaining()];
    readOnlyBuffer.get(temp);
    System.out.println(new String(temp));

    // put data to readonly buffer will cause error
    readOnlyBuffer.put(" World!".getBytes("utf-8"));

  }

}
