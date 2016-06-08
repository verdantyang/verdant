package com.verdant.jtools.common.pool.client;

import java.util.Calendar;
import java.util.UUID;

/**
 * 抽象客户端
 *
 * @author verdant
 * @since 2016/06/02
 */
public abstract class AbstractClient<E> {
    private E executor;
    private String uuid;
    private Calendar createTime;

    public AbstractClient() {
        this.createTime = Calendar.getInstance();
        this.uuid = UUID.randomUUID().toString();
    }

    public abstract void close();

    public E getExecutor() {
        return executor;
    }

    public void setExecutor(E executor) {
        this.executor = executor;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }
}
