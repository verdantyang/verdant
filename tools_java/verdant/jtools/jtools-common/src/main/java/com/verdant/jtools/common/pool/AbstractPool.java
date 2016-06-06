package com.verdant.jtools.common.pool;

import com.verdant.jtools.common.pool.config.AbstractConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 抽象连接池
 *
 * @author verdant
 * @since 2016/06/02
 * 构造函数，操作内容
 */
public class AbstractPool<C extends AbstractClient> {

    private int POOL_SIZE = 10;
    private int IDLE_TIMEOUT = 60 * 1000;
    private AbstractConfig config;

    private List<C> idlePool = new Vector<>();
    private List<C> activePool = new Vector<>();
    private Semaphore semphore = new Semaphore(POOL_SIZE);
    private static AtomicInteger idleCounts = new AtomicInteger(0);

    public AbstractConfig getAbstractConfig() {
        return config;
    }

    public void setAbstractConfig(AbstractConfig config) {
        this.config = config;
    }

    public C borrow(Class<C> clazz) throws Exception {
        return getInstance(clazz, true);
    }

    public C release(Class<C> clazz) throws Exception {
        return getInstance(clazz, true);
    }

    public <T> T execute(Method method, final Object[] args, Class<C> clazz) throws Exception {
        releaseTimeout();
        C client = getInstance(clazz, false);
        T res = null;
        try {
            semphore.acquire();
            res = (T) method.invoke(client, args);
            client.setCreateTime(Calendar.getInstance());
            idlePool.add(idleCounts.getAndIncrement(), client);
        } catch (InterruptedException e) {
            e.printStackTrace();
            client.close();
        } finally {
            semphore.release();
        }
        return res;
    }

    public <T> T execute(ICallback<T, C> callback, Class<C> clazz) throws Exception {
        releaseTimeout();
        C client = getInstance(clazz, false);
        T res = null;
        try {
            semphore.acquire();
            res = callback.execute(client);
            client.setCreateTime(Calendar.getInstance());
            idlePool.add(idleCounts.getAndIncrement(), client);
        } catch (InterruptedException e) {
            e.printStackTrace();
            client.close();
        } finally {
            semphore.release();
        }
        return res;
    }

    private C getInstance(Class<C> clazz, boolean initialize) throws Exception {
        C client;
        if (idleCounts.get() > 0)
            client = idlePool.get(idleCounts.decrementAndGet());
        else {
            Constructor constructor = clazz.getDeclaredConstructor(new Class[]{AbstractConfig.class});
            constructor.setAccessible(true);
            client = (C) constructor.newInstance(new Object[]{config});
            if (initialize) {
                semphore.acquire();
                activePool.add(idleCounts.getAndIncrement(), client);
                semphore.release();
            }
        }
        return client;
    }

    private void releaseTimeout() {
        while (idleCounts.get() > 0) {
            C elem = idlePool.get(0);
            if (Calendar.getInstance().getTimeInMillis() - elem.getCreateTime().getTimeInMillis() > IDLE_TIMEOUT) {
                idlePool.remove(0);
                idleCounts.decrementAndGet();
                try {
                    elem.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                break;
        }
    }
}
