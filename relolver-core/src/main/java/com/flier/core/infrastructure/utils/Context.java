package com.flier.core.infrastructure.utils;

import com.flier.core.infrastructure.loader.TemplateResourcesLoader;
import com.flier.core.infrastructure.method.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * 上下文容器
 *
 * @author mac
 */
public class Context {
    private Map<String, Object> params = new HashMap<String, Object>();
    private Map<String, Method> methodMap = new HashMap<String, Method>();
    private TemplateResourcesLoader resourcesLoader;

    public Context() {
    }

    public Context(TemplateResourcesLoader resourcesLoader) {
        this.resourcesLoader = resourcesLoader;
    }

    public void bindArgs(String key, Object value) {
        params.put(key, value);
    }

    public void bindMethod(String key, Method method) {
        this.methodMap.put(key, method);
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Method getMethod(String key) {
        return methodMap.get(key);
    }

    public void unbindArgs(String itemName) {
        this.params.remove(itemName);
    }

    public TemplateResourcesLoader getResourcesLoader() {
        return resourcesLoader;
    }

    public void setResourcesLoader(TemplateResourcesLoader resourcesLoader) {
        this.resourcesLoader = resourcesLoader;
    }

    public Map<String, Method> getMethodMap() {
        return methodMap;
    }

    public void setMethodMap(Map<String, Method> methodMap) {
        this.methodMap = methodMap;
    }
}
