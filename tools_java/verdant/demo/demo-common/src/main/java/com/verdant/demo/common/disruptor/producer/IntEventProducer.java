package com.verdant.demo.common.disruptor.producer;

import com.lmax.disruptor.WorkHandler;
import com.verdant.demo.common.disruptor.event.IntEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Event生产者
 *
 * @author verdant
 * @since 2016/08/05
 */
public class IntEventProducer implements WorkHandler<IntEvent> {
    private static final Logger logger = LoggerFactory.getLogger(IntEventProducer.class);

    private int seq = 0;

    public void onEvent(IntEvent event) throws Exception {
        logger.info("Produce the Event: {}", ++seq);
        event.setValue(seq);
    }
}
