package com.verdant.demo.common.thread.lock;

import org.junit.Test;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock锁应用
 *
 * @author verdant
 * @since 2016/07/27
 */
public class UReentrantLock {

    private static Lock fairLock = new ReentrantLock2(true);
    private static Lock unfairLock = new ReentrantLock2(false);

    @Test
    public void fair() throws InterruptedException {
        System.out.println("fair version");
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Job(fairLock)) {
                public String toString() {
                    return getName();
                }
            };
            thread.setName("" + i);
            thread.start();
        }
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void unfair() throws InterruptedException {
        System.out.println("unfair version");
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Job(unfairLock)) {
                public String toString() {
                    return getName();
                }
            };
            thread.setName("" + i);
            thread.start();
        }
        TimeUnit.SECONDS.sleep(5);
    }

    private static class Job implements Runnable {
        private Lock lock;

        public Job(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                lock.lock();
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                    System.out.println("Lock by:"
                            + Thread.currentThread().getName() + " and "
                            + ((ReentrantLock2) lock).getQueuedThreads()
                            + " waits.");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private static class ReentrantLock2 extends ReentrantLock {

        // Constructor Override
        private static final long serialVersionUID = 1773716895097002072L;

        private ReentrantLock2(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads() {
            return super.getQueuedThreads();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        if (lock.tryLock(5, TimeUnit.SECONDS)) {  //可以设定获取锁的超时时间
            try {
                //使用共享资源
            } finally {
                lock.unlock();
            }
        }
        lock.lockInterruptibly();  //获取可被中断的锁
    }
}
