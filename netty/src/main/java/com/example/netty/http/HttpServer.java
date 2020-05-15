package com.example.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yulshi
 * @create 2020/01/18 10:01
 */
@Slf4j
public class HttpServer {

  private final int port;

  public HttpServer(int port) {
    this.port = port;
  }

  public void start() {

    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
              .channel(NioServerSocketChannel.class)
              .option(ChannelOption.SO_BACKLOG, 128)
              .childOption(ChannelOption.SO_KEEPALIVE, true)
              .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                  ch.pipeline().addLast(new HttpServerCodec());
                  ch.pipeline().addLast(new HttpOutputHandler());
                  //ch.pipeline().addLast(new DefaultEventExecutorGroup(5), null);
                  ch.pipeline().addLast(new HttpProcessor());
                }
              });
      ChannelFuture channelFuture = b.bind(port).syncUninterruptibly();
      log.info("HTTP server is started at the port: " + port);
      channelFuture.channel().closeFuture().syncUninterruptibly();

    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }

  public static void main(String[] args) {
    HttpServer httpServer = new HttpServer(8080);
    httpServer.start();
  }

  private class HttpProcessor extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
      if (msg instanceof HttpRequest) {
        HttpRequest httpRequest = (HttpRequest) msg;
        log.debug("request path: " + httpRequest.uri());
        // Send response
        ByteBuf content = Unpooled.copiedBuffer("Hello World!", CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK, content);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN);
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
        ctx.writeAndFlush(response);
      }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
      log.debug("... read completed ...");
    }
  }

  private class HttpOutputHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      super.write(ctx, msg, promise);
      log.debug("msg's type: " + msg.getClass().getName());
      log.debug("writing response messages: " + msg);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
      super.flush(ctx);
      log.debug("Response data is flushed to client");
    }
  }
}

