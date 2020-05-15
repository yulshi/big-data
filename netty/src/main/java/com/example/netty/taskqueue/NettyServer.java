package com.example.netty.taskqueue;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author yulshi
 * @create 2020/01/17 11:06
 */
@Slf4j
public class NettyServer {
  public static void main(String[] args) {

    EventExecutorGroup executorGroup = new DefaultEventExecutorGroup(5);

    EventLoopGroup boss = new NioEventLoopGroup(1);
    EventLoopGroup worker = new NioEventLoopGroup(3);

    ServerBootstrap bootstrap = new ServerBootstrap();
    bootstrap.group(boss, worker)
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 128)
            .childOption(ChannelOption.SO_KEEPALIVE, true)
            .childHandler(new ChannelInitializer<SocketChannel>() {
              @Override
              protected void initChannel(SocketChannel ch) throws Exception {
                //ch.pipeline().addLast(new ServerHandler());
                ch.pipeline().addLast(executorGroup, new ServerHandler());
              }
            });

    ChannelFuture channelFuture = bootstrap.bind(6666).syncUninterruptibly();
    channelFuture.addListener(f -> {
      if (f.isSuccess()) {
        log.info("Binding succeed!!!");
      } else {
        log.error("Binding failed due to " + f.cause());
      }
    });


    channelFuture.channel().closeFuture().syncUninterruptibly();

  }

  private static class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      ByteBuf buf = (ByteBuf) msg;
      log.debug("a time-consuming operation gets started...");
      //ctx.channel().eventLoop().execute(()->{
      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      String info = "message in a task queue";
      log.debug(info);
      ctx.writeAndFlush(Unpooled.copiedBuffer(info.getBytes(CharsetUtil.UTF_8)));
      //});
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
      log.debug("channel read complete");
      ctx.writeAndFlush(Unpooled.copiedBuffer("Hello from Server\n".getBytes(CharsetUtil.UTF_8)));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      cause.printStackTrace();
      ctx.close();
    }
  }
}
