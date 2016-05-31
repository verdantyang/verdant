package com.verdant.jtools.common.zookeeper;

import com.google.common.base.Preconditions;
import org.apache.curator.RetryPolicy;
import org.apache.curator.RetrySleeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Author: verdant
 * Date: 2015/11/12
 */
public class ZookeeperRetryForever implements RetryPolicy {
    private static final Logger log = LoggerFactory.getLogger(ZookeeperRetryForever.class);
    private final int retryIntervalMs;

    public ZookeeperRetryForever(int retryIntervalMs) {
        Preconditions.checkArgument(retryIntervalMs > 0);
        this.retryIntervalMs = retryIntervalMs;
    }

    public boolean allowRetry(int retryCount, long elapsedTimeMs, RetrySleeper sleeper) {
        try {
            sleeper.sleepFor((long) this.retryIntervalMs, TimeUnit.MILLISECONDS);
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Error occurred while sleeping", e);
            return false;
        }
    }
}
