package com.verdant.demo.common.net.socket.tcp.aio.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class AioReadHandler implements CompletionHandler<Integer,ByteBuffer> {
    private static final Logger log = LoggerFactory.getLogger(AioReadHandler.class);

    private CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();

    private AsynchronousSocketChannel socket;

    public AioReadHandler(AsynchronousSocketChannel socket) {
        this.socket = socket;
    }

    public void cancelled(ByteBuffer attachment) {
        log.info("cancelled");
    }

    @Override
    public void completed(Integer i, ByteBuffer buf) {
        if (i > 0) {
            buf.flip();
            try {
                log.info("服务端收到" + socket.getRemoteAddress().toString() + "的消息: " + decoder.decode(buf));
                buf.compact();
            } catch (CharacterCodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            socket.read(buf, buf, this);
        } else if (i == -1) {
            try {
                log.info("客户端断线:" + socket.getRemoteAddress().toString());
                buf = null;
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

