package com.flier.core.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flier.common.User;
import com.flier.core.interfaces.facade.RelolverControl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TemplateTest {

    @Test
    public void string() {
        String resource = "src/main/resources/template";
        String templateName = "string";
        String suffix = ".txt";
        JSONObject object = new JSONObject() {{
            put("username", "flier");
            put("password", "123456");
        }};
        String render = RelolverControl.render(resource, templateName, suffix, JSON.toJSONString(object));
        System.out.println(render);
    }

    @Test
    public void test_if() {
        String resource = "src/main/resources/template";
        String templateName = "if";
        String suffix = ".txt";
        JSONObject object = new JSONObject() {{
            put("username", "flier");
            put("password", "123456");
        }};
        String render = RelolverControl.render(resource, templateName, suffix, JSON.toJSONString(object));
        System.out.println(render);
    }

    @Test
    public void test_if_else() {
        String resource = "src/main/resources/template";
        String templateName = "if_else";
        String suffix = ".txt";
        JSONObject object = new JSONObject() {{
            put("username", "flier");
            put("password", "123456");
        }};
        String render = RelolverControl.render(resource, templateName, suffix, JSON.toJSONString(object));
        System.out.println(render);
    }

    @Test
    public void test_for() {
        String resource = "src/main/resources/template";
        String templateName = "for";
        String suffix = ".txt";
        JSONObject object = new JSONObject();

        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("flier" + i);
            user.setAge(i);
            users.add(user);
        }
        object.put("users", users);
        String render = RelolverControl.render(resource, templateName, suffix, JSON.toJSONString(object));
        System.out.println(render);
    }

    @Test
    public void test_if_and_for() {
        String resource = "src/main/resources/template";
        String templateName = "if_and_for";
        String suffix = ".txt";
        JSONObject object = new JSONObject() {{
            put("username", "flier");
            put("password", "123456");
        }};
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("flier" + i);
            user.setAge(i);
            users.add(user);
        }
        object.put("users", users);
        String render = RelolverControl.render(resource, templateName, suffix, JSON.toJSONString(object));
        System.out.println(render);
    }

    @Test
    public void test_raw() {
        String resource = "src/main/resources/template";
        String templateName = "raw";
        String suffix = ".txt";
        JSONObject object = new JSONObject() {{
            put("username", "flier");
            put("password", "123456");
        }};
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("flier" + i);
            user.setAge(i);
            users.add(user);
        }
        object.put("users", users);
        Map<String, String> map = new HashMap<>(2);
        object.put("object", map);
        String render = RelolverControl.render(resource, templateName, suffix, JSON.toJSONString(object));
        System.out.println(render);
    }

    @Test
    public void testRender() {
        String resource = "src/main/resources/template";
        String templateName = "template";
        String suffix = ".txt";
        JSONObject object = new JSONObject() {{
            put("username", "flier");
            put("password", "123456");
        }};
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("flier" + i);
            user.setAge(i);
            users.add(user);
        }
        object.put("users", users);
        Map<String, String> map = new HashMap<String, String>(2) {{
            put("esp", "99");
            put("sad", "666");
        }};
        object.put("object", map);
        String render = RelolverControl.render(resource, templateName, suffix, JSON.toJSONString(object));
        System.out.println(render);
    }

    @Test
    public void testRender2() {
        String resource = "src/main/resources";
        String templateName = "index";
        String suffix = ".html";
        JSONObject object = new JSONObject() {{
            put("username", "flier");
            put("password", "123456");
        }};
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("flier" + i);
            user.setAge(i);
            users.add(user);
        }
        object.put("users", users);
        Map<String, String> map = new HashMap<>(2);
        object.put("object", map);
        String render = RelolverControl.render(resource, templateName, suffix, JSON.toJSONString(object));
        System.out.println(render);
    }

}