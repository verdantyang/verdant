package com.verdant.jtools.proxy.codec.compression;


import com.verdant.jtools.proxy.codec.compression.lib.QuickLz;

/**
 * 数据压缩
 * @author verdant
 * @since 2016/06/02
 */
public class Compressor {
    public static CompressorType defaultType = CompressorType.QUICK_LZ;

    public static byte[] compress(byte[] bytes) {
        return compress(bytes, defaultType);
    }

    public static byte[] decompress(byte[] bytes) {
        return decompress(bytes, defaultType);
    }

    public static byte[] compress(byte[] bytes, CompressorType type) {
        switch (type) {
            case QUICK_LZ:
                return QuickLz.compress(bytes, 3);
            default:
                throw new CompressorException("Not Support Compressor Type : " + type);
        }
    }

    public static byte[] decompress(byte[] bytes, CompressorType type) {
        switch (type) {
            case QUICK_LZ:
                return QuickLz.decompress(bytes);
            default:
                throw new CompressorException("Not Support Compressor Type : " + type);
        }
    }

}