package com.verdant.jtools.proxy.codec.serialization.impl;


import com.verdant.jtools.proxy.codec.serialization.SerializerException;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;

/**
 * Kryo序列化实现
 *
 * @author verdant
 * @since 2016/06/02
 */
public class KryoSerializer {

    public static final int BUFFER_SIZE = 1024 * 1024;

    public static <T> byte[] serialize(T object) {
        if (object == null) {
            return null;
        }

        KryoPool pool = KryoSerializerPool.getInstance();
        Kryo kryo = null;

        byte[] bytes = null;
        try {
            kryo = pool.borrow();
            bytes = serialize(kryo, object);
            return bytes;
        } catch (Exception e) {
            throw new SerializerException(e.getMessage(), e);
        } finally {
            try {
                if (kryo != null) {
                    pool.release(kryo);
                }
            } catch (Exception e) {

            }
        }
    }

    public static <T> byte[] serialize(Kryo kryo, T object) {
        if (object == null) {
            return null;
        }

        byte[] bytes = null;
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
             Output output = new Output(stream)) {
            kryo.writeClassAndObject(output, object);
            output.flush();
            bytes = stream.toByteArray();
        } catch (Exception e) {
            throw new SerializerException(e.getMessage(), e);
        }
        return bytes;
    }

    public static <T> T deserialize(byte[] bytes) {
        if (ArrayUtils.isEmpty(bytes)) {
            return null;
        }

        KryoPool pool = KryoSerializerPool.getInstance();
        Kryo kryo = null;
        Object object = null;
        try {
            kryo = pool.borrow();
            object = deserialize(kryo, bytes);
            if (object == null) {
                return null;
            }
            return (T) object;
        } catch (Exception e) {
            throw new SerializerException(e.getMessage(), e);
        } finally {
            try {
                if (kryo != null) {
                    pool.release(kryo);
                }
            } catch (Exception e) {
            }
        }
    }

    public static <T> T deserialize(Kryo kryo, byte[] bytes) {
        if (ArrayUtils.isEmpty(bytes)) {
            return null;
        }

        Object object = null;
        try (ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
             Input input = new Input(stream)) {
            object = kryo.readClassAndObject(input);
            if (object == null) {
                return null;
            }
            return (T) object;
        } catch (Exception e) {
            throw new SerializerException(e.getMessage(), e);
        }
    }
}