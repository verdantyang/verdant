package com.verdant.jtools.proxy.codec.serialization;


public class SerializerException extends RuntimeException {
    private static final long serialVersionUID = 5334177157885143616L;

    public SerializerException() {
        super();
    }

    public SerializerException(String message) {
        super(message);
    }

    public SerializerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializerException(Throwable cause) {
        super(cause);
    }
}