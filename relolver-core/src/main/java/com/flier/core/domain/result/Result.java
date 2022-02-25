package com.flier.core.domain.result;

/**
 * 渲染结果
 *
 * @author user
 */
public interface Result<T> {

    /**
     * 获取结果
     *
     * @return
     */
    T getResult();
}
