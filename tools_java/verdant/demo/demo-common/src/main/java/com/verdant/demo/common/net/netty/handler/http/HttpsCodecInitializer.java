package com.verdant.demo.common.net.netty.handler.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 * HTTPS消息处理器
 *
 * @author verdant
 * @since 2016/06/23
 */
public class HttpsCodecInitializer extends ChannelInitializer<Channel> {

    private final SSLContext context;
    private final boolean client;
    private final boolean startTls;

    public HttpsCodecInitializer(SSLContext context, boolean client, boolean startTls) {
        this.context = context;
        this.client = client;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        SSLEngine engine = context.createSSLEngine();
        engine.setUseClientMode(client);
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addFirst("ssl", new SslHandler(engine, startTls));
        if (client) {
            pipeline.addLast("codec", new HttpClientCodec());
        } else {
            pipeline.addLast("codec", new HttpServerCodec());
        }
    }

}
