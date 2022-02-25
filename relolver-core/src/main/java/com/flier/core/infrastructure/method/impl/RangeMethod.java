package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;

import java.util.ArrayList;
import java.util.List;

/**
 * 范围截取
 *
 * @author admin
 * @date 2018/7/10
 */
public class RangeMethod implements Method<Object> {

    @Override
    public List doFormat(Object content, Object[] args) {
        List<Integer> result = new ArrayList<Integer>();
        Integer total = (Integer) content;
        for (Integer i = 0; i < total; i++) {
            result.add(i);
        }
        return result;
    }
}
