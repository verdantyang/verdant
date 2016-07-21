package com.verdant.demo.common.net.socket.udp.nio;

import com.verdant.demo.common.net.Constants;
import com.verdant.demo.common.net.socket.utils.ClientMessage;
import com.verdant.demo.common.net.socket.utils.SocketUtils2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * UDP NIO服务端
 *
 * @author verdant
 * @since 2016/06/20
 */
public class UdpNioServer {
    private static final Logger logger = LoggerFactory.getLogger(UdpNioServer.class);

    private static final Integer TIME_OUT = 1000;

    private volatile boolean flag = true;

    private DatagramSocket receiveSocket;
    private DatagramChannel receiveChannel;
    private Selector selector;

    public UdpNioServer(int port) throws IOException {
        selector = Selector.open();
        receiveChannel = DatagramChannel.open();
        receiveChannel.configureBlocking(false);
        receiveChannel.register(selector, SelectionKey.OP_READ);

        receiveSocket = receiveChannel.socket();
        receiveSocket.bind(new InetSocketAddress(port));

        logger.info("Server listen on port: " + port);

        while (flag) {
            int nKeys = selector.select(TIME_OUT);
            //nKeys>0 说明有IO事件发生
            if (nKeys > 0) {
                logger.info("Get new channel!");
                for (SelectionKey key : selector.selectedKeys()) {
                    if (key.isReadable()) {      //有流可读取
                        DatagramChannel sc = (DatagramChannel) key.channel();
                        ClientMessage cm = SocketUtils2.readFromChannel(sc);
                        logger.info(cm.getMessage());

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
    }

    public static void main(String[] args) {
        try {
            Integer port = Constants.PORT_UDP_NIO_SERVER;
            new UdpNioServer(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
