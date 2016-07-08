package com.verdant.jtools.proxy.server.exporter.factory;

import com.commons.metadata.code.ResultCode;
import com.commons.metadata.exception.ServiceException;
import com.commons.proxy.center.transfer.model.TransferType;
import com.commons.proxy.server.handler.HttpServiceExporter;
import com.commons.proxy.server.handler.SecurityHessianServiceExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (C), 2015-2016 中盈优创
 * IProxyTransfer
 * Author: 龚健
 * Date: 2016/4/21
 */
public class ExporterFactory {

    private static Logger logger = LoggerFactory.getLogger(ExporterFactory.class);

    public static Class buildClazz(TransferType transferType) throws ServiceException {
        switch (transferType) {
            case HTTP:
                return HttpServiceExporter.class;
            case HESSIAN:
                return SecurityHessianServiceExporter.class;
            case NETTY:
                return null;
            case MQ:
                return null;
            default:
                logger.error("ProxyTransferFactory Transfer Enums Not Include {}", transferType);
                throw new ServiceException(ResultCode.ERROR_PARAMETER);
        }
    }

}
