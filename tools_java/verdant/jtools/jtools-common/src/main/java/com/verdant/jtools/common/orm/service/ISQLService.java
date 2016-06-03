package com.verdant.jtools.common.orm.service;


import com.verdant.jtools.metadata.exception.ServiceException;
import com.verdant.jtools.metadata.model.Page;
import com.verdant.jtools.common.orm.model.PageParam;

import java.io.Serializable;
import java.util.List;

/**
 * Author: verdant
 * Create: 2016/03/14
 * Desc: 数据库操作接口
 */
public interface ISQLService<T> extends Serializable{
    /**
     * 插入对象
     */
    T insert(T model) throws ServiceException;

    /**
     * 批量插入
     */
    List<T> inserts(List<T> models) throws ServiceException;

    /**
     * 更新对象
     */
    boolean update(T model) throws ServiceException;

    /**
     * 删除对象
     */
    boolean delete(T model) throws ServiceException;

    /**
     * 批量删除
     */
    boolean deletes(List<T> model) throws ServiceException;

    /**
     * 查询单个对象
     */
    T get(T model) throws ServiceException;

    /**
     * 分页查询
     */
    Page<T> page(PageParam page, T model) throws ServiceException;
}
