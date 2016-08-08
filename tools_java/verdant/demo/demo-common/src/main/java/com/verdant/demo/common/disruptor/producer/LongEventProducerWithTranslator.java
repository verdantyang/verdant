package com.verdant.demo.common.disruptor.producer;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.verdant.demo.common.disruptor.event.LongEvent;

import java.nio.ByteBuffer;

/**
 * 事件生产者
 * Disruptor3.0后首选
 *
 * @author verdant
 * @since 2016/08/05
 */
public class LongEventProducerWithTranslator {

    private final RingBuffer<LongEvent> ringBuffer;
    //translator可看做一个事件处理器
    private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR =
            new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
                @Override
                public void translateTo(LongEvent event, long sequence, ByteBuffer bb) {
                    event.setValue(bb.getLong(0));
                }
            };

    public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer bb) {
        ringBuffer.publishEvent(TRANSLATOR, bb);
    }
}
