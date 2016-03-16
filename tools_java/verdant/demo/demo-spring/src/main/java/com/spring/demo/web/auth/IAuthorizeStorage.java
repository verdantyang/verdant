package com.spring.demo.web.auth;

import com.verdant.jtools.metadata.exception.ServiceException;

/**
 * IAuthorizeStorage 授权存储数据
 * Author: verdant
 * Create: 2016/03/14
 */
public interface IAuthorizeStorage {

    void put(AuthToken token,Object user)throws ServiceException;

    void remove(String tokenValue)throws ServiceException;

    <T> T get(String tokenValue, Class<T> clazz) throws ServiceException;
}
