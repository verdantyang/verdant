package com.verdant.demo.spring.auth.storage.impl;


import com.verdant.demo.spring.auth.model.AuthToken;
import com.verdant.demo.spring.auth.model.CommonEnum;
import com.verdant.demo.spring.auth.storage.IAuthorizeStorage;
import com.verdant.jtools.common.web.utils.WebUtils2;
import com.verdant.jtools.metadata.code.ResultCode;
import com.verdant.jtools.metadata.exception.ServiceException;

/**
 * 认证存储到session 不可集群 仅供单机使用
 * Date: 2016/1/13
 */
public class SessionAuthorizeStorage<T> implements IAuthorizeStorage {

    @Override
    public void put(AuthToken token, Object user) throws ServiceException {
        if (user == null) {
            throw new ServiceException(ResultCode.ERROR_PARAMETER_REQUIRED);
        }
//        WebUtils2.getSession().setAttribute(CommonEnum.TOKEN.name(), user);
    }

    @Override
    public void remove(String tokenValue) throws ServiceException {
//        WebUtils2.getSession().removeAttribute(CommonEnum.TOKEN.name());
    }

    @Override
    public <T> T get(String tokenValue, Class<T> clazz) throws ServiceException {
        return WebUtils2.getSessionAttribute(CommonEnum.TOKEN.name());
    }
}
