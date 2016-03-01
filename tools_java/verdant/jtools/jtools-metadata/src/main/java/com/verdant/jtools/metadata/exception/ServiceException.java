package com.verdant.jtools.metadata.exception;


import com.verdant.jtools.metadata.code.ResultCode;

public class ServiceException extends Exception {

    /**
     * 异常码
     */
    private String exceptionCode;

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public ServiceException(String message) {
        super(message);
        this.setExceptionCode(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
        if (isCausedBy(cause, ServiceException.class)) {
            this.setExceptionCode(((ServiceException) cause).getExceptionCode());
        } else {
            this.setExceptionCode(ResultCode.ERROR_INNER);
        }

    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.setExceptionCode(message);
    }


    public boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
        Throwable cause = ex.getCause();
        return isCausedBy(cause, causeExceptionClasses);
    }

    public boolean isCausedBy(Throwable cause, Class<? extends Exception>... causeExceptionClasses) {
        while (cause != null) {
            for (Class<? extends Exception> causeClass : causeExceptionClasses) {
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }
}
