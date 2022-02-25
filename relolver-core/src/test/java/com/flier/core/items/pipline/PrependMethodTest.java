package com.flier.core.items.pipline;

import com.flier.core.domain.template.Template;
import com.flier.core.domain.template.TemplateResolver;
import com.flier.core.infrastructure.config.Configuration;
import com.flier.core.infrastructure.loader.impl.StringTemplateResourceLoader;
import org.junit.Assert;
import org.junit.Test;

public class PrependMethodTest {
    @Test
    public void test1() {
        String templateString = "{{ \"apples, oranges, and bananas\" | prepend: \"Some fruit: \" }}";
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
        Assert.assertEquals(template.render(), "Some fruit: apples, oranges, and bananas");
    }

    @Test
    public void test2() {
        String templateString = "{% assign url = \"liquidmarkup.com\" %}" +
                "{{ \"/index.html\" | prepend: url }}";
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
        Assert.assertEquals(template.render(), "liquidmarkup.com/index.html");
    }
}