package com.verdant.jtools.cache.util;

import com.verdant.jtools.cache.manager.CacheManager;
import com.verdant.jtools.cache.operator.CacheOperatorFactory;
import com.verdant.jtools.cache.operator.ICacheOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;

import javax.annotation.PostConstruct;


/**
 * 缓存工具
 * Useage: CacheUtil.getInstance().getCache().xx
 *
 * @author verdant
 * @since 2016/11/29
 */
public class CacheUtil extends ApplicationObjectSupport {

    /**
     * 默认存储前缀
     */
    private static final String DEFAULT_PREFIX = "Default";

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

    public static CacheUtil getInstance() {
        return INSTANCE;
    }

    /**
     * 取默认Cache
     */
    public ICacheOperator getCache() {
        return getCache(DEFAULT_PREFIX);
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

