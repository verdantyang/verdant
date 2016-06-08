package com.verdant.jtools.common.pool.way1;

import com.verdant.jtools.common.pool.client.AbstractClient;

/**
 * 连接池-新连接构造接口
 *
 * @author verdant
 * @since 2016/06/06
 */
public interface IFactory<C extends AbstractClient> {
    C create();
}
