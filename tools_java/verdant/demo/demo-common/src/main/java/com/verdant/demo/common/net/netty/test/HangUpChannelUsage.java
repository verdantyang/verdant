package com.verdant.demo.common.net.netty.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by Administrator on 2016/7/8.
 */
public class HangUpChannelUsage {
    public void hangup() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx,
                                                ByteBuf msg) throws Exception {
                        //remove this ChannelHandler and de-register
                        ctx.pipeline().remove(this);
                        ctx.deregister();
                    }
                });
        ChannelFuture future = bootstrap.connect(
                new InetSocketAddress("www.baidu.com", 80)).sync();

        Channel channel = future.channel();
        //TODO: Something which takes some amount of time

        //re-register channel and add ChannelFutureLister
        group.register(channel).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Channel registered");
                } else {
                    System.out.println("register channel on EventLoop fail");
                    future.cause().printStackTrace();
                }
            }
        });
    }

    public void migrate() {
        EventLoopGroup group = new NioEventLoopGroup();
        final EventLoopGroup group2 = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx,
                                                ByteBuf msg) throws Exception {
                        // remove this channel handler and de-register
                        ctx.pipeline().remove(this);
                        ChannelFuture f = ctx.deregister();
                        // add ChannelFutureListener
                        f.addListener(new ChannelFutureListener() {
                            @Override
                            public void operationComplete(ChannelFuture future)
                                    throws Exception {
                                // migrate this handler register to group2
                                group2.register(future.channel());
                            }
                        });
                    }
                });
        ChannelFuture future = b.connect("www.baidu.com", 80);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future)
                    throws Exception {
                if (future.isSuccess()) {
                    System.out.println("connection established");
                } else {
                    System.out.println("connection attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        HangUpChannelUsage usage = new HangUpChannelUsage();
        try {
            usage.hangup();
            usage.migrate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
