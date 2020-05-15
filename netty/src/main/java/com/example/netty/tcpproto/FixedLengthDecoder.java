package com.example.netty.tcpproto;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author yulshi
 * @create 2020/01/23 20:51
 */
//public class FixedLengthDecoder extends ByteToMessageDecoder {
public class FixedLengthDecoder extends ReplayingDecoder<Void> {
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//        if (in.readableBytes() < 4) {
//            return;
//        }
//
//        in.markReaderIndex();
//        int length = in.readInt();
//        in.resetReaderIndex();
//
//        int totalLength = length + 4;
//        if (in.readableBytes() < totalLength) {
//            return;
//        }
//
    MessageProtocol mp = new MessageProtocol();
    int length = in.readInt();
    mp.setLength(length);
    byte[] content = new byte[length];
    in.readBytes(content);
    mp.setContent(content);

    out.add(mp);
  }
}
