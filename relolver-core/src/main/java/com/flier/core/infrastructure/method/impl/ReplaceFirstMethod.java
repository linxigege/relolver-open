package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

/**
 * 替换第一个
 *
 * @author admin
 * @date 2022/02/18
 */
public class ReplaceFirstMethod implements Method<String> {
    @Override
    public String doFormat(String content, Object[] args) {
        return content.replaceFirst(args[0].toString(), args[1].toString());
    }
}
