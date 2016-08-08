package com.verdant.demo.common.disruptor;

import com.lmax.disruptor.*;
import com.verdant.demo.common.disruptor.event.IntEvent;
import com.verdant.demo.common.disruptor.exception.IntEventExceptionHandler;
import com.verdant.demo.common.disruptor.processor.IntEventProcessor;
import com.verdant.demo.common.disruptor.producer.IntEventProducer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * IntEvent调度
 *
 * @author verdant
 * @since 2016/08/05
 */
public class IntEventMain {
    private static int bufferSize = 1024;

    public static void main(String[] args) throws InterruptedException {
        //创建一个RingBuffer对象
        RingBuffer<IntEvent> ringBuffer = RingBuffer.createMultiProducer(IntEvent.INT_ENEVT_FACTORY, bufferSize,
                new SleepingWaitStrategy());

        SequenceBarrier produceBarrier = ringBuffer.newBarrier();
        IntEventProducer[] producers = new IntEventProducer[1];
        for (int i = 0; i < producers.length; i++) {
            producers[i] = new IntEventProducer();
        }
        WorkerPool<IntEvent> crawler = new WorkerPool<IntEvent>(ringBuffer, produceBarrier,
                new IntEventExceptionHandler(), producers);

        SequenceBarrier processorBarrier = ringBuffer.newBarrier(crawler.getWorkerSequences());
        IntEventProcessor[] processors = new IntEventProcessor[1];
        for (int i = 0; i < processors.length; i++) {
            processors[i] = new IntEventProcessor();
        }
        WorkerPool<IntEvent> applier = new WorkerPool<IntEvent>(ringBuffer, processorBarrier,
                new IntEventExceptionHandler(), processors);

        List<Sequence> gatingSequences = new ArrayList<Sequence>();
        for (Sequence s : crawler.getWorkerSequences()) {
            ringBuffer.addGatingSequences(s);
        }
        for (Sequence s : applier.getWorkerSequences()) {
            ringBuffer.addGatingSequences(s);
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(7, 7, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(5));
        crawler.start(executor);
        applier.start(executor);

        while (true) {
            Thread.sleep(1000);
            long lastSeq = ringBuffer.next();
            ringBuffer.publish(lastSeq);
        }
    }
}
