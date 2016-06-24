package com.verdant.demo.common.net.netty.test.output;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg,
                          List<Object> out) throws Exception {
        while(msg.readableBytes() >= 4){
            int value = Math.abs(msg.readInt());
            out.add(value);
        }
    }
}
