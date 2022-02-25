package com.flier.core.infrastructure.loader.impl;

import com.flier.common.Constants;
import com.flier.core.infrastructure.loader.TemplateResourcesLoader;
import com.flier.core.infrastructure.utils.RelolverUtil;

import java.io.File;

public class FileTemplateResourceLoader implements TemplateResourcesLoader {
    private String basePath;
    /**
     * 前缀
     */
    private String prefix;

    /**
     * 后缀
     */
    private String suffix;

    public FileTemplateResourceLoader() {
        this.init();
    }

    public FileTemplateResourceLoader(String basePath) {
        this.init();
        this.basePath = basePath;
    }


    @Override
    public String load(String source) {
        String content = this.readContentFromFile(this.getFilePath(source));
        return content;
    }

    private String readContentFromFile(String filePath) {
        return RelolverUtil.readContentFromFile(filePath, Constants.CHAR_SET_UTF_8);
    }

    /**
     * 根据 basepath suffix prefix sources 等拼接文件路径
     *
     * @param source
     * @return
     */
    private String getFilePath(String source) {
        String filePath = this.basePath + File.separator;
        if (this.prefix != null && this.prefix.trim().length() > 0) {
            filePath += this.prefix;
            filePath += File.separator;
        }
        filePath += source;
        if (this.suffix != null && this.suffix.trim().length() > 0) {
            filePath += this.suffix;
        }
        return filePath;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String getSuffix() {
        return this.suffix;
    }

    @Override
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String getBasePath() {
        return this.basePath;
    }

    private void init() {
        this.prefix = Constants.TEMPLATE_PREFIX;
        this.suffix = Constants.TEMPLATE_SUFFIX;
    }
}
