package com.flier.core.items.process;

import com.flier.core.domain.template.Template;
import com.flier.core.domain.template.TemplateResolver;
import com.flier.core.infrastructure.config.Configuration;
import com.flier.core.infrastructure.loader.impl.StringTemplateResourceLoader;
import org.junit.Test;

public class AssignProcessBlockTest {

    @Test
    public void render() {
        // 设置全局域参数
        String templateString = "{% assign __path = 123 %} {{ __path }} {% assign username = uname %} {{ username }}";
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        template.bind("uname", "bob");
        System.out.println(template.render());
    }
}