package com.example.netty.pipleline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author yulshi
 * @create 2020/01/22 12:10
 */
@Slf4j
public class ByteToLongDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.debug("Decoding the inbound message: " + in.readableBytes());
        if(in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }

}
