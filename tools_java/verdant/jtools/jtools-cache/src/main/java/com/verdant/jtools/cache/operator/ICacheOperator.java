package com.verdant.jtools.cache.operator;

import java.util.List;
import java.util.Map;

/**
 * 缓存操作接口
 *
 * @author verdant
 * @since 2016/11/29
 */
public interface ICacheOperator {

    public <T> T getNativeCache();

    public Boolean exist(String key);

    public void expire(String key, Integer seconds);

    public void delAll();

    //String
    public String get(String key);

    public <T> T get(String key, Class<T> clazz);

    public <T> List<T> getList(String key, Class<T> clazz);

    public <T> Map<String, T> keys(String patten, Class<T> clazz);

    public void set(String key, Object value);

    public void set(String key, Object value, Integer seconds);

    public void del(String key);

    //Hash
    public Boolean hexists(String key, Object hk);

    public Object hget(String key, Object hk);

    public Map<Object, Object> hgetall(String key);

    public void hset(String key, Object hk, Object hv);

    public void hmset(String key, Map<Object, Object> map);
}
