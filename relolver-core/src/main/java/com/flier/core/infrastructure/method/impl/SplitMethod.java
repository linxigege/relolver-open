package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

/**
 * 截取
 *
 * @author admin
 * @date 2022/02/18
 */
public class SplitMethod implements Method<String> {
    @Override
    public String[] doFormat(String content, Object[] args) {
        return content.split(args[0].toString());
    }
}
