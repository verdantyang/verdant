package com.verdant.jtools.common.pool.way2;

import com.verdant.jtools.common.pool.ICallback;
import com.verdant.jtools.common.pool.client.AbstractClient;
import com.verdant.jtools.common.pool.config.IConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

/**
 * 抽象连接池
 * <p>
 * 提供无大小限制的borrowPool
 * 提供有大小限制、超时时间的idlePool
 *
 * @author verdant
 * @since 2016/06/02
 */
public class AbstractPool<C extends AbstractClient> {
    private static Logger logger = LoggerFactory.getLogger(AbstractPool.class);

    private final int POOL_SIZE = 10;

    private int idleTimeout = 60 * 1000;
    private IConfig config;

    private Queue<C> borrowPool = new ConcurrentLinkedQueue<C>();

    private Queue<C> idlePool = new ConcurrentLinkedQueue<C>();
    private Semaphore semphore = new Semaphore(POOL_SIZE);

    public int getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(int idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public IConfig getAbstractConfig() {
        return config;
    }

    public void setAbstractConfig(IConfig config) {
        this.config = config;
    }

    public C borrow(Class<C> clazz) {
        C client = getInstance(clazz, borrowPool);
        return client;
    }

    public void release(C client) {
        borrowPool.offer(client);
    }

    public <T> T execute(final Method method, final Object[] args, Class<C> clazz) throws Exception {
        T res = execute(new IExecute<T, C>() {
            @Override
            public T run(C client) {
                T res = null;
                try {
                    res = (T) method.invoke(client, args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return res;
            }
        }, clazz);
        return res;
    }

    public <T> T execute(final ICallback<T, C> callback, Class<C> clazz) throws Exception {
        T res = execute(new IExecute<T, C>() {
            @Override
            public T run(C client) {
                return callback.execute(client);
            }
        }, clazz);
        return res;
    }

    private interface IExecute<T, C> {
        T run(C client);
    }

    private <T> T execute(IExecute<T, C> task, Class<C> clazz) {
        releaseTimeout();
        final C client = getInstance(clazz, idlePool);
        T res = null;
        try {
            semphore.acquire();
            res = task.run(client);
            client.setCreateTime(Calendar.getInstance());
            idlePool.offer(client);
        } catch (InterruptedException e) {
            e.printStackTrace();
            client.close();
        } finally {
            semphore.release();
        }
        return res;
    }

    /**
     * 获取一个Client实例
     *
     * @param clazz
     * @param queue
     * @return
     */
    private C getInstance(Class<C> clazz, Queue<C> queue) {
        C client = null;
        try {
            if (queue.size() > 0)
                client = queue.poll();
            else {
                Constructor constructor = clazz.getDeclaredConstructor(new Class[]{IConfig.class});
                constructor.setAccessible(true);
                client = (C) constructor.newInstance(new Object[]{config});
            }
        } catch (Exception e) {
            logger.error("Construct client error!");
            e.printStackTrace();
        }
        return client;
    }

    /**
     * 释放连接超时的Client
     */
    private void releaseTimeout() {
        while (idlePool.size() > 0) {
            C client = idlePool.peek();
            if (Calendar.getInstance().getTimeInMillis() - client.getCreateTime().getTimeInMillis() > idleTimeout) {
                idlePool.remove(client);
                try {
                    client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                break;
        }
    }
}
