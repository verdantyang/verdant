package com.verdant.jtools.common.pool.impl;

import com.verdant.jtools.common.pool.AbstractClient;
import com.verdant.jtools.common.pool.ICallback;
import com.verdant.jtools.common.pool.IFactory;
import com.verdant.jtools.common.pool.IPool;

import java.util.Queue;

/**
 * 普通连接池实现
 *
 * @author verdant
 * @since 2016/06/02
 * 无大小限制、超时时间
 */
public class PoolImpl<C extends AbstractClient> implements IPool<C> {
    private final Queue<C> queue;
    private final IFactory<C> factory;

    public PoolImpl(IFactory factory, Queue<C> queue) {
        this.factory = factory;
        this.queue = queue;
    }

    public int size() {
        return this.queue.size();
    }

    public C borrow() {
        C res;
        return (res = this.queue.poll()) != null ? res : this.factory.create();
    }

    public void release(C client) {
        this.queue.offer(client);
    }

    public <T> T run(ICallback<T, C> callback) {
        C client = this.borrow();

        T var3;
        try {
            var3 = callback.execute(client);
        } finally {
            this.release(client);
        }

        return var3;
    }

    public void clear() {
        this.queue.clear();
    }
}

