package com.verdant.jtools.proxy.serializable;

public enum SerializerType {

    KRYO("KRYO");

    private String value;

    SerializerType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SerializerType getEnum(String value) {
        for (SerializerType type : SerializerType.values()) {
            if (type.getValue().equalsIgnoreCase(value.trim())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Not Found SerializerType value=" + value);
    }

    @Override
    public String toString() {
        return value;
    }
}