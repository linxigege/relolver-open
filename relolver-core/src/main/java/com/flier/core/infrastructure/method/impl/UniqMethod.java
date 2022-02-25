package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 去重
 *
 * @author admin
 * @date 2022/02/18
 */
public class UniqMethod implements Method<Object> {
    @Override
    public Object doFormat(Object content, Object[] args) {
        if (content.getClass().isArray()) {
            Set<Object> result = new HashSet<Object>();
            result.addAll(Arrays.asList((Object[]) content));
            return result;
        } else if (content instanceof Collection) {
            Collection input = (Collection) content;
            return (Set<Object>) input;
        }
        return new Object();
    }
}
