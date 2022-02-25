package com.flier.core.domain.result.impl;

import com.flier.core.domain.result.Result;

public class ObjectResult<Object> implements Result {
    private final Object result;

    public ObjectResult(Object result) {
        this.result = result;
    }

    @Override
    public Object getResult() {
        return result;
    }
}
