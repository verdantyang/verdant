package com.verdant.demo.common.socket.tcp.nio;

import com.verdant.demo.common.socket.SocketUtils2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Author: verdant
 * Func:   NIO服务端
 */
public class NioServer {
    private static final Integer PORT = 7889;
    private static final Integer TIME_OUT = 60;
    private static final String SHUTDOWN = "shutdown";

    private volatile boolean flag = true;

    private ServerSocket serverSocket;
    private ServerSocketChannel channel;
    private Selector selector;

    public NioServer() throws IOException {
        selector = Selector.open();
        channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_ACCEPT);

        serverSocket = channel.socket();
        serverSocket.bind(new InetSocketAddress(PORT));

        System.out.println("Server listen on port: " + PORT);

        while (flag) {
            int nKeys = selector.select(TIME_OUT);
            SocketChannel sc = null;
            //nKeys>0 说明有IO事件发生
            if (nKeys > 0) {
                for (SelectionKey key : selector.selectedKeys()) {
                    if (key.isAcceptable()) {      //发生连接的事件
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        sc = server.accept();
                        if (sc == null) {
                            continue;
                        }
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {      //有流可读取
                        sc = (SocketChannel) key.channel();
                        String message = SocketUtils2.readFromChannel(sc);
                        if (SHUTDOWN.equalsIgnoreCase(message.trim())) {
                            flag = false;
                            sc.close();
                            System.out.println("Server shutdown!");
                            break;
                        }
                        String outMessage = "Get message (" + SocketUtils2.readFromChannel(sc) + ")";
                        System.out.println(outMessage);
                        if (message.length() > 0)
                            sc.write(Charset.forName("UTF-8").encode(outMessage));
                    }
                    selector.selectedKeys().clear();
                }
            }
        }
        selector.close();
        System.exit(0);
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        try {
            new NioServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
