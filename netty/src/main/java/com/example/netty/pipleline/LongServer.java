package com.example.netty.pipleline;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author yulshi
 * @create 2020/01/22 12:07
 */
public class LongServer {

    private final int port;

    public LongServer(int port) {
        this.port = port;
    }

    public void start() {

        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, worker)
                    .channel(NioServerSocketChannel .class)
                    //.handler(new LoggingHandler(LogLevel.INFO) )
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("decoder", new ByteToLongDecoder());
                            ch.pipeline().addLast("encoder", new LongToByteEncoder());
                            ch.pipeline().addLast("biz", new ServerHandler());
                        }
                    });
            ChannelFuture channelFuture = b.bind(port).syncUninterruptibly();
            channelFuture.channel().closeFuture().syncUninterruptibly();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        LongServer server = new LongServer(6666);
        server.start();
    }

    private class ServerHandler extends SimpleChannelInboundHandler<Long> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
            System.out.println("Received: " + msg);
            ctx.writeAndFlush(msg + 666666666);
        }
    }
}
