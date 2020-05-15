package com.example.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yulshi
 * @create 2020/01/18 16:46
 */
@Slf4j
public class ByteBufDemo {

  public static void main(String[] args) {
    ByteBuf buf = Unpooled.copiedBuffer("Hello,Netty", CharsetUtil.UTF_8);

    buf.writeBytes("a long sentence that is made up".getBytes());

    int index = buf.forEachByte(ByteProcessor.FIND_COMMA);
    System.out.println(index);

  }
}
