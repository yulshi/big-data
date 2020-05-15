package com.example.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author yulshi
 * @create 2020/01/22 12:14
 */
public class LongToByteEncoder extends MessageToByteEncoder<Long> {

  @Override
  protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
    out.writeLong(msg);
  }
}
