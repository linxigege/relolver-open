package com.flier.core.items.pipline;

import com.flier.core.domain.template.Template;
import com.flier.core.domain.template.TemplateResolver;
import com.flier.core.infrastructure.config.Configuration;
import com.flier.core.infrastructure.loader.impl.StringTemplateResourceLoader;
import org.junit.Assert;
import org.junit.Test;

/**
 * @PROJECT_NAME: relolver-master-lx
 * @DESCRIPTION:
 * @AUTHOR: lx
 * @DATE: 2022/2/22 11:43
 */
public class RightCutMethodTest {

    @Test
    public void test1() {
        String templateString = "{% assign code_file = \"/crashdumps/collect\" %}{{ code_file | right_cut: \"/\" }}";
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
    }

    @Test
    public void test2() {
        String templateString = "{% assign content = \"http:///dumps/crashdumps/collect\" | right_cut: \"/\" %}{{ content }}";
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
        Assert.assertEquals("plums, peaches, oranges, apples", "plums, peaches, oranges, apples");
    }

}
