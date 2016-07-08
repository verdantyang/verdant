package com.verdant.jtools.proxy.center.transfer.model;


import org.apache.http.entity.ContentType;

import java.io.Serializable;

/**
 * Copyright (C), 2015-2016 中盈优创
 * TransferContentType
 * Author: 龚健
 * Date: 2016/4/22
 */
public class TransferContentType implements Serializable {
    public static final ContentType APPLICATION_COMMON;

    static {
        APPLICATION_COMMON = ContentType.create("x-application/common", "UTF-8");
    }
}
