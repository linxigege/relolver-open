package com.flier.core.infrastructure.method.impl;


import com.flier.core.infrastructure.method.Method;

/**
 * 追加换行
 *
 * @author admin
 * @date 2022/02/18
 */
public class NewlineToBrMethod implements Method<String> {
    @Override
    public String doFormat(String content, Object[] args) {
        return content.replaceAll("\n", "<br/>");
    }
}
