package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

/**
 * 自动补全空白
 *
 * @author admin
 * @date 2022/02/18
 */
public class FillBlankMethod implements Method<Object> {
    @Override
    public Object doFormat(Object content, Object[] args) {
        Integer length = Integer.valueOf(args[0].toString());
        if (length <= 0) {
            return content;
        }
        StringBuilder c = new StringBuilder(content.toString());
        while (c.length() < length) {
            c.append(" ");
        }
        return c.toString();
    }
}
