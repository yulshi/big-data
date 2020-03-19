package com.example.netty.rpc.provider;

import com.example.netty.rpc.common.HelloService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yulshi
 * @create 2020/01/27 11:35
 */
public class NettyServer {

    private final String host;
    private final int port;

    public NettyServer(String host, int port) {
        this.host = host;
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
                            ch.pipeline().addLast("decoder", new StringDecoder());
                            ch.pipeline().addLast("encoder", new StringEncoder());
                            ch.pipeline().addLast(new ServerHandler());
                        }
                    });
            ChannelFuture channelFuture = b.bind(host, port).syncUninterruptibly();
            channelFuture.channel().closeFuture().syncUninterruptibly();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    @Slf4j
    static class ServerHandler extends SimpleChannelInboundHandler<String> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            log.debug("Received: " + msg);
            HelloService service = new HelloServiceImpl();
            String result = service.hello(msg.substring(msg.lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }

    public static void main(String[] args) {
        NettyServer server = new NettyServer("localhost", 6666);
        server.start();
    }
}
