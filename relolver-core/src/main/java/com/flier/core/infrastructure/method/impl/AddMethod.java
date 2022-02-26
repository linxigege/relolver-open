package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

import java.math.BigDecimal;

/**
 * 数值类型相加
 *
 * @author admin
 * @date 2022/02/18
 */
public class AddMethod implements Method<BigDecimal> {
    @Override
    public BigDecimal doFormat(BigDecimal content, Object[] args) {
        return content.add((BigDecimal) args[0]);
    }
}
