package com.example.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * @author yulshi
 * @create 2020/01/19 10:57
 */
@Slf4j
public class ChatClient {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        try {
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast(new ChatHandler());
                        }
                    });
            ChannelFuture channelFuture = b.connect("127.0.0.1", 6666).syncUninterruptibly();

            Channel channel = channelFuture.channel();

            // The scanner should be running on the main thread
            Scanner scanner = new Scanner(System.in, "utf-8");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                channel.writeAndFlush(line + "\n");
                log.info("Sent message: " + line);
            }
            channelFuture.channel().closeFuture().syncUninterruptibly();
        } finally {
            group.shutdownGracefully();
        }
    }

    private static class ChatHandler extends SimpleChannelInboundHandler<String> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            log.debug("Received: " + msg);
            System.out.println(msg);
        }
    }
}
