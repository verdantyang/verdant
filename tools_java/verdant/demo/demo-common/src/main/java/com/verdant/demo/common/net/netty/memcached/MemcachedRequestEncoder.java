package com.verdant.demo.common.net.netty.memcached;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * Memcached请求解码器
 *
 * @author verdant
 * @since 2016/07/07
 */
public class MemcachedRequestEncoder extends MessageToByteEncoder<MemcachedRequest> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MemcachedRequest msg, ByteBuf out) throws Exception {
        // convert key and body to bytes array
        byte[] key = msg.getKey().getBytes(CharsetUtil.UTF_8);
        byte[] body = msg.getBody().getBytes(CharsetUtil.UTF_8);
        // total size of body = key size + body size + extras size
        int bodySize = key.length + body.length + (msg.isHasExtras() ? 8 : 0);
        int extraSize = msg.isHasExtras() ? 0x08 : 0x0;

        out.writeInt(msg.getMagic());
        out.writeByte(msg.getOpCode());
        out.writeShort(key.length);
        out.writeByte(extraSize);
        // byte is the data type, not currently implemented in Memcached
        out.writeByte(0);
        // next two bytes are reserved, not currently implemented
        out.writeShort(0);
        out.writeInt(bodySize);
        out.writeInt(msg.getId());
        out.writeLong(msg.getCas());
        if (msg.isHasExtras()) {
            // write extras
            // (flags and expiry, 4 bytes each), 8 bytes total
            out.writeInt(msg.getFlags());
            out.writeInt(msg.getExpires());
        }
        out.writeBytes(key);
        out.writeBytes(body);
    }
}