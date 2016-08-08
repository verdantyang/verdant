package com.verdant.demo.common.disruptor.event;

import com.lmax.disruptor.EventFactory;
import com.verdant.demo.common.disruptor.event.LongEvent;

/**
 * 事件工厂，实例化Event对象
 *
 * @author verdant
 * @since 2016/08/05
 */
public class LongEventFactory implements EventFactory {
    @Override
    public Object newInstance() {
        return new LongEvent();
    }
}
