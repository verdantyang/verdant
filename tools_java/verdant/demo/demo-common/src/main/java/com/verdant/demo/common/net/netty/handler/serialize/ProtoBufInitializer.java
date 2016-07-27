package com.verdant.demo.common.net.netty.handler.serialize;

/**
 * Created by Administrator on 2016/6/23.
 */

import com.google.protobuf.MessageLite;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

import java.io.Serializable;

/**
 * 使用Protobuf序列化
 * 注意：需要protobuf-java-2.5.0.jar
 *
 * @author verdant
 * @since 2016/06/23
 */
public class ProtoBufInitializer extends ChannelInitializer<Channel> {

    private final MessageLite lite;

    public ProtoBufInitializer(MessageLite lite) {
        this.lite = lite;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufEncoder())
                .addLast(new ProtobufDecoder(lite))
                .addLast(new ObjectHandler());
    }

    public final class ObjectHandler extends SimpleChannelInboundHandler<Serializable> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Serializable msg) throws Exception {
            // do something
        }
    }
}
