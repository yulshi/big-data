package com.example.netty.tcpproto;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @author yulshi
 * @create 2020/01/23 21:05
 */
public class Client {

  public static void main(String[] args) {
    EventLoopGroup group = new NioEventLoopGroup();
    Bootstrap b = new Bootstrap();
    try {
      b.group(group).channel(NioSocketChannel.class)
              .option(ChannelOption.SO_KEEPALIVE, true)
              .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                  ch.pipeline().addLast("Decoder", new FixedLengthDecoder());
                  ch.pipeline().addLast("encoder", new FixedLengthEecoder());
                  ch.pipeline().addLast("handler", new ClientHandler());
                }
              });
      ChannelFuture channelFuture = b.connect("localhost", 6666).syncUninterruptibly();

      channelFuture.channel().closeFuture().syncUninterruptibly();
    } finally {
      group.shutdownGracefully();
    }
  }

  private static class ClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//            for (int i = 0; i < 10; i++) {
//                ctx.writeAndFlush(new MessageProtocol(String.format("Hello,world!%d", i+1).getBytes(CharsetUtil.UTF_8)));
//            }
      MessageProtocol mp = new MessageProtocol("Hello,TCP protocol".getBytes(CharsetUtil.UTF_8));
      mp.setLength(mp.getLength() + 2);
      ctx.writeAndFlush(mp);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

    }
  }
}
