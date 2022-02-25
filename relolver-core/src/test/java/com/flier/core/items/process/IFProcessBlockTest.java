package com.flier.core.items.process;

import com.flier.core.domain.template.Template;
import com.flier.core.domain.template.TemplateResolver;
import com.flier.core.infrastructure.config.Configuration;
import com.flier.core.infrastructure.loader.impl.StringTemplateResourceLoader;
import org.junit.Test;

/**
 * @author admin
 * @date 2018/7/5
 */
public class IFProcessBlockTest {
    @Test
    public void render() throws Exception {
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve("{% if username==\"admin\" %} adsf {% endif %}{% if username!=\"admin\" %} adsf {% endif %}{% if age == 10 %} age is {{age}} {% endif %} ");
        template.bind("username", "admin");
        template.bind("age", 10);
        System.out.println(template.render());
    }

    @Test
    public void elseRender() throws Exception {
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve("{% if age == 10 %} age is {{age}} {% elsif age == 11 %} this is elseif {% else %} this is else {% endif %} ");
        template.bind("username", "admin");
        template.bind("age", 10);
        System.out.println(template.render());
    }
}