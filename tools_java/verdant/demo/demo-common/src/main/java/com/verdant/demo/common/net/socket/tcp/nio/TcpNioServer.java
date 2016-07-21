package com.verdant.demo.common.net.socket.tcp.nio;

import com.verdant.demo.common.net.Constants;
import com.verdant.demo.common.net.socket.utils.SocketUtils2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * TCP NIO服务端
 *
 * @author verdant
 * @since 2016/06/20
 */
public class TcpNioServer {
    private static final Logger logger = LoggerFactory.getLogger(TcpNioServer.class);

    private static final Integer TIME_OUT = 1000;

    private volatile boolean flag = true;

    private Selector selector;
    private ServerSocketChannel channel;

    public TcpNioServer(int port) throws IOException {
        selector = Selector.open();

        channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_ACCEPT);
        channel.socket().bind(new InetSocketAddress(port));

        logger.info("Server listen on port: " + port);

        while (flag) {
            int nKeys = selector.select(TIME_OUT);
            //nKeys>0 说明有IO事件发生
            if (nKeys > 0) {
                SocketChannel sc = null;
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
                        if (Constants.COMMAND_SHUTDOWN.equalsIgnoreCase(message.trim())) {
                            flag = false;
                            sc.close();
                            logger.info("Server shutdown!");
                            break;
                        }
                        String outMessage = "Get message (" + message + ")";
                        logger.info(outMessage);
                        if (message.length() > 0)
                            sc.write(Charset.forName("UTF-8").encode(outMessage));
                    }
                    selector.selectedKeys().clear();
                }
            }
        }
        channel.close();
        selector.close();
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        try {
            Integer serverPort = Constants.PORT_TCP_NIO_SERVER;
            new TcpNioServer(serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
