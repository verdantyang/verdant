package com.verdant.jtools.common.pool.way1;

import com.verdant.jtools.common.pool.client.AbstractClient;
import com.verdant.jtools.common.pool.ICallback;
import com.verdant.jtools.common.pool.utils.SoftReferenceQueue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 普通连接池接口
 *
 * @author verdant
 * @since 2016/06/02
 * 无大小限制、超时时间
 */
public interface IPool<C extends AbstractClient> {
    C borrow();

    void release(C var1);

    <T> T run(ICallback<T, C> var1);

    class Builder<C extends AbstractClient> {
        private final IFactory factory;
        private Queue<C> queue = new ConcurrentLinkedQueue<C>();  //传给IPool的实现
        private boolean softReferences;

        public Builder(IFactory factory) {
            if (factory == null) {
                throw new IllegalArgumentException("factory must not be null");
            } else {
                this.factory = factory;
            }
        }

        public IPool.Builder<C> queue(Queue<C> queue) {
            if (queue == null) {
                throw new IllegalArgumentException("queue must not be null");
            } else {
                this.queue = queue;
                return this;
            }
        }

        public IPool.Builder<C> softReferences() {
            this.softReferences = true;
            return this;
        }

        public IPool<C> build() {
            Queue<C> q = this.softReferences ? new SoftReferenceQueue<C>(this.queue) : this.queue;
            return new PoolImpl<C>(this.factory, q);
        }

        public String toString() {
            return this.getClass().getName() + "[queue.class=" + this.queue.getClass() + ", softReferences=" + this.softReferences + "]";
        }
    }
}
