package com.verdant.jtools.common.pool;

/**
 * 连接池-回调接口
 *
 * @author verdant
 * @since 2016/06/06
 */
public interface ICallback<T, C extends AbstractClient> {
    T execute(C var);
}
