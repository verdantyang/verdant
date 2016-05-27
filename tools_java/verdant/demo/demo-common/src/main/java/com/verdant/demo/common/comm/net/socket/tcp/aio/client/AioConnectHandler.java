package com.verdant.demo.common.comm.net.socket.tcp.aio.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

public class AioConnectHandler implements CompletionHandler<Void, AsynchronousSocketChannel> {
    private static final Logger log = LoggerFactory.getLogger(AioConnectHandler.class);
    private String content = "test";

    public AioConnectHandler(String value) {
        this.content = value;
    }

    @Override
    public void completed(Void attachment, AsynchronousSocketChannel connector) {
        File
        try {
            log.info("建立连接:" + connector.getLocalAddress().toString());
            connector.write(ByteBuffer.wrap(content.getBytes())).get();
            startRead(connector);
        } catch (ExecutionException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
        exc.printStackTrace();
    }

    public void startRead(AsynchronousSocketChannel socket) {
        ByteBuffer clientBuffer = ByteBuffer.allocate(1024);
        socket.read(clientBuffer, clientBuffer, new AioReadHandler(socket));
    }
}
