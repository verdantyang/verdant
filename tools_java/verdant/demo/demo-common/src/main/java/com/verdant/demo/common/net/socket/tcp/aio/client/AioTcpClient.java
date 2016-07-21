package com.verdant.demo.common.net.socket.tcp.aio.client;

import com.verdant.demo.common.net.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TCP AIO客户端
 *
 * @author verdant
 * @since 2016/06/20
 */
public class AioTcpClient {
    private static final Logger logger = LoggerFactory.getLogger(AioTcpClient.class);

    private static final Integer NUM_THREADS = 20;
    private static final Integer NUM_CONNECT = 200;

    private AsynchronousChannelGroup asyncChannelGroup;

    public AioTcpClient() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        asyncChannelGroup = AsynchronousChannelGroup.withThreadPool(executor);
    }

    public void start(final String ip, final int port) throws IOException {
        for (int i = 0; i < NUM_CONNECT; i++) {
            try {
                AsynchronousSocketChannel connector = null;
                if (connector == null || !connector.isOpen()) {
                    connector = AsynchronousSocketChannel.open(asyncChannelGroup);
                    connector.setOption(StandardSocketOptions.TCP_NODELAY, true);
                    connector.setOption(StandardSocketOptions.SO_REUSEADDR, true);
                    connector.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
                    connector.connect(new InetSocketAddress(ip, port), connector,
                            new AioConnectHandler(String.valueOf(i)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String... args) throws Exception {
        String serverHost = System.getProperty("host", "127.0.0.1");
        Integer serverPort = Constants.PORT_TCP_AIO_SERVER;
        AioTcpClient client = new AioTcpClient();
        client.start(serverHost, serverPort);
    }
}