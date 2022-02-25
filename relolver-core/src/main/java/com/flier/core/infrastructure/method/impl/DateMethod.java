package com.flier.core.infrastructure.method.impl;

import com.flier.core.infrastructure.method.Method;
import com.flier.core.infrastructure.utils.RelolverUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期
 *
 * @author admin
 * @date 2022/02/18
 */
public class DateMethod implements Method<Object> {
    @Override
    public String doFormat(Object content, Object[] args) {
        Long timestamp = null;
        if (RelolverUtil.isNum(content.toString())) {
            timestamp = Long.parseLong(content.toString());
        } else if ("now".equals(content)) {
            timestamp = System.currentTimeMillis();
        } else {
            timestamp = System.currentTimeMillis();
        }
        String formater = (String) args[0];
        SimpleDateFormat format = new SimpleDateFormat(formater);
        return format.format(new Date(timestamp));
    }
}
