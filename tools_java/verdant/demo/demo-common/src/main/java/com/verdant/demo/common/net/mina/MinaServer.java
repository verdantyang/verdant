package com.verdant.demo.common.net.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * Author: verdant
 * Desc:   Mina服务端
 */
public class MinaServer {
    //服务端监听端口
    private static final Integer PORT_SERVER = 6488;

    public static void main(String[] args) throws IOException {
        // 创建服务端监控线程
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

        // 设置日志记录器
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        // 设置编码过滤器
        acceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

        // 指定业务逻辑处理器
        acceptor.setHandler(new MinaServerHandler());
        // 监听端口
        acceptor.bind(new InetSocketAddress(PORT_SERVER));
        System.out.println("Server listen on port: " + PORT_SERVER);
    }
}