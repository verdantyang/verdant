package com.verdant.jtools.proxy.center.transfer.model;

/**
 * Copyright (C), 2015-2016 中盈优创
 * TransferMessage
 * Author: 龚健
 * Date: 2016/4/21
 */
public class TransferResponseMessage extends TransferMessage {

    public TransferResponseMessage() {
        super();
    }
    private Object result;

    private Throwable exception;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }
}
