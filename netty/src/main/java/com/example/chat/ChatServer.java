package com.example.chat;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author yulshi
 * @create 2020/01/15 11:07
 */
@Slf4j
public class ChatServer {

    public static void main(String[] args) throws IOException {
        ChatServer chatServer = new ChatServer();
        chatServer.start();
    }

    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

    public ChatServer() throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open()
                .bind(new InetSocketAddress(6666));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void start() throws IOException {

        while (true) {

            int eventsCount = selector.select();
            log.trace("Events get fired...");

            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();
                keyIterator.remove();

                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    String username = "Anonymous" + socketChannel.hashCode();
                    log.debug(username + " is online");
                    broadcast("system", (username + " is online").getBytes());
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ, username);
                }

                if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    String user = (String) selectionKey.attachment();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len = socketChannel.read(buffer);
                    if(len == -1) {
                        log.debug(user + " left");
                        selectionKey.cancel();
                        socketChannel.close();
                        broadcast("system", (user + " left").getBytes());
                    } else {
                        buffer.flip();
                        byte[] data = new byte[len];
                        buffer.get(data);
                        //broadcast
                        broadcast(user, data);
                    }
                }
            }

        }
    }

    private void broadcast(String speaker, byte[] data) throws IOException {

        for (SelectionKey key : selector.keys()) {
            if (key.attachment() != null) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                if (socketChannel.isOpen()) {
                    String username = (String) key.attachment();
                    if(!username.equals(speaker)) {
                        socketChannel.write(Arrays.asList(
                                ByteBuffer.wrap(("[" + speaker + "] ").getBytes("utf-8")),
                                ByteBuffer.wrap(data)).toArray(new ByteBuffer[0]));
                    }
                }
            }
        }

    }
}
