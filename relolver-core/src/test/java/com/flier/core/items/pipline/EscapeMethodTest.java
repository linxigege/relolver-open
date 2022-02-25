package com.flier.core.items.pipline;

import com.flier.core.domain.template.Template;
import com.flier.core.domain.template.TemplateResolver;
import com.flier.core.infrastructure.config.Configuration;
import com.flier.core.infrastructure.loader.impl.StringTemplateResourceLoader;
import org.junit.Assert;
import org.junit.Test;

public class EscapeMethodTest {
    @Test
    public void test1() {
        String templateString = "{{ \"<a></a>\" | escape }}";
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
        Assert.assertEquals(template.render(), "&lt;a&gt;&lt;/a&gt;");
    }
}