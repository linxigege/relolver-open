package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

/**
 * 追加
 *
 * @author admin
 * @date 2022/02/18
 */
public class RelativeUrlMethod implements Method<String> {
    @Override
    public String doFormat(String content, Object[] args) {
        return content;
    }
}
