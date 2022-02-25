package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

/**
 * 首字母大写
 *
 * @author admin
 * @date 2022/02/18
 */
public class CapitalizeMethod implements Method<String> {
    @Override
    public String doFormat(String content, Object[] args) {
        return content.substring(0, 1).toUpperCase().concat(content.substring(1).toLowerCase());
    }
}
