package com.verdant.demo.common.net.netty.memcached;

import java.util.Random;

/**
 * Memcached请求消息体
 *
 * @author verdant
 * @since 2016/07/07
 */
public class MemcachedRequest {

    private static final Random rand = new Random();
    private int magic = 0x80;  // fixed so hard coded
    private byte opCode;       // the operation e.g. set or get
    private String key;        // the key to delete, get or set
    private int flags = 0xdeadbeef; // random
    private int expires;           // 0 = item never expires
    private String body;           // if opCode is set, the value
    private int id = rand.nextInt(); // Opaque
    private long cas;           // data version check...not used
    private boolean hasExtras; // not all ops have extras

    public MemcachedRequest(byte opcode, String key, String value) {
        this.opCode = opcode;
        this.key = key;
        this.body = value == null ? "" : value;
        // only set command has extras in our example
        hasExtras = opcode == Opcode.SET;
    }

    public MemcachedRequest(byte opCode, String key) {
        this(opCode, key, null);
    }

    public int getMagic() {
        return magic;
    }

    public byte getOpCode() {
        return opCode;
    }

    public String getKey() {
        return key;
    }

    public int getFlags() {
        return flags;
    }

    public int getExpires() {
        return expires;
    }

    public String getBody() {
        return body;
    }

    public int getId() {
        return id;
    }

    public long getCas() {
        return cas;
    }

    public boolean isHasExtras() {
        return hasExtras;
    }

}
