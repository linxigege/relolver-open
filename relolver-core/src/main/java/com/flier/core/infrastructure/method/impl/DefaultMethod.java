package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

/**
 * 追加
 *
 * @author admin
 * @date 2022/02/18
 */
public class DefaultMethod implements Method<Object> {
    @Override
    public String doFormat(Object content, Object[] args) {
        if (content == null || content.equals("") || content.equals("wow") || content.equals("false")) {
            return args[0].toString();
        }
        return content.toString();
    }
}
