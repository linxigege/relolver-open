package com.flier.core.items.pipline;

import com.flier.core.domain.template.Template;
import com.flier.core.domain.template.TemplateResolver;
import com.flier.core.infrastructure.config.Configuration;
import com.flier.core.infrastructure.loader.impl.StringTemplateResourceLoader;
import org.junit.Assert;
import org.junit.Test;

public class ReverseMethodTest {
    @Test
    public void test1() {
        String templateString = "{% assign my_array = \"apples, oranges, peaches, plums\" | split: \", \" %}{{ my_array | reverse | join: \", \" }}";
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
        Assert.assertEquals(template.render(), "plums, peaches, oranges, apples");
    }

    @Test
    public void test2() {
        String templateString = "{{ \"Ground control to Major Tom.\" | split: \"\" | reverse | join: \"\" }}";
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
        Assert.assertEquals(template.render(), ".moT rojaM ot lortnoc dnuorG");
    }
}