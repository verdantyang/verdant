package com.verdant.jtools.metadata.generic;

import com.verdant.jtools.metadata.exception.ServiceException;
import com.verdant.jtools.metadata.model.AuthToken;

/**
 * 授权存储数据
 * Copyright (C), 2015-2016 中盈优创
 * IUserStorage
 * Author: 龚健
 * Date: 2016/1/13
 */
public interface IAuthorizeStorage {

    void put(AuthToken token,Object user)throws ServiceException;

    void remove(String tokenValue)throws ServiceException;

    <T> T get(String tokenValue, Class<T> clazz) throws ServiceException;
}
