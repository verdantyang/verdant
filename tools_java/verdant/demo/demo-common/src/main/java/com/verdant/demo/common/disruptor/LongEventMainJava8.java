package com.verdant.demo.common.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.verdant.demo.common.disruptor.event.LongEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 事件调度（Java8的方式）
 *
 * @author verdant
 * @since 2016/08/05
 */
public class LongEventMainJava8 {
    private static final Logger logger = LoggerFactory.getLogger(LongEventMainJava8.class);

    private static int bufferSize = 1024;
    /**
     * 用lambda表达式来注册EventHandler和EventProductor
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        // Executor that will be used to construct new threads for consumers
        Executor executor = Executors.newCachedThreadPool();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        // Construct the Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, threadFactory);
        // 可以使用lambda来注册一个EventHandler
        disruptor.handleEventsWith((event, sequence, endOfBatch) -> logger.info("Handle the Event by lambda: {}", event.getValue()));
        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.setValue(buffer.getLong(0)), bb);
            Thread.sleep(1000);
        }
    }
}
