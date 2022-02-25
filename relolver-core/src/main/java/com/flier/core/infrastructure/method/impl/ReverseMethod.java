package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

import java.util.*;

/**
 * 反转
 *
 * @author admin
 * @date 2022/02/18
 */
public class ReverseMethod implements Method<Object> {
    @Override
    public Object doFormat(Object content, Object[] args) {
        if (content.getClass().isArray()) {
            List<String> result = new ArrayList<>();
            result.addAll(Arrays.asList((String[]) content));
            Collections.reverse(result);
            return result;
        } else if (content instanceof Collection) {
            Collection input = (Collection) content;
            Collections.reverse((List<?>) input);
            return input;
        }
        return new ArrayList<Object>();
    }
}
