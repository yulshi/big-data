package com.example.netty.rpc.consumer;

import com.example.netty.rpc.common.HelloService;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yulshi
 * @create 2020/01/27 11:45
 */
@Slf4j
public class ClientBootstrap {

    private static ExecutorService threadPool = new ThreadPoolExecutor(
            4, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

    public static Object getBean(Class<?> clazz) {

        Object proxyInstance = Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{clazz}, (proxy, method, args) -> {
                    String sendingMessage = method.getName() + "#" + args[0];
                    log.debug("Sending message: " + sendingMessage);
                    NettyClient client = new NettyClient("localhost", 6666);
                    ClientHanlder handler = new ClientHanlder();
                    handler.setParam(sendingMessage);
                    client.connect(handler);

                    String result = threadPool.submit(handler).get();
                    return result;
                }
        );
        return proxyInstance;
    }

    public static void main(String[] args) {

        HelloService service = (HelloService) getBean(HelloService.class);
        System.out.println(service.hello("Jimmmmmy"));

    }
}
