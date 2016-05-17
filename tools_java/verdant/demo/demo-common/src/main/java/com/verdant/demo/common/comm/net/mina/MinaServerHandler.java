package com.verdant.demo.common.comm.net.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Author: verdant
 * Desc:   服务端业务逻辑
 */
public class MinaServerHandler extends IoHandlerAdapter {
    private static final String commandShutdown = "quit";
    private static final String receiveFormat = "<- Server receive: %s";
    private static final String acceptFormat = "Connect with %s:%s";

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        System.out.println("IDLE" + session.getIdleCount(status));
    }

    /**
     * 连接事件
     * @param session
     */
    @Override
    public void sessionCreated(IoSession session) {
        String [] address = session.getRemoteAddress().toString().replace("/", "").split(":");
        System.out.println(String.format(acceptFormat, address[0], address[1]));
    }

    /**
     * 消息接收事件
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String strMsg = message.toString();
        if (strMsg.trim().equalsIgnoreCase(commandShutdown)) {
            session.closeOnFlush();
            return;
        }
        System.out.println(String.format(receiveFormat, message));
        // 返回消息字符串
        session.write("Hi Client!");
    }
}