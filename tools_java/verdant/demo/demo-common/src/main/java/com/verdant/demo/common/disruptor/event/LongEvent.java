package com.verdant.demo.common.disruptor.event;

/**
 * 事件
 *
 * @author verdant
 * @since 2016/08/05
 */
public class LongEvent {
    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
