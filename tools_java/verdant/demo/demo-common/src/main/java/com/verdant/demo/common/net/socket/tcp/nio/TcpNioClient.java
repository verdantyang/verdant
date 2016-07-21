package com.verdant.demo.common.net.socket.tcp.nio;

import com.verdant.demo.common.net.Constants;
import com.verdant.demo.common.net.socket.utils.SocketUtils2;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * TCP NIO客户端
 *
 * @author verdant
 * @since 2016/06/20
 */
public class TcpNioClient {
    private static final Logger logger = LoggerFactory.getLogger(TcpNioClient.class);

    private static final Integer TIME_OUT = 1000;
    private volatile boolean flag = true;

    private Selector selector;
    private SocketChannel channel;

    public TcpNioClient(String host, int port) throws IOException {
        selector = Selector.open();

        channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_CONNECT);
        channel.connect(new InetSocketAddress(host, port));

        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));

        while (flag) {
            if (channel.isConnected()) {
                logger.info("Please enter the send message:");
                String clientSay = systemIn.readLine();
                if (StringUtils.isEmpty(clientSay)) {
                    continue;
                } else if (Constants.COMMAND_END.equalsIgnoreCase(clientSay)) {
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
                        logger.info("Server reply：" + SocketUtils2.readFromChannel(sc));
                    }
                }
                selector.selectedKeys().clear();
            }
        }
        logger.info("Client quit !");
        systemIn.close();
        channel.close();
        selector.close();
    }


    public static void main(String[] args) throws IOException {
        try {
            String serverHost = System.getProperty("host", "127.0.0.1");
            Integer serverPort = Constants.PORT_TCP_NIO_SERVER;
            new TcpNioClient(serverHost, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
