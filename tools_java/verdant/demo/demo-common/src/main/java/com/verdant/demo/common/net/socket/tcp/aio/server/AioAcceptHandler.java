package com.verdant.demo.common.net.socket.tcp.aio.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * TCP AIO服务端连接处理器
 *
 * @author verdant
 * @since 2016/06/20
 */
public class AioAcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AioTcpServer> {
    private static final Logger logger = LoggerFactory.getLogger(AioAcceptHandler.class);

    @Override
    public void completed(AsynchronousSocketChannel socket, AioTcpServer attachment) {
        try {
            attachment.listener.accept(attachment, this);
            logger.info("A new client connect：{}", socket.getRemoteAddress().toString());
            ByteBuffer clientBuffer = ByteBuffer.allocate(1024);
            socket.read(clientBuffer, clientBuffer, new AioReadHandler(socket));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, AioTcpServer attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }
}
