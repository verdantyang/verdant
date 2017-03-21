package com.verdant.jtools.cache.manager.impl;

import com.verdant.jtools.cache.manager.ICacheManager;
import com.verdant.jtools.cache.operator.ICacheOperator;
import com.verdant.jtools.cache.operator.impl.CacheOperatorRedis;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisOperations;
/**
 * Redis缓存管理
 *
 * @author verdant
 * @since 2016/11/29
 */
public class CacheManagerRedis extends RedisCacheManager implements ICacheManager {

    @Override
    public ICacheOperator getCacheOperator(String prefix) {
        return new CacheOperatorRedis(this.getCache(prefix), prefix);
    }

    public CacheManagerRedis(boolean usePrefix, boolean transactionAware, RedisOperations redisOperations) {
        super(redisOperations);
        this.setUsePrefix(usePrefix);

        //如果需要支持事务，需设置StringRedisTemplate的supportTransaction
        this.setTransactionAware(transactionAware);
    }
}
