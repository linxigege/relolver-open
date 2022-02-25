package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 追加
 *
 * @author admin
 * @date 2022/02/18
 */
public class JoinMethod implements Method<Object> {
    @Override
    public String doFormat(Object content, Object[] args) {
        if (content.getClass().isArray()) {
            List<String> result = new ArrayList<>();
            result.addAll(Arrays.asList((String[]) content));
            return String.join(args[0].toString(), result);
        } else if (content instanceof Collection) {
            Collection input = (Collection) content;
            return String.join(args[0].toString(), input);
        }
        return "";
    }
}
