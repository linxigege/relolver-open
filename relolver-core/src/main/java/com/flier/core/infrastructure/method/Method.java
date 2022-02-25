package com.flier.core.infrastructure.method;

/**
 * 函数块，对应常用的一些函数，如：求绝对值、追加字符串、替换字符串、日期格式化等等
 *
 * @param <U>
 * @author user
 */
public interface Method<U> {
    /**
     * 根据函数语义转化
     *
     * @param content
     * @param args
     * @param <T>
     * @return
     */
    <T> T doFormat(U content, Object... args);
}
