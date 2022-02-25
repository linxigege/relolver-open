package com.flier.core.domain.result.impl;

import com.flier.core.domain.result.Result;

public class StringResult<String> implements Result {
    private final String result;

    public StringResult(String result) {
        this.result = result;
    }

    @Override
    public String getResult() {
        return result;
    }
}
