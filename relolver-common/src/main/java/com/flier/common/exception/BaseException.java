package com.flier.common.exception;

public class BaseException extends RuntimeException {

    protected String message;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
