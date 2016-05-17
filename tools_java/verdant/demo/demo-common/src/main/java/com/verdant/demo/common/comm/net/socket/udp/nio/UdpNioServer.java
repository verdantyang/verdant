package com.verdant.demo.common.comm.net.socket.udp.nio;

import com.verdant.demo.common.comm.net.socket.utils.ClientMessage;
import com.verdant.demo.common.comm.net.socket.utils.SocketUtils2;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * Author: verdant
 * Desc:   UDP NIO服务端
 */
public class UdpNioServer {
    private static final String HOST = "127.0.0.1";
    private static final Integer PORT_SERVER = 7001;
    private static final Integer TIME_OUT = 1000;

    private volatile boolean flag = true;

    private DatagramSocket receiveSocket;
    private DatagramChannel receiveChannel;
    private Selector selector;

    public UdpNioServer() throws IOException {
        selector = Selector.open();
        receiveChannel = DatagramChannel.open();
        receiveChannel.configureBlocking(false);
        receiveChannel.register(selector, SelectionKey.OP_READ);

        receiveSocket = receiveChannel.socket();
        receiveSocket.bind(new InetSocketAddress(HOST, PORT_SERVER));

        System.out.println("Server listen on port: " + PORT_SERVER);

        while (flag) {
            int nKeys = selector.select(TIME_OUT);
            //nKeys>0 说明有IO事件发生
            if (nKeys > 0) {
                System.out.println("Get new channel!");
                for (SelectionKey key : selector.selectedKeys()) {
                    if (key.isReadable()) {      //有流可读取
                        DatagramChannel sc = (DatagramChannel) key.channel();
                        ClientMessage cm = SocketUtils2.readFromChannel(sc);
                        System.out.println(cm.getMessage());

                        ByteBuffer bufOut = ByteBuffer.allocate(65507);
                        bufOut.clear();
                        bufOut.put(cm.getMessage().getBytes());
                        bufOut.flip();
                        sc.send(bufOut, new InetSocketAddress(cm.getHost(), cm.getPort()));
                    }
                    selector.selectedKeys().clear();
                }
            }
        }
        receiveSocket.close();
        receiveChannel.close();
        selector.close();
        System.exit(0);
    }

    public static void main(String[] args) {
        try {
            new UdpNioServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
