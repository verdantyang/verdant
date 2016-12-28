package com.verdant.jtools.cache.operator;

import com.verdant.jtools.cache.manager.ICacheManager;
import com.verdant.jtools.cache.operator.impl.CacheOperatorRedis;
import org.springframework.data.redis.cache.RedisCacheManager;

/**
 * 缓存处理器工厂
 *
 * @author verdant
 * @since 2016/11/29
 */
public class CacheOperatorFactory {

    public static ICacheOperator create(ICacheManager cache, String prefix) {
        if (cache instanceof RedisCacheManager) {
            return new CacheOperatorRedis(((RedisCacheManager) cache).getCache(prefix), prefix);
        }
        return null;
    }

}
