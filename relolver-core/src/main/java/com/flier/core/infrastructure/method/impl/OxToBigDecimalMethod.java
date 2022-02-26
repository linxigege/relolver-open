package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

/**
 * 16进制转 BigDecimal进行后续支持的数值计算
 *
 * @author admin
 * @date 2022/02/18
 */
public class OxToBigDecimalMethod implements Method<String> {
    @Override
    public String doFormat(String content, Object[] args) {
        Integer val16 = Integer.valueOf(content, 16);
        Integer addVal16 = Integer.valueOf(String.valueOf(args[0]), 16);
        return Integer.toHexString(val16 + addVal16);
    }
}
