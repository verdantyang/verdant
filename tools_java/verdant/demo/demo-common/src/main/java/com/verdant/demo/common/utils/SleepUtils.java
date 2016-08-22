package com.verdant.demo.common.utils;

import java.util.concurrent.TimeUnit;

/**
 * SleepUtils
 *
 * @author verdant
 * @since 2016/08/22
 */
public class SleepUtils {
    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }
}
