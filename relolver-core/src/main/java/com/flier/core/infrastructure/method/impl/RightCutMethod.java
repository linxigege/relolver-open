package com.flier.core.infrastructure.method.impl;

import com.flier.common.StringConveyor;
import com.flier.core.infrastructure.method.Method;

/**
 * 从右边截取从 x 字符到结束
 *
 * @author admin
 * @date 2022/02/18
 */
public class RightCutMethod implements Method<String> {

    @Override
    public String doFormat(String content, Object[] args) {
        return new StringConveyor(content).rightCut(args[0].toString());
    }

}
