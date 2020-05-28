package com.yff.sysaop;


import java.lang.annotation.*;

/*
 建立注解系统访问日志
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SysLoga {

    String value() default "";
}
