package com.example.netty.rpc.consumer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author yulshi
 * @create 2020/01/27 11:50
 */
public class NettyClient {

  private final String host;
  private final int port;

  public NettyClient(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public void connect(ClientHanlder hanlder) {
    EventLoopGroup group = new NioEventLoopGroup();
    Bootstrap b = new Bootstrap();

    b.group(group).channel(NioSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY, true)
            .handler(new ChannelInitializer<SocketChannel>() {
                       @Override
                       protected void initChannel(SocketChannel ch) throws Exception {
                         ch.pipeline().addLast(new StringDecoder());
                         ch.pipeline().addLast(new StringEncoder());
                         ch.pipeline().addLast(hanlder);
                       }
                     }
            );
    b.connect(host, port).syncUninterruptibly();

  }
}
