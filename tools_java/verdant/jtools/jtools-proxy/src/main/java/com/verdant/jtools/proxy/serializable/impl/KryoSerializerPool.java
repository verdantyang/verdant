package com.verdant.jtools.proxy.serializable.impl;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import org.objenesis.strategy.StdInstantiatorStrategy;

/**
 * Kryo池
 *
 * @author verdant
 * @since 2016/06/02
 */
public class KryoSerializerPool {
    public static final int MAX_DEPTH = 1024;
    private static KryoPool pool;

    static {
        initialize();
    }

    public static void initialize() {
        pool = new KryoPool.Builder(new KryoFactory() {
            @Override
            public Kryo create() {
                return createKryo(MAX_DEPTH);
            }
        }).softReferences().build();
    }

    public static Kryo createKryo() {
        return createKryo(MAX_DEPTH);
    }

    public static Kryo createKryo(int maxDepth) {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        kryo.setAsmEnabled(true);
        kryo.setMaxDepth(maxDepth);
        kryo.setAutoReset(true);
        kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
        return kryo;
    }

    public static KryoPool getInstance() {
        return pool;
    }
}