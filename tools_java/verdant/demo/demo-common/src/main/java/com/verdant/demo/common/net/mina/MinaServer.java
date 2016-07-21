package com.verdant.demo.common.net.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import com.verdant.demo.common.net.Constants;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mina服务端
 *
 * @author verdant
 * @since 2016/06/15
 */
public class MinaServer {
    private static final Logger logger = LoggerFactory.getLogger(MinaServerHandler.class);

    public MinaServer(int port) throws IOException {
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
        acceptor.bind(new InetSocketAddress(port));
        logger.info("Server listen on port: " + port);
    }

    public static void main(String[] args) throws IOException {
        try {
            Integer serverPort = Constants.PORT_MINA_SERVER;
            new MinaServer(serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}