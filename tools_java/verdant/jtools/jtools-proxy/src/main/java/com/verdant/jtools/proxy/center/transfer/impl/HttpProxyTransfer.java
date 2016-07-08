package com.verdant.jtools.proxy.center.transfer.impl;

import com.commons.common.utils.HttpClientHelper;
import com.commons.metadata.code.ResultCode;
import com.commons.metadata.exception.ServiceException;
import com.commons.metadata.model.http.HttpResponse;
import com.commons.proxy.center.model.RequestAuthorize;
import com.commons.proxy.center.model.RequestTrace;
import com.commons.proxy.center.transfer.IProxyTransfer;
import com.commons.proxy.center.transfer.model.TransferContentType;
import com.commons.proxy.center.transfer.model.TransferRequestMessage;
import com.commons.proxy.center.transfer.model.TransferResponseMessage;
import com.commons.proxy.serializable.Serializer;
import org.apache.http.HttpEntity;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.InputStreamEntity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2015-2016 中盈优创
 * IProxyTransfer
 * Author: 龚健
 * Date: 2016/4/21
 */
public class HttpProxyTransfer implements IProxyTransfer {

    private static IProxyTransfer transfer = null;

    public static IProxyTransfer getInstance() {
        if (transfer == null) {
            synchronized (HttpProxyTransfer.class) {
                transfer = new HttpProxyTransfer();
            }
        }
        return transfer;
    }

    public TransferResponseMessage send(TransferRequestMessage requestMessage) throws ServiceException {

        String url = requestMessage.getUrl();


        Map<String, String> header = new HashMap<>();
        header.put(RequestAuthorize.PROXY_ID, requestMessage.getMessageId());
        header.put(RequestAuthorize.PROXY_SIGN, requestMessage.getSignature());
        header.put(RequestAuthorize.PROXY_TIME, requestMessage.getTimestamp());
        header.put(RequestTrace.PROXY_TRACE_ID, requestMessage.getTraceId());
        header.put(RequestTrace.PROXY_SEQUENCE_ID, requestMessage.getSequenceId());

        InputStream is = new ByteArrayInputStream(Serializer.serialize(requestMessage));
        HttpEntity entity = new InputStreamEntity(is, TransferContentType.APPLICATION_COMMON);
        HttpResponse response = null;
        TransferResponseMessage responseMessage = null;
        try {
            response = HttpClientHelper.getInstance().post(url, entity, header);
            responseMessage = Serializer.deserialize(response.getBytes());
        } catch (Exception e) {
            if (e instanceof HttpHostConnectException) {
                throw new ServiceException(ResultCode.REMOTE_INVOKE_HOST_ERROR, e);
            }
            throw new ServiceException(ResultCode.REMOTE_INVOKE_UNKNOWN_ERROR, e);
        }
        if (responseMessage.getException() != null) {
            throw new ServiceException(responseMessage.getException());
        }
        return responseMessage;
    }

}
