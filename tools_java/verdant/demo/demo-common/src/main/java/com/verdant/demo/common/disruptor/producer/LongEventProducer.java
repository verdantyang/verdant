package com.verdant.demo.common.disruptor.producer;

import com.lmax.disruptor.RingBuffer;
import com.verdant.demo.common.disruptor.event.LongEvent;

import java.nio.ByteBuffer;

/**
 * 事件生产者
 *
 * @author verdant
 * @since 2016/08/05
 */
public class LongEventProducer {
    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * onData用来发布事件，每调用一次就发布一次事件
     *
     * @param bb
     */
    public void onData(ByteBuffer bb) {
        //可以把ringBuffer看做一个事件队列，next用来得到下面一个事件槽
        long sequence = ringBuffer.next();
        try {
            //用上面的索引取出一个空的事件用于填充
            LongEvent event = ringBuffer.get(sequence);
            event.setValue(bb.getLong(0));
        } finally {
            //发布事件
            ringBuffer.publish(sequence);
        }
    }
}