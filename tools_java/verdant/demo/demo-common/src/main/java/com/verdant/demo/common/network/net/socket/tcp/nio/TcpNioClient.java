package com.verdant.demo.common.network.net.socket.tcp.nio;

import com.verdant.demo.common.network.net.socket.utils.SocketUtils2;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Author: verdant
 * Desc:   TCP NIO客户端
 */
public class TcpNioClient {
    private static final String HOST = "127.0.0.1";
    private static final Integer PORT_SERVER = 7889;
    private static final String END = "quit";
    private static final Integer TIME_OUT = 1000;

    private volatile boolean flag = true;

    private SocketChannel channel;
    private Selector selector;

    public TcpNioClient() throws IOException {
        selector = Selector.open();
        channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_CONNECT);

        channel.connect(new InetSocketAddress(HOST, PORT_SERVER));

        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));

        while (flag) {
            if (channel.isConnected()) {
                System.out.println("Please enter the send message:");
                String clientSay = systemIn.readLine();
                if (StringUtils.isEmpty(clientSay)) {
                    continue;
                } else if (END.equalsIgnoreCase(clientSay)) {
                    flag = false;
                    break;
                }
                channel.write(Charset.forName("UTF-8").encode(clientSay));
            }

            int nKeys = selector.select(TIME_OUT);
            if (nKeys > 0) {
                for (SelectionKey key : selector.selectedKeys()) {
                    if (key.isConnectable()) {      //发生连接的事件
                        SocketChannel sc = (SocketChannel) key.channel();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                        sc.finishConnect();
                    } else if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        System.out.println("Server reply：" + SocketUtils2.readFromChannel(sc));
                    }
                }
                selector.selectedKeys().clear();
            }
        }
        System.out.println("Client quit !");
        systemIn.close();
        channel.close();
        selector.close();
        System.exit(0);
    }


    public static void main(String[] args) throws IOException {
        try {
            new TcpNioClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
