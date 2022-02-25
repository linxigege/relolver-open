package com.flier.web.aop;

import com.alibaba.fastjson.JSON;
import com.flier.core.interfaces.facade.RelolverControl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @PROJECT_NAME: relolver
 * @DESCRIPTION: 拦截处理对返回的视图进行模板渲染
 * @AUTHOR: lx
 * @DATE: 2022/2/19 9:20
 */
@Aspect
@Component
public class AopHandler {

    @Pointcut("@annotation(com.flier.web.aop.Servlet)")
    public void servletPointCut() {
    }

    @Around("servletPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
///        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
///        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
///        HttpServletRequest request = sra.getRequest();
///        Object obj = request.getAttribute("obj");

        String resource = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String suffix = ".html";
        Object result = point.proceed();
        String render = RelolverControl.render(resource, String.valueOf(result), suffix, JSON.toJSONString(Context.getContext().get("obj")));
        return render;
    }

}
