package com.flier.core;

/**
 * @PROJECT_NAME: relolver
 * @DESCRIPTION: bug 记录
 * @AUTHOR: lx
 * @DATE: 2022/2/18 15:31
 */
public class Bug {

    /**
     * bug:
     * 1. 模板添加多了特殊符号导致部分变量解析不出来   -------------------------------
     * 2. for循环中嵌套if的话会是if解释逻辑失效
     */

    public static void main(String[] args) {
        Integer val16 = Integer.valueOf("0xf40000", 16);
        System.out.println(val16);
    }
}
