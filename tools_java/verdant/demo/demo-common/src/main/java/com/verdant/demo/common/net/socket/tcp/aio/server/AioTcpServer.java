package com.verdant.demo.common.net.socket.tcp.aio.server;

import com.verdant.demo.common.net.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TCP AIO服务端
 *
 * @author verdant
 * @since 2016/06/20
 */
public class AioTcpServer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(AioTcpServer.class);

    private static final Integer NUM_THREADS = 8;

    public CountDownLatch latch;
    private AsynchronousChannelGroup asyncChannelGroup;
    public AsynchronousServerSocketChannel listener;

    public AioTcpServer(int port) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        asyncChannelGroup = AsynchronousChannelGroup.withThreadPool(executor);
        listener = AsynchronousServerSocketChannel.open(asyncChannelGroup)
                .bind(new InetSocketAddress(port));
        logger.info("AioServer listen on port: " + port);
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        listener.accept(this, new AioAcceptHandler());
        try {
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("finished server");
        }
    }

    public static void main(String... args) throws Exception {
        Integer serverPort = Constants.PORT_TCP_AIO_SERVER;
        AioTcpServer server = new AioTcpServer(serverPort);
        new Thread(server).start();
    }
}
