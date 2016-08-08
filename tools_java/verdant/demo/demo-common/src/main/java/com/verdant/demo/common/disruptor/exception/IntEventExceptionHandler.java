package com.verdant.demo.common.disruptor.exception;

import com.lmax.disruptor.ExceptionHandler;

/**
 * Created by Administrator on 2016/8/5.
 */
public class IntEventExceptionHandler implements ExceptionHandler {
    public void handleEventException(Throwable ex, long sequence, Object event) {}
    public void handleOnStartException(Throwable ex) {}
    public void handleOnShutdownException(Throwable ex) {}
}
