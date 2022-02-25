package com.flier.core.domain.blocks;


import com.flier.core.domain.result.Result;

/**
 * 语法块： 对对象块的解析、函数块、控制流程块、文本块的解析处理
 *
 * @author user
 */
public interface Block {

    /**
     * 标识
     *
     * @param flag
     * @return
     */
    Block setFlag(boolean flag);

    /**
     * 渲染块
     *
     * @return
     */
    Result render();

    /**
     * 内容
     *
     * @return
     */
    String text();
}
