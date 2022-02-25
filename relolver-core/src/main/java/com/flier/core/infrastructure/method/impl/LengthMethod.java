package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;


public class LengthMethod implements Method<Object> {
    @Override
    public Integer doFormat(Object content, Object[] args) {
        if (content instanceof String) {
            return ((String) content).length();
        } else if (content instanceof Collection) {
            return ((Collection) content).size();
        } else if (content instanceof Object[]) {
            Object[] objects = (Object[]) content;
            return objects.length;
        } else {
            Class<? extends Object> clazz = content.getClass();
            try {
                java.lang.reflect.Method lengthMethod = clazz.getMethod("length");
                return (Integer) lengthMethod.invoke(content);
            } catch (NoSuchMethodException e) {
                try {
                    java.lang.reflect.Method sizeMethod = clazz.getMethod("size");
                    return (Integer) sizeMethod.invoke(content);
                } catch (NoSuchMethodException e1) {
                    e.printStackTrace();
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
