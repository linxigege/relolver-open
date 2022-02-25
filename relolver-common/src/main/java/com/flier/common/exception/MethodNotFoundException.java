package com.flier.common.exception;


public class MethodNotFoundException extends BaseException {

    public MethodNotFoundException(String message) {
        super(message);
    }

    public MethodNotFoundException() {
        super("找不到对应方法");
    }
}
