package com.verdant.jtools.proxy.serializable;

import com.verdant.jtools.proxy.compression.Compressor;
import com.verdant.jtools.proxy.compression.CompressorType;
import com.verdant.jtools.proxy.serializable.impl.KryoSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Serializer {
    private static final Logger logger = LoggerFactory.getLogger(Serializer.class);

    public static <T> byte[] serialize(T object) {
        return serialize(object, false);
    }

    public static <T> T deserialize(byte[] bytes) {
        return deserialize(bytes, false);
    }

    public static <T> byte[] serialize(T object, boolean compress) {
        return serialize(object, SerializerType.KRYO, compress);
    }

    public static <T> T deserialize(byte[] bytes, boolean compress) {
        return deserialize(bytes, SerializerType.KRYO, compress);
    }

    public static <T> byte[] serialize(T object, SerializerType serializerType, boolean compress) {
        byte[] bytes = null;

        if (serializerType == SerializerType.KRYO) {
            bytes = KryoSerializer.serialize(object);
        } else {
            throw new SerializerException("NotSupport SerializeType   : " + serializerType);
        }

        if (compress) {
            bytes = compress(bytes);
        }

        return bytes;
    }

    public static <T> T deserialize(byte[] bytes, SerializerType serializerType, boolean compress) {
        if (compress) {
            bytes = decompress(bytes);
        }

        T object = null;
        if (serializerType == SerializerType.KRYO) {
            object = KryoSerializer.deserialize(bytes);
        } else {
            throw new SerializerException("NotSupport SerializeType   : " + serializerType);
        }
        return object;
    }


    public static byte[] compress(byte[] bytes) {
        return Compressor.compress(bytes, CompressorType.QUICK_LZ);
    }

    public static byte[] decompress(byte[] bytes) {
        return Compressor.decompress(bytes, CompressorType.QUICK_LZ);
    }

}
