package com.verdant.jtools.cache.operator;

import com.verdant.jtools.cache.manager.ICacheManager;
import com.verdant.jtools.cache.operator.impl.CacheOperatorRedis;
import org.springframework.data.redis.cache.RedisCacheManager;

public class CacheOperatorFactory {

    public static ICacheOperator create(ICacheManager cache, String prefix) {
        if (cache instanceof RedisCacheManager) {
            return new CacheOperatorRedis(((RedisCacheManager) cache).getCache(prefix), prefix);
        }
        return null;
    }

}
