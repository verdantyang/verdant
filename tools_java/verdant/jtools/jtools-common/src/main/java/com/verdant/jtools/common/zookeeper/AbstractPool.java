package com.verdant.jtools.common.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 抽象连接池
 *
 * @author verdant
 * @since 2016/06/02
 * 构造函数，操作内容
 */
public class AbstractPool<T extends AbstractClient> {

    private int POOL_SIZE = 10;
    private int TIMEOUT = 60 * 1000;
    private AbstractConfig abstractConfig;

    private List<T> pool = new Vector<>();
    private Semaphore semphore = new Semaphore(POOL_SIZE);
    private static AtomicInteger count = new AtomicInteger(0);

    public AbstractConfig getAbstractConfig() {
        return abstractConfig;
    }

    public void setAbstractConfig(AbstractConfig abstractConfig) {
        this.abstractConfig = abstractConfig;
    }

    public T execute(Runnable operate) {
        T client;
        while (count.get() > 0) {
            AbstractClient<T> oo = pool.get(0);
            if (new Calendar().getTimeInMillis() - oo.getCreateTime().getTimeInMillis() > TIMEOUT) {
                pool.remove(0);
                count.decrementAndGet();
                try {
                    oo.getClient().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                break;
        }
        try {
            semphore.acquire();
            client = T.init();
            pool.add(count.incrementAndGet(), client);
            operate.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
            client.close();
        } finally {
            semphore.release();
        }
        return client;
    }

//    private CuratorFramework createCurator() {
//        CuratorFramework client = CuratorFrameworkFactory.newClient(
//                zookeeperConfig.getConnectString(),
//                zookeeperConfig.getSessionTimeoutMs(),
//                zookeeperConfig.getConnectionTimeoutMs(),
//                zookeeperConfig.getRetryPolicy());
//        client.start();
//        pool.add(count++.get(), client);
//        return client;
//    }
}
