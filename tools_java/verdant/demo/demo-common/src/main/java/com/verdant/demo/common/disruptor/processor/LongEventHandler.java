package com.verdant.demo.common.disruptor.processor;

import com.lmax.disruptor.EventHandler;
import com.verdant.demo.common.disruptor.event.LongEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 事件处理器
 *
 * @author verdant
 * @since 2016/08/05
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    private static final Logger logger = LoggerFactory.getLogger(LongEventHandler.class);

    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        logger.info("Handle the event: {}", longEvent.getValue());
    }
}
