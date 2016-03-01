package com.verdant.jtools.cache.operator;

import java.util.List;
import java.util.Map;

public interface ICacheOperator {

    public <T> T getNativeCache();

    /**
     * 按key取string
     *
     * @param key
     * @return
     */
    public String get(String key);

    /**
     * 按key取对象
     */
    public <T> T get(String key, Class<T> clazz);

    /**
     * 按key取 List
     */
    public <T> List<T> getList(String key, Class<T> clazz);

    /**
     * 按key正则取Map
     */
    public <T> Map<String, T> keys(String patten, Class<T> clazz);

    /**
     * 按key set对象
     */
    public void set(String key, Object value);

    /**
     * 按key set对象 and 超时时间
     */
    public void set(String key, Object value, Integer seconds);

    /**
     * 按key设置过期
     */
    public void expire(String key, Integer seconds);

    /**
     * 按key删除
     */
    public void del(String key);

    /**
     * 删除所有key
     */
    public void delAll();
}
