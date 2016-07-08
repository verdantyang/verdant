package com.verdant.jtools.proxy.center.transfer.model;

public enum TransferType {
    HTTP("HTTP"),
    HESSIAN("HESSIAN"),
    NETTY("NETTY"),
    MQ("MQ");

    private String value;

    TransferType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TransferType getEnum(String value) {
        for (TransferType type : TransferType.values()) {
            if (type.getValue().equalsIgnoreCase(value.trim())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Not Found TransferType value=" + value);
    }

    @Override
    public String toString() {
        return value;
    }
}
