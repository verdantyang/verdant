package com.verdant.demo.common.thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 条件变量应用
 *
 * @author verdant
 * @since 2016/07/27
 */
public class UCondition {

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
