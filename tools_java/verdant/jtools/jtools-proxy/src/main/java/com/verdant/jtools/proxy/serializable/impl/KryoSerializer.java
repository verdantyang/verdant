package com.verdant.jtools.proxy.serializable.impl;


import com.verdant.jtools.proxy.serializable.SerializerException;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;
import org.apache.commons.lang3.ArrayUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class KryoSerializer {

    public static final int BUFFER_SIZE = 2 * 1024;

    public static final int MAX_BUFFER_SIZE = 1024 * 1024;

    public static <T> byte[] serialize(T object) {
        return serialize(object, BUFFER_SIZE, MAX_BUFFER_SIZE);
    }

    public static <T> byte[] serialize(T object, int bufferSize, int maxBufferSize) {
        if (object == null) {
            return null;
        }

        KryoPool pool = KryoSerializerFactory.getDefaultPool();

        Kryo kryo = null;
        byte[] bytes = null;
        try {
            kryo = pool.borrow();
            bytes = serialize(kryo, object, bufferSize, maxBufferSize);
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
        return serialize(kryo, object, BUFFER_SIZE, MAX_BUFFER_SIZE);
    }

    public static <T> byte[] serialize(Kryo kryo, T object, int bufferSize, int maxBufferSize) {
        if (object == null) {
            return null;
        }

        Output output = null;
        byte[] bytes = null;
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            output = new Output(stream);//bufferSize, maxBufferSize
            kryo.writeClassAndObject(output, object);
            output.flush();
            bytes = stream.toByteArray();
        } catch (Exception e) {
            throw new SerializerException(e.getMessage(), e);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {

            } finally {
                output = null;
            }
        }

        return bytes;
    }

    public static <T> T deserialize(byte[] bytes) {
        if (ArrayUtils.isEmpty(bytes)) {
            return null;
        }

        KryoPool pool = KryoSerializerFactory.getDefaultPool();
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

        Input input = null;
        Object object = null;
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
            input = new Input(stream);
            object = kryo.readClassAndObject(input);
            if (object == null) {
                return null;
            }
            return (T) object;
        } catch (Exception e) {
            throw new SerializerException(e.getMessage(), e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (Exception e) {

            } finally {
                input = null;
            }
        }

    }
}