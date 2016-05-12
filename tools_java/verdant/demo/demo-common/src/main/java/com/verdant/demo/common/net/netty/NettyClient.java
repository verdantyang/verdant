package com.verdant.demo.common.net.netty;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Author: verdant
 * Desc:   Netty客户端
 */
public class NettyClient {

    public static void main(String[] args) {

//        ChannelFactory factory = new NioClientSocketChannelFactory(
//                Executors.newCachedThreadPool(),
//                Executors.newCachedThreadPool());
//        ClientBootstrap bootstrap = new ClientBootstrap(factory);
//        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
//            public ChannelPipeline getPipeline() {
//                ChannelPipeline pipeline = Channels.pipeline();
//                pipeline.addLast("encode",new StringEncoder());
//                pipeline.addLast("decode",new StringDecoder());
//                pipeline.addLast("handler",new TimeClientHandler());
//                return pipeline;
//            }
//        });
//        bootstrap.setOption("tcpNoDelay" , true);
//        bootstrap.setOption("keepAlive", true);
//        bootstrap.connect (new InetSocketAddress("127.0.0.1", 8080));

        // Configure the client.
        ClientBootstrap bootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

        // Set up the default event pipeline.
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new StringDecoder(), new StringEncoder(), new NettyClientHandler());
            }
        });

        // Start the connection attempt.
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost", 8000));

        // Wait until the connection is closed or the connection attempt fails.
        future.getChannel().getCloseFuture().awaitUninterruptibly();

        // Shut down thread pools to exit.
        bootstrap.releaseExternalResources();
    }


}
