package com.jumei.tracker.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dong on 2017/9/19.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface PointParams {

    String value();

    /**
     * 数据变量名称
     */
    String dataName() default "";

}
