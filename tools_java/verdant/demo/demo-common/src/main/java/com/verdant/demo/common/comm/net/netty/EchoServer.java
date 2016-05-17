package com.verdant.demo.common.comm.net.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: verdant
 * Desc:   Netty服务端
 */
public class EchoServer {

    private static final Integer PORT_SERVER = 6488;
    private static final Logger log = LoggerFactory.getLogger(EchoServer.class);

    public void start(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel sc) throws Exception {
                    // server端发送的是httpResponse，解析要使用HttpResponseEncoder进行编码
                    sc.pipeline().addLast(new HttpResponseEncoder());
                    // server端接收到的是httpRequest，解析要使用HttpRequestDecoder进行解码
                    sc.pipeline().addLast(new HttpRequestDecoder());
                    sc.pipeline().addLast(new NettyServerHandler());
                }
            });

            ChannelFuture cf = b.bind(port).sync();
            cf.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        EchoServer server = new EchoServer();
        log.info("Http Server listening on 8844 ...");
        server.start(8844);
    }
}
