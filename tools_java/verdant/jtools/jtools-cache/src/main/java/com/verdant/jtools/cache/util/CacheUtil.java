package com.verdant.jtools.cache.util;

import com.verdant.jtools.cache.operator.ICacheOperator;
import com.verdant.jtools.cache.manager.CacheManager;
import com.verdant.jtools.cache.operator.CacheOperatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;

import javax.annotation.PostConstruct;

/**
 * Author: verdant
 * Create: 2016/1/22
 * 缓存工具
 * cacheManager管理缓存平台，目前仅实现了Redis
 * Useage: CacheUtil.getInstance().getCache().xx
 */
public class CacheUtil extends ApplicationObjectSupport {

    /**
     * 默认存储前缀
     */
    private String defaultPrefix = "Default";

    private static CacheUtil INSTANCE;

    /**
     * spring cache管理类
     */
    @Autowired
    private CacheManager cacheManager;

    private CacheUtil() {
    }

    /**
     * 初始化从spring获取bean
     */
    @PostConstruct
    private void initialize() {
        INSTANCE = getApplicationContext().getBean(CacheUtil.class);
    }

    /**
     * 单例
     */
    public static CacheUtil getInstance() {
        return INSTANCE;
    }

    /**
     * 取默认Cache
     */
    public ICacheOperator getCache() {
        return getCache(defaultPrefix);
    }

    public ICacheOperator getCache(String prefixKey) {
        return getCache("default", prefixKey);
    }

    /**
     * 按管理的cache key取
     *
     * @param managerKey
     * @param prefixKey
     * @return
     */
    public ICacheOperator getCache(String managerKey, String prefixKey) {
        return CacheOperatorFactory.create(cacheManager.getManager(managerKey), prefixKey);
    }
}
