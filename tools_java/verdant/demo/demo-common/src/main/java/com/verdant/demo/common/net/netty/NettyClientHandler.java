package com.verdant.demo.common.net.netty;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2016/5/12.
 */
public class NettyClientHandler extends SimpleChannelHandler {
    private BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        if (e.getMessage() instanceof String) {
            String message = (String) e.getMessage();
            System.out.println(message);

            e.getChannel().write(sin.readLine());

            System.out.println("\n等待客户端输入。。。");
        }

        super.messageReceived(ctx, e);
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("已经与Server建立连接。。。。");
        System.out.println("\n请输入要发送的信息：");
        super.channelConnected(ctx, e);

        e.getChannel().write(sin.readLine());
    }

}
