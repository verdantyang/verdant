package com.verdant.demo.common.thread.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁实现
 *
 * @author verdant
 * @since 2016/07/27
 */
public class SpinLockImpl {
    private AtomicReference<Thread> owner = new AtomicReference<>();
    private int count = 0;

    public void lock() {
        Thread current = Thread.currentThread();
        while (!owner.compareAndSet(null, current)) {
        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        owner.compareAndSet(current, null);
    }


    public void lockReentry() {
        Thread current = Thread.currentThread();
        if (current == owner.get()) {
            count++;
            return;
        }
        while (!owner.compareAndSet(null, current)) {
        }
    }

    public void unlockReentry() {
        Thread current = Thread.currentThread();
        if (current == owner.get()) {
            if (count != 0) {
                count--;
            } else {
                owner.compareAndSet(current, null);
            }
        }
    }
}
