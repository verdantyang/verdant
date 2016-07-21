package com.verdant.demo.common.net;

/**
 * 常量
 *
 * @author verdant
 * @since 2016/07/07
 */
public class Constants {
    public static final int PORT_UDP = 4096;
    public static final int PORT_TCP_BIO_SERVER = 4001;
    public static final int PORT_TCP_NIO_SERVER = 4002;
    public static final int PORT_TCP_AIO_SERVER = 4003;
    public static final int PORT_UDP_BIO_SERVER = 7001;
    public static final int PORT_UDP_NIO_SERVER = 7002;

    public static final int PORT_MINA_SERVER = 6001;
    public static final int PORT_NETTY_ECHO_SERVER = 6002;

    public static final String COMMAND_END = "quit";
    public static final String COMMAND_SHUTDOWN = "shutdown";
}
