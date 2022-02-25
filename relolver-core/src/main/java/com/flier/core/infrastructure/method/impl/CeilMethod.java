package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

import java.math.BigDecimal;

/**
 * 向上取整
 * 1.2 => 2
 *
 * @author admin
 * @date 2022/02/18
 */
public class CeilMethod implements Method<Object> {
    @Override
    public String doFormat(Object content, Object[] args) {
        BigDecimal input = new BigDecimal(content.toString());
        return input.setScale(0, BigDecimal.ROUND_UP).longValue() + "";
    }
}
