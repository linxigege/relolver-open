package com.flier.web.aop;

import java.lang.annotation.*;

/**
 * 仅充当标识
 *
 * @author user
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Servlet {
}