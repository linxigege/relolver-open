package com.flier.core.domain.template;

import com.flier.common.Constants;
import com.flier.core.infrastructure.cache.Cache;
import com.flier.core.infrastructure.cache.CacheBuilder;
import com.flier.core.infrastructure.config.Configuration;

/**
 * 模板解析器
 *
 * @author user
 */
public class TemplateResolver {
    private final Configuration configuration;
    private Cache templateCache;
    private String contentType;

    public TemplateResolver(Configuration configuration) {
        this.configuration = configuration;
        if (Constants.TEMPLATE_CACHE) {
            this.templateCache = CacheBuilder.getOrCreateCache(Constants.KEY_TEMPLATE_CACHE);
        }
    }

    /**
     * 根据 source 构建一个 template
     *
     * @param source
     * @return
     */
    public Template resolve(String source) {
        Template template = null;
        if (Constants.TEMPLATE_CACHE) {
            /// 先把缓存的功能去掉
            /// template = (Template) templateCache.get(source);
        }
        if (template == null) {
            template = new Template(this.configuration, source);
            if (this.contentType != null && this.contentType.length() > 0) {
                template.setContentType(this.contentType);
            }
        }
        if (Constants.TEMPLATE_CACHE) {
///            templateCache.cache(source, template, Constants.KEY_TEMPLATE_TIMEOUT_MILLISECOND);
        }
        return template;
    }

    public void setPrefix(String prefix) {
        this.configuration.getResourcesLoader().setPrefix(prefix);
    }

    public void setSuffix(String suffix) {
        this.configuration.getResourcesLoader().setSuffix(suffix);
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}