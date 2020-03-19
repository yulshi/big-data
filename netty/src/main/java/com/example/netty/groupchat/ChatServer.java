package com.example.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yulshi
 * @create 2020/01/18 18:29
 */
@Slf4j
public class ChatServer {

    private final int port;

    public ChatServer(int port) {
        this.port = port;
    }

    private void start() {
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
                            ch.pipeline().addLast("decoder", new StringDecoder());
                            ch.pipeline().addLast("encoder", new StringEncoder());
                            ch.pipeline().addLast("heartbeat", new IdleStateHandler(10, 5, 0));
                            ch.pipeline().addLast(new ChatHandler());
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
        ChatServer chatServer = new ChatServer(6666);
        chatServer.start();
    }

    private static class ChatHandler extends SimpleChannelInboundHandler<String> {

        private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                // If no outbound traffic for 5 seconds, a ping message is sent
                // If no read operation for 10 secondes, the connection will be closed.
                IdleStateEvent event = (IdleStateEvent) evt;
                if (event.state() == IdleState.WRITER_IDLE) {
                    ctx.writeAndFlush("are you alive?");
                } else if (event.state() == IdleState.READER_IDLE) {
                    ctx.close();
                }
            }
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            Channel channel = ctx.channel();
            channels.writeAndFlush("[Client] " + channel.remoteAddress() + " entered the room\n");
            channels.add(channel);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("A new user is online: " + ctx.channel().remoteAddress());
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("User '" + ctx.channel().remoteAddress() + "' is offline");
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            channels.writeAndFlush("[client] " + ctx.channel().remoteAddress() + " left the room\n");
            log.debug("current client count: " + channels.size());
        }

        @Override
        public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            log.debug("Received: " + msg);
            channels.stream().filter(ch -> ch != ctx.channel()).forEach(ch->{
                ch.writeAndFlush(ctx.channel().remoteAddress() + " said: " + msg);
            });
        }
    }
}
