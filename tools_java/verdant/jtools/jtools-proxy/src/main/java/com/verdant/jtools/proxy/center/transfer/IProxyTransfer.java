package com.verdant.jtools.proxy.center.transfer;

import com.commons.metadata.exception.ServiceException;
import com.commons.proxy.center.transfer.model.TransferRequestMessage;
import com.commons.proxy.center.transfer.model.TransferResponseMessage;

/**
 * Copyright (C), 2015-2016 中盈优创
 * IProxyTransfer
 * Author: 龚健
 * Date: 2016/4/21
 */
public interface IProxyTransfer {

    TransferResponseMessage send(TransferRequestMessage requestMessage) throws ServiceException;


}
