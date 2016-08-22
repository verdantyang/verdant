package com.verdant.demo.common.thread.container;

import java.util.HashMap;
import java.util.Map;

/**
 * CopyOnWriteMap简单实现
 *
 * @author verdant
 * @since 2016/08/01
 */
public class CopyOnWriteMapImpl<K, V> extends HashMap<K, V> implements Cloneable {

    private volatile Map<K, V> internalMap;

    public CopyOnWriteMapImpl() {
        internalMap = new HashMap<>();
    }

    public V put(K key, V value) {
        synchronized (this) {
            Map<K, V> newMap = new HashMap<>(internalMap);
            V val = newMap.put(key, value);
            internalMap = newMap;
            return val;
        }
    }

    public V get(Object key) {
        return internalMap.get(key);
    }

    public void putAll(Map<? extends K, ? extends V> newData) {
        synchronized (this) {
            Map<K, V> newMap = new HashMap<>(internalMap);
            newMap.putAll(newData);
            internalMap = newMap;
        }
    }
}
