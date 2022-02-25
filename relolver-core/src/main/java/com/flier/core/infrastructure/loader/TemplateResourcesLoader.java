package com.flier.core.infrastructure.loader;


public interface TemplateResourcesLoader {
    String load(String source);

    String getPrefix();

    void setPrefix(String prefix);

    String getSuffix();

    void setSuffix(String suffix);

    String getBasePath();
}
