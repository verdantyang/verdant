package com.verdant.demo.common.net.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import com.verdant.demo.common.net.Constants;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * Mina客户端
 *
 * @author verdant
 * @since 2016/06/15
 */
public class MinaClient {

    public MinaClient(String host, Integer port) {
        // 创建客户端连接器
        NioSocketConnector connector = new NioSocketConnector();

        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

        // 设置连接超时检查时间
        connector.setConnectTimeoutCheckInterval(30);
        connector.setHandler(new MinaClientHandler());

        // 建立连接
        ConnectFuture cf = connector.connect(new InetSocketAddress(host, port));
        // 等待连接创建完成
        cf.awaitUninterruptibly();

        cf.getSession().write("Hi Server!");
        cf.getSession().write("quit");

        // 等待连接断开
        cf.getSession().getCloseFuture().awaitUninterruptibly();
        // 释放连接
        connector.dispose();
    }

    public static void main(String[] args) {
        String serverHost = System.getProperty("host", "127.0.0.1");
        Integer serverPort = Constants.PORT_MINA_SERVER;
        new MinaClient(serverHost, serverPort);
    }
}
