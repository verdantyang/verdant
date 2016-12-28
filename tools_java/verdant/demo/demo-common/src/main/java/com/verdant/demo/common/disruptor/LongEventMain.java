package com.verdant.demo.common.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.verdant.demo.common.disruptor.event.LongEvent;
import com.verdant.demo.common.disruptor.event.LongEventFactory;
import com.verdant.demo.common.disruptor.processor.LongEventHandler;
import com.verdant.demo.common.disruptor.producer.LongEventProducer;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 事件调度
 *
 * @author verdant
 * @since 2016/08/05
 */
public class LongEventMain {
    // Specify the size of the ring buffer, must be power of 2.
    private static int bufferSize = 1024;

    public static void main(String[] args) throws InterruptedException {

        // The factory for the event
        LongEventFactory factory = new LongEventFactory();

        // Executor that will be used to construct new threads for consumers
        Executor executor = Executors.newCachedThreadPool();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        // Construct the Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, threadFactory);

        Disruptor<LongEvent> disruptorSingle = new Disruptor<>(factory, bufferSize,
                threadFactory, ProducerType.SINGLE, new BlockingWaitStrategy());

        Disruptor<LongEvent> disruptorMulti = new Disruptor<>(factory, bufferSize,
                threadFactory, ProducerType.MULTI, new BlockingWaitStrategy());
        // Connect the handler
        disruptor.handleEventsWith(new LongEventHandler());
        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ringBuffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        System.out.println(ringBuffer.toString());
        LongEventProducer producer = new LongEventProducer(ringBuffer);

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            producer.onData(bb);
            Thread.sleep(1000);
        }
//        disruptor.shutdown();
    }
}
