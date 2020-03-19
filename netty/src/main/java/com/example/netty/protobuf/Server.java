package com.example.netty.protobuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yulshi
 * @create 2020/01/20 08:49
 */
@Slf4j
public class Server {

    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("encoder",
                                    new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
                            ch.pipeline().addLast(new ServerHandler());
                        }
                    });
            ChannelFuture channelFuture = b.bind(port).syncUninterruptibly();
            channelFuture.channel().closeFuture().syncUninterruptibly();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    private class ServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

            //if (msg.getDataType() == MyDataInfo.MyMessage.DataType.StudentType) {
            if(msg.hasStudent()) {
                MyDataInfo.Student student = msg.getStudent();
                System.out.println("ID: " + student.getId() + "; name: " + student.getName());
            } else {
                MyDataInfo.Worker worker = msg.getWorker();
                System.out.println("name: " + worker.getName() + "; age: " + worker.getAge());
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server(6666);
        server.start();
    }
}
