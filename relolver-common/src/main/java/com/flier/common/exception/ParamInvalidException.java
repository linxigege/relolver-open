package com.flier.common.exception;


public class ParamInvalidException extends BaseException {

    public ParamInvalidException(String message) {
        super(message);
    }

    public ParamInvalidException() {
        super("参数异常");
    }
}
