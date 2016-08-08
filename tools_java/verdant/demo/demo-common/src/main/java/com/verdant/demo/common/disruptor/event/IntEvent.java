package com.verdant.demo.common.disruptor.event;

import com.lmax.disruptor.EventFactory;

/**
 * IntEvent
 *
 * @author verdant
 * @since 2016/08/05
 */
public class IntEvent {
    private int value = -1;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String toString() {
        return String.valueOf(value);
    }

    public static EventFactory<IntEvent> INT_ENEVT_FACTORY = new EventFactory<IntEvent>() {
        public IntEvent newInstance() {
            return new IntEvent();
        }
    };
}
