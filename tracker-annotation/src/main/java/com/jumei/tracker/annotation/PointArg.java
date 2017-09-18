package com.jumei.tracker.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by kayo on 17/9/18.
 * 参数注解
 * value 为了可以使用value特权 所有没有 用dataId名称，用value
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface PointArg {
    String[] value() default {};
}
