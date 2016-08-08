package com.verdant.demo.common.disruptor.processor;

import com.lmax.disruptor.WorkHandler;
import com.verdant.demo.common.disruptor.event.IntEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Event消费者
 *
 * @author verdant
 * @since 2016/08/05
 */
public class IntEventProcessor implements WorkHandler<IntEvent> {
    private static final Logger logger = LoggerFactory.getLogger(IntEventProcessor.class);

    public void onEvent(IntEvent event) throws Exception {
        logger.info("Handle the event: {}", event.getValue());
        event.setValue(1);
    }
}
