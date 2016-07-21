package com.verdant.demo.common.net.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mina客户端业务逻辑
 *
 * @author verdant
 * @since 2016/06/15
 */
public class MinaClientHandler extends IoHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MinaClientHandler.class);

    private static final String receiveFormat = "<- Client receive: %s";
    private static final String sentFormat = "-> Client sent: %s";

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        logger.info(String.format(receiveFormat, message));
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        logger.info(String.format(sentFormat, message));
    }

}
