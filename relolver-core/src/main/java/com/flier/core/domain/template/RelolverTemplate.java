package com.flier.core.domain.template;

/**
 * 模板类
 *
 * @author user
 */
public interface RelolverTemplate {

    /**
     * 绑定参数到全局域
     *
     * @param key
     * @param value
     */
    void bind(String key, Object value);

    /**
     * 根据全局域参数渲染模板
     *
     * @return
     */
    String render();
}
