package com.verdant.jtools.proxy.center.model;

import com.commons.proxy.center.transfer.model.TransferType;
import com.commons.proxy.serializable.SerializerType;

import java.io.Serializable;

/**
 * Copyright (C), 2015-2016 中盈优创
 * ProxyConfigure
 * Author: 龚健
 * Date: 2016/4/19
 */
public class ProxyConfigure implements Serializable {
    private String serviceUrl;
    private Class serviceInterface;
    private String serviceInterfaceName;
    private Float maxVersion;
    private SerializerType serializerType = SerializerType.KRYO;
    private TransferType transferType = TransferType.HTTP;

    public SerializerType getSerializerType() {
        return serializerType;
    }

    public void setSerializerType(SerializerType serializerType) {
        this.serializerType = serializerType;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }


    public String getServiceInterfaceName() {
        return serviceInterfaceName;
    }

    public void setServiceInterfaceName(String serviceInterfaceName) {
        this.serviceInterfaceName = serviceInterfaceName;
    }

    public Float getMaxVersion() {
        return maxVersion;
    }

    public void setMaxVersion(Float maxVersion) {
        this.maxVersion = maxVersion;
    }

    public Class getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class serviceInterface) {
        this.serviceInterface = serviceInterface;
    }
}
