package com.mustr.cluster.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MSchedule {


    /**
     * 任务名称
     * @return
     */
    String title() default "";

    
    /**
     * 调度触发的corn表达式 : 用作Job的触发器，目前只支持一个触发器表达式。
     */
    String corn();

    /**
     * 描述
     */
    String desc() default "";

    /**
     * 参数
     */
    String param() default "";
}
