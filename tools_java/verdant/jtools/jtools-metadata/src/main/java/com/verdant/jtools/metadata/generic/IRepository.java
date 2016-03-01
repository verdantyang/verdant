package com.verdant.jtools.metadata.generic;


import com.verdant.jtools.metadata.model.PageParam;
import com.verdant.jtools.metadata.model.PageWrapper;

/**
 * Mybatis动态代理接口
 */
public interface IRepository<T> {
    /**
     * 插入对象
     */
    int insert(T model);

    /**
     * 更新对象
     */
    int update(T model);

    /**
     * 删除对象
     */
    int delete(T model);

    /**
     * 根据条件查询model对象
     */
    T get(T model);

    /**
     * 查询分页
     */
    PageWrapper<T> page(PageParam page, T model);
}
