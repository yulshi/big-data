package com.example.netty.rpc.consumer;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yulshi
 * @create 2020/01/27 11:52
 */
@Slf4j
public class ClientHanlder extends SimpleChannelInboundHandler<String> implements Callable<String> {

  private ChannelHandlerContext context;
  private String param = null;
  private String result;

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    this.context = ctx;
  }

  @Override
  protected synchronized void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
    result = msg;
    notify();
  }

  public void setParam(String param) {
    this.param = param;
  }

  @Override
  public synchronized String call() throws Exception {
    log.debug("sending ...");
    context.writeAndFlush(param);
    wait();
    return result;
  }
}
