package com.verdant.demo.common.net.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * Author: verdant
 * Desc:   客户端业务逻辑
 */
public class MinaClientHandler extends IoHandlerAdapter {
    private static final String receiveFormat = "<- Client receive: %s";
    private static final String sentFormat = "-> Client sent: %s";

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println(String.format(receiveFormat, message));
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println(String.format(sentFormat, message));
    }

}
