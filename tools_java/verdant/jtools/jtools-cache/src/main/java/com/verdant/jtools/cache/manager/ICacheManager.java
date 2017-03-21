package com.verdant.jtools.cache.manager;

import com.verdant.jtools.cache.operator.ICacheOperator;

/**
 * 缓存管理器接口
 *
 * @author verdant
 * @since 2016/11/29
 */
public interface ICacheManager {
    /**
     * 获取自定义的Cache实例
     *
     * @param prefix 写入key前缀
     * @return
     */
    ICacheOperator getCacheOperator(String prefix);
}
