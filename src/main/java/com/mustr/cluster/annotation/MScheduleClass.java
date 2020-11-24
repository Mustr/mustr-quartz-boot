package com.mustr.cluster.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface MScheduleClass {

    /**
     * 任务分组，页面显示作用
     * @return
     */
    String module() default "系统";
    
    
    /**
     * 描述，提示作用
     * @return
     */
    String desc() default "";
    
}
