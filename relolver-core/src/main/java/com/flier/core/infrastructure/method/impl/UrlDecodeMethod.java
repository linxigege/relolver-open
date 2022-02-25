package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

import java.net.URLDecoder;

/**
 * url编码
 *
 * @author admin
 * @date 2022/02/18
 */
public class UrlDecodeMethod implements Method<String> {
    @Override
    public String doFormat(String content, Object[] args) {
        return URLDecoder.decode(content);
    }
}
