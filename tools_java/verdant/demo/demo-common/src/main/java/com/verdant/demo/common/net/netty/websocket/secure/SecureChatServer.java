package com.verdant.demo.common.net.netty.websocket.secure;

import com.verdant.demo.common.net.netty.websocket.ChatServer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;

import java.net.InetSocketAddress;
import javax.net.ssl.SSLContext;

/**
 * 访问地址：http://localhost:4096
 *
 * @author verdant
 * @since 2016/06/22
 */
public class SecureChatServer extends ChatServer {
    private final SSLContext context;

    public SecureChatServer(SSLContext context) {
        this.context = context;
    }

    @Override
    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
        return new SecureChatServerIntializer(group, context);
    }

    /**
     * 获取SSLContext需要相关的keystore文件
     *
     * @return
     */
    private static SSLContext getSslContext() {
        return SecureChatSslContextFactory.getServerContext();
    }

    public static void main(String[] args) {
        SSLContext context = getSslContext();
        final SecureChatServer server = new SecureChatServer(context);
        ChannelFuture future = server.start(new InetSocketAddress(4096));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                server.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }
}
