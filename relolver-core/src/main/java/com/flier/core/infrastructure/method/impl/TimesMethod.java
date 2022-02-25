package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

import java.math.BigDecimal;

/**
 * 乘以
 *
 * @author admin
 * @date 2022/02/18
 */
public class TimesMethod implements Method<Object> {
    @Override
    public Object doFormat(Object content, Object[] args) {
        BigDecimal first = new BigDecimal(content.toString());
        BigDecimal other = new BigDecimal(args[0].toString());
        return first.multiply(other);
    }
}
