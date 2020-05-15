package com.example.netty.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yulshi
 * @create 2020/01/22 13:44
 */
@Slf4j
public class LongClient {

  public static void main(String[] args) {
    EventLoopGroup group = new NioEventLoopGroup();
    Bootstrap b = new Bootstrap();
    try {
      b.group(group).channel(NioSocketChannel.class)
              .option(ChannelOption.SO_KEEPALIVE, true)
              .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                  ch.pipeline().addLast("decoder", new ByteToLongDecoder());
                  ch.pipeline().addLast("encoder", new LongToByteEncoder());
                  ch.pipeline().addLast(new ClientHandler());
                }
              });
      ChannelFuture channelFuture = b.connect("localhost", 6666).syncUninterruptibly();

      channelFuture.channel().closeFuture().syncUninterruptibly();
    } finally {
      group.shutdownGracefully();
    }
  }

  private static class ClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      log.debug("Sending data with type Long ...");
//            ctx.writeAndFlush(123456L);
//            ctx.writeAndFlush(9878765L);
      ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabcdabcdabcdab", CharsetUtil.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
      System.out.println("Received: " + msg);
    }
  }
}
