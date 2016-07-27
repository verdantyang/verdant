package com.verdant.demo.common.thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author: verdant
 * Desc:   锁应用
 */
public class ULock {
    public void ReentrantLockUsage() throws InterruptedException {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {  //使用共享资源
        } finally {
            lock.unlock();
        }
        lock.lockInterruptibly();  //获取可被中断的锁
        lock.tryLock();  //相比lock()，在获取锁失败时有超时机制
    }

    public void ConditionUsage() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Boolean conditionCase = false;
        lock.lock();
        try {
            while (!conditionCase)
                condition.await();
                //使用共享资源
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args)  throws InterruptedException {

    }
}
