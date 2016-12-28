package com.verdant.jtools.cache.operator.impl;

import com.verdant.jtools.cache.operator.ICacheOperator;
import com.verdant.jtools.cache.util.CacheHelper;
import org.springframework.cache.Cache;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存处理器
 * Cache structure:  prefix:key
 *
 * @author verdant
 * @since 2016/11/29
 */
public class CacheOperatorRedis implements ICacheOperator {

    private Cache cache;
    private String prefix;

    public CacheOperatorRedis(Cache cache, String prefix) {
        this.cache = cache;
        this.prefix = prefix;
    }

    @Override
    public StringRedisTemplate getNativeCache() {
        return (StringRedisTemplate) cache.getNativeCache();
    }

    @Override
    public Boolean exist(String key) {
        return getNativeCache().hasKey(key);
    }

    @Override
    public void expire(String key, Integer seconds) {
        if (seconds != null && seconds > 0) {
            getNativeCache().expire(CacheHelper.getKey(prefix, key), seconds, TimeUnit.SECONDS);
        }
    }

    @Override
    public void del(String key) {
        getNativeCache().delete(CacheHelper.getKey(prefix, key));
    }

    @Override
    public void delAll() {
        getNativeCache().delete(getNativeCache().keys(CacheHelper.getKey(prefix, "*")));
    }

    @Override
    public String get(String key) {
        return get(key, String.class);
    }

    //String
    @Override
    public <T> T get(String key, Class<T> clazz) {
        return (T) CacheHelper.deserialize(getNativeCache().opsForValue().get(CacheHelper.getKey(prefix, key)), clazz);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clazz) {
        return (List<T>) CacheHelper.deserialize(getNativeCache().opsForValue().get(CacheHelper.getKey(prefix, key)), clazz);
    }

    @Override
    public <T> Map<String, T> keys(String patten, Class<T> clazz) {
        Map<String, T> result = null;
        result = new HashMap<>();
        Set<String> keys = getNativeCache().keys(CacheHelper.getKey(prefix, patten));
        for (String key : keys) {
            key = key.replaceAll(CacheHelper.getKey(prefix, ""), "");
            result.put(key, this.get(key, clazz));
        }
        return result;
    }

    @Override
    public void set(String key, Object value) {
        getNativeCache().opsForValue().set(CacheHelper.getKey(prefix, key), CacheHelper.serialize(value));
    }

    @Override
    public void set(String key, Object value, Integer seconds) {
        if (seconds != null && seconds > 0) {
            getNativeCache().opsForValue().set(CacheHelper.getKey(prefix, key), CacheHelper.serialize(value), seconds, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }

    //Hash
    @Override
    public Boolean hexists(String key, Object hk) {
        return getNativeCache().opsForHash().hasKey(CacheHelper.getKey(prefix, key), hk);
    }

    @Override
    public Object hget(String key, Object hk) {
        return getNativeCache().opsForHash().get(CacheHelper.getKey(prefix, key), hk);
    }

    @Override
    public Map<Object, Object> hgetall(String key) {
        return getNativeCache().opsForHash().entries(CacheHelper.getKey(prefix, key));
    }

    @Override
    public void hset(String key, Object hk, Object hv) {
        getNativeCache().opsForHash().put(CacheHelper.getKey(prefix, key), hk, hv);
    }

    @Override
    public void hmset(String key, Map<Object, Object> map) {
        getNativeCache().opsForHash().putAll(CacheHelper.getKey(prefix, key), map);
    }
}
