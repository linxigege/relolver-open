package com.flier.core.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flier.core.application.service.RelolverService;
import com.flier.core.domain.template.Template;
import com.flier.core.domain.template.TemplateResolver;
import com.flier.core.infrastructure.config.Configuration;
import com.flier.core.infrastructure.loader.impl.FileTemplateResourceLoader;

/**
 * @PROJECT_NAME: relolver
 * @DESCRIPTION:
 * @AUTHOR: lx
 * @DATE: 2022/2/18 13:16
 */
public class RelolverServiceImpl implements RelolverService {
    @Override
    public String render(String resource, String templateName, String suffix, String jsonObj) {
        Configuration configuration = new Configuration(new FileTemplateResourceLoader(resource));
        TemplateResolver resolver = new TemplateResolver(configuration);
        resolver.setSuffix(suffix);
        Template template = resolver.resolve(templateName);
        JSONObject jsonObject = JSON.parseObject(jsonObj);
        jsonObject.keySet().stream().forEach(key -> template.bind(key, jsonObject.get(key)));
        return template.render();
    }
}
