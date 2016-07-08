package com.verdant.demo.common.net.netty.udp;

import java.net.InetSocketAddress;

/**
 * UDP POJO对象
 *
 * @author verdant
 * @since 2016/07/07
 */
public class LogEvent {

    public static final byte SEPARATOR = (byte) '|';

    private final InetSocketAddress source;
    private final long received;
    private final String logfile;
    private final String msg;

    public LogEvent(String logfile, String msg) {
        this(null, -1, logfile, msg);
    }

    public LogEvent(InetSocketAddress source, long received, String logfile, String msg) {
        this.source = source;
        this.logfile = logfile;
        this.msg = msg;
        this.received = received;
    }

    public InetSocketAddress getSource() {
        return source;
    }

    public String getLogfile() {
        return logfile;
    }

    public String getMsg() {
        return msg;
    }

    public long getReceived() {
        return received;
    }

}