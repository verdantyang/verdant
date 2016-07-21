package com.verdant.demo.common.net.socket.tcp.aio.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * TCP AIO服务端读处理器
 *
 * @author verdant
 * @since 2016/06/20
 */
public class AioReadHandler implements CompletionHandler<Integer, ByteBuffer> {
    private static final Logger logger = LoggerFactory.getLogger(AioReadHandler.class);

    private AsynchronousSocketChannel channel;
    private CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();

    public AioReadHandler(AsynchronousSocketChannel socket) {
        this.channel = socket;
    }

    @Override
    public void completed(Integer i, ByteBuffer attachment) {
        if (i > 0) {
            attachment.flip();
            try {
                logger.info("Server received {} message: {}",
                        channel.getRemoteAddress().toString(), decoder.decode(attachment));
                attachment.compact();
            } catch (IOException e) {
                e.printStackTrace();
            }
            channel.read(attachment, attachment, this);
        } else if (i == -1) {
            try {
                logger.info("Client disconnected:" + channel.getRemoteAddress().toString());
                attachment.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer buf) {
        exc.printStackTrace();
    }
}

