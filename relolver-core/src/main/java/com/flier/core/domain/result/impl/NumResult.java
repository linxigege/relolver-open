package com.flier.core.domain.result.impl;

import com.flier.core.domain.result.Result;

import java.math.BigDecimal;

public class NumResult implements Result {
    private final BigDecimal result;

    public NumResult(String result) {
        this.result = new BigDecimal(result);
    }

    @Override
    public BigDecimal getResult() {
        return result;
    }
}
