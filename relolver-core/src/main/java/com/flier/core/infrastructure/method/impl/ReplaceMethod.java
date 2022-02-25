package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

/**
 * 替换
 *
 * @author admin
 * @date 2022/02/18
 */
public class ReplaceMethod implements Method<String> {
    @Override
    public String doFormat(String content, Object[] args) {
        return content.replaceAll(args[0].toString(), args[1].toString());
    }
}
