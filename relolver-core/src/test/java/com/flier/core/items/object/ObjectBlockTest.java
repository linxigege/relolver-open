package com.flier.core.items.object;

import com.flier.common.Constants;
import com.flier.common.User;
import com.flier.core.domain.blocks.object.ObjectBlock;
import com.flier.core.domain.template.Template;
import com.flier.core.domain.template.TemplateResolver;
import com.flier.core.infrastructure.config.Configuration;
import com.flier.core.infrastructure.loader.impl.StringTemplateResourceLoader;
import com.flier.core.infrastructure.method.impl.AppendMethod;
import com.flier.core.infrastructure.method.impl.LengthMethod;
import com.flier.core.infrastructure.utils.Context;
import org.junit.Assert;
import org.junit.Test;


public class ObjectBlockTest {
    @Test
    public void render() throws Exception {
        User user = new User();
        user.setName("xiaoming");
        Context context = new Context();
        context.bindArgs("user", user);
        String template = "{{ user.name }}";
        ObjectBlock objectBlock = new ObjectBlock(context, template);
        String result = objectBlock.render().getResult();
        Assert.assertEquals(result, "xiaoming");
    }

    @Test
    public void testPipline() throws Exception {
        User user = new User();
        user.setName("xiaoming");
        user.setAge(123);

        User user1 = new User();
        user1.setName("baobo");
        user1.setAge(456);

        Context context = new Context();
        context.bindArgs("user", user);
        context.bindArgs("user1", user1);
        context.bindMethod(Constants.APPEND, new AppendMethod());
        String template = "{{ user.name | append: \" === \" | append: user1.name}}";
        ObjectBlock objectBlock = new ObjectBlock(context, template);
        String result = objectBlock.render().getResult();
        System.out.println(result);
        Assert.assertEquals(result, "xiaoming === baobo");
    }

    @Test
    public void lengthTest() throws Exception {
        User user = new User();
        user.setName("xiaoming");
        Context context = new Context();
        context.bindArgs("user", user);
        context.bindMethod("length", new LengthMethod());
        String template = "{{ user.name | length }}";
        ObjectBlock objectBlock = new ObjectBlock(context, template);
        String result = objectBlock.render().getResult();
        System.out.println(result);
        Assert.assertEquals(Integer.parseInt(result), 8);
    }

    @Test
    public void booleanTest() throws Exception {
        Context context = new Context();
        String template = "{{ has }}";
        context.bindArgs("has", false);
        ObjectBlock objectBlock = new ObjectBlock(context, template);
        String result = objectBlock.render().getResult();
        System.out.println(result);
        Assert.assertEquals(result, "false");
    }

    @Test
    public void arrTest() throws Exception {
        Context context = new Context();
        String template = "{{ has.first }}";
        String[] hass = new String[]{
                "1",
                "2",
                "3"
        };
        context.bindArgs("has", hass);
        ObjectBlock objectBlock = new ObjectBlock(context, template);
        String result = objectBlock.render().getResult();
        System.out.println(result);
        Assert.assertEquals(result, "1");
    }

    @Test
    public void arrFromIndexTest() throws Exception {
        Context context = new Context();
        String templateString = "{{ has[0] }}";
        String[] hass = new String[]{
                "1",
                "2",
                "3"
        };
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        template.bind("has", hass);
        System.out.println(template.render());
        Assert.assertEquals(template.render(), "1");
    }

    @Test
    public void objectFromIndexTest() throws Exception {
        Context context = new Context();
        String templateString = "{{ user[namekey] | append: \" is \" | append: user.age }}";
        User user = new User();
        user.setName("wangerxiao");
        user.setAge(102);
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        template.bind("user", user);
        template.bind("namekey", "name");
        System.out.println(template.render());
        Assert.assertEquals(template.render(), "wangerxiao is 102");
    }
}