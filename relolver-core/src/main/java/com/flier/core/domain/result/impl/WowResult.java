package com.flier.core.domain.result.impl;

import com.flier.core.domain.result.Result;

public class WowResult implements Result<String> {
    private final String result = "wow";
    private final String key;

    public WowResult(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String getResult() {
        return result;
    }
}
