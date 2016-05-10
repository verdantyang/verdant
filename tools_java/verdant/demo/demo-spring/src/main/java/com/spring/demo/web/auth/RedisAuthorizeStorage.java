package com.spring.demo.web.auth;

import com.verdant.jtools.cache.util.CacheUtil;
import com.verdant.jtools.metadata.code.ResultCode;
import com.verdant.jtools.metadata.exception.ServiceException;

public class RedisAuthorizeStorage implements IAuthorizeStorage {
    @Override
    public void put(AuthToken token, Object user) throws ServiceException {
        if (token == null || user == null) {
            throw new ServiceException(ResultCode.ERROR_PARAMETER_REQUIRED);
        }
        CacheUtil.getInstance().getCache().set(token.getValue(), user, token.getExpire());
    }

    @Override
    public void remove(String tokenValue) throws ServiceException {
        if (tokenValue == null) {
            return;
        }
        CacheUtil.getInstance().getCache().del(tokenValue);
    }

    @Override
    public <T> T get(String tokenValue, Class<T> clazz) throws ServiceException {
        if (tokenValue == null) {
            return null;
        }
        return CacheUtil.getInstance().getCache().get(tokenValue, clazz);
    }
}
