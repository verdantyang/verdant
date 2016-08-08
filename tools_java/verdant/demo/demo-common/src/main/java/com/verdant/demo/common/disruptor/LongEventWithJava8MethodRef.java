package com.verdant.demo.common.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.verdant.demo.common.disruptor.event.LongEvent;
import com.verdant.demo.common.disruptor.producer.LongEventProducer;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 事件调度（Java8的方式）
 * 在Java 8中方法引用也是一个lambda
 *
 * @author verdant
 * @since 2016/08/05
 */
public class LongEventWithJava8MethodRef {
    private static int bufferSize = 1024;

    public static void handleEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println(event.getValue());
    }

    public static void translate(LongEvent event, long sequence, ByteBuffer buffer) {
        event.setValue(buffer.getLong(0));
    }

    public static void main(String[] args) throws Exception {

        // Executor that will be used to construct new threads for consumers
        Executor executor = Executors.newCachedThreadPool();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        // Construct the Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, threadFactory);

        // Connect the handler
        disruptor.handleEventsWith(LongEventWithJava8MethodRef::handleEvent);
        // Start the Disruptor, starts all threads running
        disruptor.start();
        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        LongEventProducer producer = new LongEventProducer(ringBuffer);

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            ringBuffer.publishEvent(LongEventWithJava8MethodRef::translate, bb);
            Thread.sleep(1000);
        }
    }
}
