package com.verdant.jtools.proxy.center.transfer.model;

import java.io.Serializable;

/**
 * Copyright (C), 2015-2016 中盈优创
 * TransferMessage
 * Author: 龚健
 * Date: 2016/4/21
 */
public class TransferMessage implements Serializable {

    public TransferMessage() {
        super();
    }
    //每次不同
    protected String messageId;
    //web层调用生成  贯穿service
    protected String traceId;
    protected String sequenceId;
    protected String interfaze;
    protected String method;
    protected Class<?>[] parameterTypes;
    protected Object[] parameters;
    //http sync,mq async
    protected boolean async;
    //调用类型
    protected String transferType;
    //序列化类型
    protected String serializerType;
    //版本
    protected Float version;
    protected String timestamp;

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getSerializerType() {
        return serializerType;
    }

    public void setSerializerType(String serializerType) {
        this.serializerType = serializerType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public Float getVersion() {
        return version;
    }

    public void setVersion(Float version) {
        this.version = version;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getInterfaze() {
        return interfaze;
    }

    public void setInterfaze(String interfaze) {
        this.interfaze = interfaze;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }
}
