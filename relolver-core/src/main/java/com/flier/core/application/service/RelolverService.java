package com.flier.core.application.service;

/**
 * @PROJECT_NAME: relolver
 * @DESCRIPTION:
 * @AUTHOR: lx
 * @DATE: 2022/2/18 13:15
 */
public interface RelolverService {

    /**
     * 根据指定模板、传入json对象进行渲染
     *
     * @param resource     传入当前项目资源路径
     * @param templateName 传入当前解析模板名称
     * @param suffix       传入当前解析模板名称后缀
     * @param jsonObj      传入json对象
     * @return
     */
    String render(String resource, String templateName, String suffix, String jsonObj);
}
