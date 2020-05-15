package com.example.netty.protobuf;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author yulshi
 * @create 2020/01/20 09:20
 */
@Slf4j
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
                  ch.pipeline().addLast("encoder", new ProtobufEncoder());
                  ch.pipeline().addLast(new ClientHandler());
                }
              });
      ChannelFuture channelFuture = b.connect("localhost", 6666).syncUninterruptibly();

      channelFuture.channel().closeFuture().syncUninterruptibly();
    } finally {
      group.shutdownGracefully();
    }
  }

  private static class ClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

      int random = new Random().nextInt(3);
      if (random == 0) {
        log.debug("sending student info ...");
        MyDataInfo.MyMessage jimmy = MyDataInfo.MyMessage.newBuilder()
                .setDataType(MyDataInfo.MyMessage.DataType.StudentType)
                .setStudent(MyDataInfo.Student.newBuilder().setId(2)
                        .setName("Jimmy").build()).build();
        ctx.writeAndFlush(jimmy);
      } else {
        log.debug("sending worker info ...");
        MyDataInfo.MyMessage yulong = MyDataInfo.MyMessage.newBuilder()
                .setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
                .setWorker(MyDataInfo.Worker.newBuilder()
                        .setAge(30).setName("Yulong").build()).build();
        ctx.writeAndFlush(yulong);
      }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }
  }
}
