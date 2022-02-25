package com.flier.core.items.pipline;

import com.flier.core.domain.template.Template;
import com.flier.core.domain.template.TemplateResolver;
import com.flier.core.infrastructure.config.Configuration;
import com.flier.core.infrastructure.loader.impl.StringTemplateResourceLoader;
import org.junit.Test;

public class DateMethodTest {

    @Test
    public void test1() {
        String templateString = "{{ published_at | date: \"YYYY-HH-mm\" }}";
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        template.bind("published_at", "785971906000");
        System.out.println(template.render());
    }

    @Test
    public void test2() {
        String templateString = "{{ \"now\" | date: \"YYYY-HH-mm\" }}";
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        template.bind("published_at", "785971906000");
        System.out.println(template.render());
    }

}