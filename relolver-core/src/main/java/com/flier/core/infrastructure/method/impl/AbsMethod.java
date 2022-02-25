package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

import java.math.BigDecimal;

/**
 * 取绝对值
 *
 * @author admin
 * @date 2022/02/18
 */
public class AbsMethod implements Method<Object> {
    @Override
    public String doFormat(Object content, Object[] args) {
        BigDecimal input = new BigDecimal(content.toString());
        return input.abs().toString();
    }
}
