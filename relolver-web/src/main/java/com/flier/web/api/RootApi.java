package com.flier.web.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flier.common.User;
import com.flier.core.interfaces.facade.RelolverControl;
import com.flier.web.aop.Context;
import com.flier.web.aop.Servlet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 用这个鬼demo实现一个类  动态页面技术（jsp、freemarker）的 简单效果
 *
 * @author user
 */
@RestController("/")
public class RootApi {

    @GetMapping("/index")
    public String index() {
        String resource = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String templateName = "index";
        String suffix = ".html";
        String render = RelolverControl.render(resource, templateName, suffix, JSON.toJSONString(getParams()));
        return render;
    }

    @Servlet
    @GetMapping("/index2")
    public String index2(HttpServletRequest request) {
///        request.setAttribute("obj", getParams());
        Context.getContext().put("obj", getParams());
        return "index";
    }

    public JSONObject getParams() {
        JSONObject object = new JSONObject() {{
            put("username", "linxi");
            put("password", "123456");
        }};
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("flier" + i);
            user.setAge(i);
            users.add(user);
        }
        object.put("users", users);
        return object;
    }
}
