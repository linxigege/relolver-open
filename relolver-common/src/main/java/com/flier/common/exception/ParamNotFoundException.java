package com.flier.common.exception;


public class ParamNotFoundException extends BaseException {

    public ParamNotFoundException(String message) {
        super(message);
    }

    public ParamNotFoundException() {
        super("找不到对应参数");
    }
}
