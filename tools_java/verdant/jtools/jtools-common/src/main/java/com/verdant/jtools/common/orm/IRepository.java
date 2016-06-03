package com.verdant.jtools.common.orm;


import com.verdant.jtools.common.orm.model.PageParam;
import com.verdant.jtools.common.orm.model.PageWrapper;

/**
 * Author: verdant
 * Create: 2016/03/14
 * Desc: ORM层动态代理接口，如Mybatis
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
     * 分页查询
     */
    PageWrapper<T> page(PageParam page, T model);
}
