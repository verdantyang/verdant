package com.verdant.demo.common.net.netty.memcached;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Administrator on 2016/7/7.
 */
public class MemcachedCodecTest {
    @Test
    public void MemcachedRequestEncoderTest() {
        MemcachedRequest request = new MemcachedRequest(Opcode.SET, "k1", "v1");
        EmbeddedChannel channel = new EmbeddedChannel(new MemcachedRequestEncoder());
        Assert.assertTrue(channel.writeOutbound(request));
        ByteBuf encoded = (ByteBuf) channel.readOutbound();
        Assert.assertNotNull(encoded);
        Assert.assertEquals(request.getMagic(), encoded.readInt());
        Assert.assertEquals(request.getOpCode(), encoded.readByte());
        Assert.assertEquals(2, encoded.readShort());
        Assert.assertEquals((byte) 0x08, encoded.readByte());
        Assert.assertEquals((byte) 0, encoded.readByte());
        Assert.assertEquals(0, encoded.readShort());
        Assert.assertEquals(2 + 2 + 8, encoded.readInt());
        Assert.assertEquals(request.getId(), encoded.readInt());
        Assert.assertEquals(request.getCas(), encoded.readLong());
        Assert.assertEquals(request.getFlags(), encoded.readInt());
        Assert.assertEquals(request.getExpires(), encoded.readInt());

        byte[] data = new byte[encoded.readableBytes()];
        encoded.readBytes(data);
        Assert.assertArrayEquals((request.getKey() + request.getBody())
                .getBytes(CharsetUtil.UTF_8), data);
        Assert.assertFalse(encoded.isReadable());
        Assert.assertFalse(channel.finish());
        Assert.assertNull(channel.readInbound());
    }

    @Test
    public void MemcachedResponseDecoderTest() {
        EmbeddedChannel channel = new EmbeddedChannel(
                new MemcachedResponseDecoder());
        byte magic = 1;
        byte opCode = Opcode.SET;
        byte dataType = 0;
        byte[] key = "Key1".getBytes(CharsetUtil.UTF_8);
        byte[] body = "Value".getBytes(CharsetUtil.UTF_8);
        int id = (int) System.currentTimeMillis();
        long cas = System.currentTimeMillis();
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeByte(magic);
        buffer.writeByte(opCode);
        buffer.writeShort(key.length);
        buffer.writeByte(0);
        buffer.writeByte(dataType);
        buffer.writeShort(Status.KEY_EXISTS);
        buffer.writeInt(body.length + key.length);
        buffer.writeInt(id);
        buffer.writeLong(cas);
        buffer.writeBytes(key);
        buffer.writeBytes(body);
        Assert.assertTrue(channel.writeInbound(buffer));
        MemcachedResponse response = (MemcachedResponse) channel.readInbound();
        assertResponse(response, magic, opCode, dataType, Status.KEY_EXISTS, 0,
                0, id, cas, key, body);
    }

    private static void assertResponse(MemcachedResponse response, byte magic,
                                       byte opCode, byte dataType, short status, int expires, int flags,
                                       int id, long cas, byte[] key, byte[] body) {
        Assert.assertEquals(magic, response.getMagic());
        Assert.assertArrayEquals(key,
                response.getKey().getBytes(CharsetUtil.UTF_8));
        Assert.assertEquals(opCode, response.getOpCode());
        Assert.assertEquals(dataType, response.getDataType());
        Assert.assertEquals(status, response.getStatus());
        Assert.assertEquals(cas, response.getCas());
        Assert.assertEquals(expires, response.getExpires());
        Assert.assertEquals(flags, response.getFlags());
        Assert.assertArrayEquals(body,
                response.getData().getBytes(CharsetUtil.UTF_8));
        Assert.assertEquals(id, response.getId());
    }
}
