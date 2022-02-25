package com.flier.web.aop;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @PROJECT_NAME: relolver
 * @DESCRIPTION: 上下文容器，可以利用request替代这个
 * @AUTHOR: lx
 * @DATE: 2022/2/19 9:34
 */
public class Context {

    private static ConcurrentHashMap<String, Object> context = new ConcurrentHashMap();

    public static ConcurrentHashMap<String, Object> getContext() {
        return context;
    }
}
