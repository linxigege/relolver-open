package com.flier.core.items.pipline;

import com.flier.core.domain.template.Template;
import com.flier.core.domain.template.TemplateResolver;
import com.flier.core.infrastructure.config.Configuration;
import com.flier.core.infrastructure.loader.impl.StringTemplateResourceLoader;
import org.junit.Test;

public class SplitMethodTest {

    @Test
    public void test1() {
        String templateString = "{% assign beatles = \"John, Paul, George, Ringo\" | split: \", \" %}\n" +
                "\n" +
                "{% for member in beatles %}\n" +
                "  {{ member }}\n" +
                "{% endfor %}";
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
    }

    @Test
    public void test2() {
        String templateString = "{% assign beatles = \"J\" | fill_blank: 3 %}{{beatles}}";
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
    }
}