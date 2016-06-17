package com.verdant.jtools.proxy.codec.compression;


public class CompressorException extends RuntimeException {
    private static final long serialVersionUID = 992603806768778271L;

    public CompressorException() {
        super();
    }

    public CompressorException(String message) {
        super(message);
    }

    public CompressorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompressorException(Throwable cause) {
        super(cause);
    }
}