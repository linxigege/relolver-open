package com.flier.core.infrastructure.method.impl;


import com.flier.core.infrastructure.method.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * 特殊符号转码
 *
 * @author admin
 * @date 2022/02/18
 */
public class EscapeMethod implements Method<String> {
    private static final Map<String, String> ruleMap = new HashMap<String, String>();

    public EscapeMethod() {
        ruleMap.put("&", "&amp;");
        ruleMap.put("\"", "&quot;");
        ruleMap.put("<", "&lt;");
        ruleMap.put(">", "&gt;");
    }

    @Override
    public String doFormat(String content, Object[] args) {
        for (Map.Entry<String, String> rule : ruleMap.entrySet()) {
            content = content.replaceAll(rule.getKey(), rule.getValue());
        }
        return content;
    }
}
