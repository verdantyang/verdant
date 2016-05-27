package com.verdant.demo.common.comm.net.socket.tcp.aio.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AioTcpServer implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(AioTcpServer.class);

    private static final Integer NUM_THREADS = 8;
    private AsynchronousChannelGroup asyncChannelGroup;
    private AsynchronousServerSocketChannel listener;

    public AioTcpServer(int port) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        asyncChannelGroup = AsynchronousChannelGroup.withThreadPool(executor);
        listener = AsynchronousServerSocketChannel.open(asyncChannelGroup).bind(new InetSocketAddress(port));
    }

    @Override
    public void run() {
        try {
            listener.accept(listener, new AioAcceptHandler());
            Thread.sleep(400000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("finished server");
        }
    }

    public static void main(String... args) throws Exception {
        AioTcpServer server = new AioTcpServer(9008);
        new Thread(server).start();
    }
}
