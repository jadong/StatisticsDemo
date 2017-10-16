package com.jumei.tracker.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CTR浏览事件标记
 * Created by dong on 2017/10/16.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface CTRView {

    /**
     * 参数变量名称
     */
    String value() default "";
}
