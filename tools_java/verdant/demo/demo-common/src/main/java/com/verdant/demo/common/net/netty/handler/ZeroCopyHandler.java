package com.verdant.demo.common.net.netty.handler;

import io.netty.channel.*;

import java.io.File;
import java.io.FileInputStream;

/**
 * 零拷贝读取文件
 *
 * @author verdant
 * @since 2016/06/23
 */
public class ZeroCopyHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        File file = new File("test.txt");
        FileInputStream fis = new FileInputStream(file);
        FileRegion region = new DefaultFileRegion(fis.getChannel(), 0, file.length());
        Channel channel = ctx.channel();
        channel.writeAndFlush(region).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(!future.isSuccess()){
                    Throwable cause = future.cause();
                    // do something
                }
            }
        });
    }
}
