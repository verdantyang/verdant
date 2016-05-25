package com.verdant.jtools.proxy.serializable;


public class SerializerException extends RuntimeException {
    private static final long serialVersionUID = -6478265600135929954L;

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