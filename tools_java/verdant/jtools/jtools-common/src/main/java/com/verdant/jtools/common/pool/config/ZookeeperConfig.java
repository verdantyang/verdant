package com.verdant.jtools.common.pool.config;

import com.verdant.jtools.common.zookeeper.retry.RetryForever;
import org.apache.curator.RetryPolicy;

import java.io.Serializable;

/**
 * Zookeeper配置
 *
 * @author verdant
 * @since 2016/06/02
 */
public class ZookeeperConfig extends AbstractConfig implements Serializable{
    private String connectString = "192.168.6.207:2181";
    private int sessionTimeoutMs = 20 * 1000;
    private int connectionTimeoutMs = 15 * 1000;
    private RetryPolicy retryPolicy = new RetryForever(3 * 1000);

    public String getConnectString() {
        return connectString;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    public int getSessionTimeoutMs() {
        return sessionTimeoutMs;
    }

    public void setSessionTimeoutMs(int sessionTimeoutMs) {
        this.sessionTimeoutMs = sessionTimeoutMs;
    }

    public int getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public void setConnectionTimeoutMs(int connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }
}
