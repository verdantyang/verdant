package com.verdant.demo.common.net.socket.tcp.aio.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class AioReadHandler implements CompletionHandler<Integer, ByteBuffer> {
    private static final Logger logger = LoggerFactory.getLogger(AioReadHandler.class);

    private CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();

    private AsynchronousSocketChannel channel;

    public AioReadHandler(AsynchronousSocketChannel socket) {
        this.channel = socket;
    }

    public void completed(Integer i, ByteBuffer attachment) {
        if (i > 0) {
            attachment.flip();
            try {
                logger.info("Client received {} 'message: {}",
                        channel.getRemoteAddress().toString(), decoder.decode(attachment));
                attachment.compact();
            } catch (CharacterCodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            channel.read(attachment, attachment, this);
        } else if (i == -1) {
            try {
                logger.info("Server disconnected:" + channel.getRemoteAddress().toString());
                attachment.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void failed(Throwable exc, ByteBuffer buf) {
        exc.printStackTrace();
    }
}
