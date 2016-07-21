package com.verdant.demo.common.net.netty.echo;


import com.verdant.demo.common.net.Constants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EchoServer
 *
 * @author verdant
 * @since 2016/06/15
 */
public class EchoServer {

    private static final int PORT_SERVER = Integer.parseInt(System.getProperty("port", "6488"));
    private static final Logger logger = LoggerFactory.getLogger(EchoServer.class);

    public void start(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(64, 1024, 65536))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline cp = ch.pipeline();
                            // server端发送的是httpResponse，解析要使用HttpResponseEncoder进行编码
                            cp.addLast(new HttpResponseEncoder());
                            // server端接收到的是httpRequest，解析要使用HttpRequestDecoder进行解码
                            cp.addLast(new HttpRequestDecoder());
                            cp.addLast(new EchoServerHandler());
                        }
                    });
            // Start the server.
            ChannelFuture cf = b.bind(port).sync();
            // Wait until the server socket is closed.
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        EchoServer server = new EchoServer();
        Integer serverPort = Constants.PORT_NETTY_ECHO_SERVER;
        logger.info("Http Server listening on " + serverPort);
        server.start(serverPort);
    }
}
