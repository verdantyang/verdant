package com.verdant.jtools.common.zookeeper;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/6/3.
 */
public abstract class AbstractClient<T> {
    T client;
    Calendar createTime;

    public abstract void close();
    public abstract T init();

    public T getClient() {
        return client;
    }

    public void setClient(T client) {
        this.client = client;
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }
}
