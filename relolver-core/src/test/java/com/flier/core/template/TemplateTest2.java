package com.flier.core.template;

import com.flier.common.User;
import com.flier.core.domain.template.Template;
import com.flier.core.domain.template.TemplateResolver;
import com.flier.core.infrastructure.config.Configuration;
import com.flier.core.infrastructure.loader.impl.FileTemplateResourceLoader;
import com.flier.core.infrastructure.loader.impl.StringTemplateResourceLoader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @PROJECT_NAME: relolver
 * @DESCRIPTION:
 * @AUTHOR: lx
 * @DATE: 2022/2/18 15:33
 */
public class TemplateTest2 {

    @Test
    public void templateFormatTest() {
        Configuration configuration = new Configuration(new FileTemplateResourceLoader("src/main/resources"));
        TemplateResolver resolver = new TemplateResolver(configuration);
        resolver.setSuffix(".txt");
        Template template = resolver.resolve("template");
        template.bind("username", "JoinWe");
        template.bind("password", "123456");
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("name" + i);
            user.setAge(i);
            users.add(user);
        }
        template.bind("users", users);
        String result = template.render();
        System.out.println(result);
    }

    @Test
    public void renderTest() {
        User user = new User();
        user.setName("xiaoming");
        user.setAge(123);
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve("Hello my name is {{ user.name | append: \" wang \" }} and my age is {{user.age }} and my names length is {{ user.name | length }}. and my hobbies have {% for hobby in hobbies %} {{ hobby }} {% endfor %}. haha {{hobbies}} ");
        template.bind("user", user);
        template.bind("hobbies", new ArrayList<String>() {
            {
                add("足球");
                add("篮球");
                add("羽毛球");
            }
        });
        System.out.println(template.render());
    }

    @Test
    public void htmlRender() {
        Configuration configuration = new Configuration(new FileTemplateResourceLoader("src/main/resources"));
        TemplateResolver resolver = new TemplateResolver(configuration);
        resolver.setSuffix(".html");
        Template template = resolver.resolve("test");
        template.bind("username", "admin");
        template.bind("password", "密码");
        template.render();
        System.out.println(template.render());
    }

    @Test
    public void forifRender() {
        Configuration configuration = new Configuration(new FileTemplateResourceLoader("src/main/resources"));
        TemplateResolver resolver = new TemplateResolver(configuration);
        resolver.setSuffix(".html");
        Template template = resolver.resolve("index");
        template.bind("username", "lx");
        template.bind("password", "123456password");
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("name" + i);
            user.setAge(i);
            users.add(user);
        }
        template.bind("users", users);
        template.render();
        System.out.println(template.render());
    }
}
