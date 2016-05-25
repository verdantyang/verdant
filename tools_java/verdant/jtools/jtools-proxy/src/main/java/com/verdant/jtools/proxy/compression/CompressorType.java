package com.verdant.jtools.proxy.compression;


public enum CompressorType {

    QUICK_LZ("QUICK_LZ");

    private String value;

    CompressorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CompressorType getEnum(String value) {
        for (CompressorType type : CompressorType.values()) {
            if (type.getValue().equalsIgnoreCase(value.trim())) {
                return type;
            }
        }
        throw new CompressorException("Not Found Compressor Type = " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}