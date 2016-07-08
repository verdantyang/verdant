package com.verdant.jtools.proxy.codec.serialization;

import com.verdant.jtools.proxy.codec.compression.Compressor;
import com.verdant.jtools.proxy.codec.compression.CompressorException;
import com.verdant.jtools.proxy.codec.compression.CompressorType;
import com.verdant.jtools.proxy.codec.serialization.impl.KryoSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 序列化
 *
 * @author verdant
 * @since 2016/06/02
 */
public class Serializer {
    private static final Logger logger = LoggerFactory.getLogger(Serializer.class);

    public static SerializerType defaultSerializerType = SerializerType.KRYO;
    public static CompressorType defaultCompressorType = CompressorType.QUICK_LZ;

    public static <T> byte[] serialize(T object) {
        return serialize(object, false);
    }

    public static <T> T deserialize(byte[] bytes) {
        return deserialize(bytes, false);
    }

    public static <T> byte[] serialize(T object, boolean compress) {
        return serialize(object, defaultSerializerType, compress);
    }

    public static <T> T deserialize(byte[] bytes, boolean compress) {
        return deserialize(bytes, defaultSerializerType, compress);
    }

    public static <T> byte[] serialize(T object, SerializerType serializerType, boolean compress) throws CompressorException {
        byte[] bytes = null;

        switch (serializerType) {
            case KRYO:
                bytes = KryoSerializer.serialize(object);
                break;
            default:
                throw new SerializerException("NotSupport SerializeType   : " + serializerType);
        }

        if (compress) {
            bytes = Compressor.compress(bytes, defaultCompressorType);
        }
        return bytes;
    }

    public static <T> T deserialize(byte[] bytes, SerializerType serializerType, boolean compress) {
        if (compress) {
            bytes = Compressor.decompress(bytes, defaultCompressorType);
        }
        switch (serializerType) {
            case KRYO:
                return KryoSerializer.deserialize(bytes);
            default:
                throw new SerializerException("NotSupport SerializeType   : " + serializerType);
        }
    }
}
