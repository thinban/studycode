package com.example.thinbanrest.aop;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * <p>加了此注解的接口将打印访问日志<p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogIt {
    boolean noHeader() default true;
}