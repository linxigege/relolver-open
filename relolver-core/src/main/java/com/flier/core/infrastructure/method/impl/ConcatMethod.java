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
public class ConcatMethod implements Method<Object> {
    @Override
    public Object doFormat(Object content, Object[] args) {
        if (content.getClass().isArray()) {
            List<Object> result = new ArrayList<Object>();
            result.addAll(Arrays.asList((Object[]) content));
            result.addAll(this.asList(args[0]));
            return result;
        } else if (content instanceof Collection) {
            Collection input = (Collection) content;
            Collection other = this.asList(args[0]);
            input.addAll(other);
            return input;
        }
        return new Object();
    }

    private Collection<?> asList(Object args) {
        if (args.getClass().isArray()) {
            return Arrays.asList((Object[]) args);
        } else if (args instanceof Collection) {
            return (Collection<?>) args;
        }
        return Arrays.asList(args);
    }
}
