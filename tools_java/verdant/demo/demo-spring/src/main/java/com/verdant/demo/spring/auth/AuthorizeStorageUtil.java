package com.verdant.demo.spring.auth;


import com.verdant.demo.spring.auth.model.AuthToken;
import com.verdant.demo.spring.auth.storage.IAuthorizeStorage;
import com.verdant.jtools.common.spring.utils.ContextUtils;
import com.verdant.jtools.metadata.exception.ServiceException;

/**
 * 授权存储工具类
 * Copyright (C), 2015-2016 中盈优创
 * AuthorizeStorageUtil
 * Date: 2016/1/13
 */
public class AuthorizeStorageUtil {

    private static IAuthorizeStorage storage;

    static {
        if (storage == null) {
            storage = ContextUtils.getBean(IAuthorizeStorage.class);
        }
    }

    public void put(AuthToken token, Object user) throws ServiceException {
        storage.put(token, user);
    }

    public void remove(String tokenValue) throws ServiceException {
        storage.remove(tokenValue);
    }

    public <T> T get(String tokenValue, Class<T> clazz) throws ServiceException {
        return storage.get(tokenValue, clazz);
    }
}
