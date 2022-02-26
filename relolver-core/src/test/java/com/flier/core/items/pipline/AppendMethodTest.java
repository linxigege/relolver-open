package com.flier.core.items.pipline;

import com.flier.core.domain.template.Template;
import com.flier.core.domain.template.TemplateResolver;
import com.flier.core.infrastructure.config.Configuration;
import com.flier.core.infrastructure.loader.impl.StringTemplateResourceLoader;
import org.junit.Assert;
import org.junit.Test;

public class AppendMethodTest {

    @Test
    public void test1() {
        String templateString = "{{ \"/my/fancy/url\" | append: \".html\" }}";
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
        Assert.assertEquals(template.render(), "/my/fancy/url.html");
    }

    @Test
    public void testAdd() {
        String templateString = "{{ 2 | add: 3 }}";
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
    }

    @Test
    public void testOxAdd() {
        String templateString = "{{ \"0xf40000\" | replace: \"0x\",\"\" | ox_add: \"667648\" | prepend: \"0x\"}}";
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
    }


    @Test
    public void test2() {
        String templateString = "{% assign filename = \"/index.html\" %}{{ \"website.com\" | append: filename }}";
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
        Assert.assertEquals(template.render(), "website.com/index.html");
    }


    @Test
    public void test3() {
        String templateString = "" +
                "{% assign fruits = \"apples, oranges, peaches\" | split: \", \" %}" +
                "{% assign vegetables = \"carrots, turnips, potatoes\" | split: \", \" %}" +
                "{% assign everything = fruits | concat: vegetables | concat: vegetables %}" +
                "{% for item in everything %}" +
                "- {{ item }}\n" +
                "{% endfor %}";
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
    }


}