package com.flier.core.infrastructure.loader.impl;

import com.flier.core.infrastructure.loader.TemplateResourcesLoader;

public class StringTemplateResourceLoader implements TemplateResourcesLoader {
    @Override
    public String load(String source) {
        return source;
    }

    @Override
    public String getPrefix() {
        return null;
    }

    @Override
    public void setPrefix(String prefix) {
    }

    @Override
    public String getSuffix() {
        return null;
    }

    @Override
    public void setSuffix(String suffix) {
    }

    @Override
    public String getBasePath() {
        return null;
    }
}
