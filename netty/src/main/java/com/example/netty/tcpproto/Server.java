package com.example.netty.tcpproto;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;

/**
 * @author yulshi
 * @create 2020/01/23 20:48
 */
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
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("LengthBased", new LengthFieldBasedFrameDecoder(255, 0, 4));
                            pipeline.addLast("Decoder", new FixedLengthDecoder());
                            pipeline.addLast("Eecoder", new FixedLengthEecoder());
                            pipeline.addLast("handler", new ServerHandler());
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
        Server server = new Server(6666);
        server.start();
    }

    private class ServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
            System.out.println("len: " + msg.getLength() + ", " + new String(msg.getContent(), CharsetUtil.UTF_8));
        }
    }
}
