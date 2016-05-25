package com.verdant.jtools.proxy.compression;


import com.verdant.jtools.proxy.compression.impl.QuickLz;

public class Compressor {

    public static byte[] compress(byte[] bytes) {
        return compress(bytes, CompressorType.QUICK_LZ);
    }

    public static byte[] decompress(byte[] bytes) {
        return decompress(bytes, CompressorType.QUICK_LZ);
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