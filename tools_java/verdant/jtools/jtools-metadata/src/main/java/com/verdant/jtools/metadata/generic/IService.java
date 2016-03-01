package com.verdant.jtools.metadata.generic;


import com.verdant.jtools.metadata.exception.ServiceException;
import com.verdant.jtools.metadata.model.Page;
import com.verdant.jtools.metadata.model.PageParam;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public interface IService<T> extends Serializable{
    /**
     * 插入对象
     */
    T insert(T model) throws ServiceException;

    /**
     * 批量插入 复用insert
     */
    List<T> inserts(List<T> models) throws ServiceException;

    /**
     * 更新对象
     */
    boolean update(T model) throws ServiceException;

    /**
     * 删除对象
     * 主键使用primaries会按,逗号分割主键以批量更新
     */
    boolean delete(T model) throws ServiceException;

    /**
     * 批量删除
     * 循环复用delete方法
     * 若仅根据主键按逗号分割 更新数据相同delete方法即可支持
     * 用次方法目的仅在于复用delete方法更新数据体不同时
     */
    boolean deletes(List<T> model) throws ServiceException;

    /**
     * 查询单个对象
     */
    T get(T model) throws ServiceException;

    /**
     * 查询分页方法
     */
    Page<T> page(PageParam page, T model) throws ServiceException;
}
