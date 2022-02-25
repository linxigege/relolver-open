package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

import java.net.URLEncoder;

/**
 * url解码
 *
 * @author admin
 * @date 2022/02/18
 */
public class UrlEncodeMethod implements Method<String> {
    @Override
    public String doFormat(String content, Object[] args) {
        return URLEncoder.encode(content);
    }
}
