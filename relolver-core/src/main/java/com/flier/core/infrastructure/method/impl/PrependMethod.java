package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

/**
 * 在最前追加
 *
 * @author admin
 * @date 2022/02/18
 */
public class PrependMethod implements Method<String> {
    @Override
    public String doFormat(String content, Object[] args) {
        return args[0].toString().concat(content);
    }
}
