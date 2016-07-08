package com.verdant.jtools.proxy.center.transfer.model;

/**
 * Copyright (C), 2015-2016 中盈优创
 * TransferMessage
 * Author: 龚健
 * Date: 2016/4/21
 */
public class TransferRequestMessage extends TransferMessage {
    public TransferRequestMessage() {
        super();
    }
    private String url;
    private String signature;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


}
