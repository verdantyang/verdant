package com.verdant.demo.common.comm.net.socket.tcp.aio.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AioAcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> {
    private static final Logger log = LoggerFactory.getLogger(AioAcceptHandler.class);

    public void cancelled(AsynchronousServerSocketChannel attachment) {
        log.info("cancelled");
    }

    @Override
    public void completed(AsynchronousSocketChannel socket, AsynchronousServerSocketChannel attachment) {
        try {
            attachment.accept(attachment, this);
            log.info("有客户端连接:" + socket.getRemoteAddress().toString());
            startRead(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, AsynchronousServerSocketChannel attachment) {
        exc.printStackTrace();
    }

    public void startRead(AsynchronousSocketChannel socket) {
        ByteBuffer clientBuffer = ByteBuffer.allocate(1024);
        socket.read(clientBuffer, clientBuffer, new AioReadHandler(socket));
    }
}
