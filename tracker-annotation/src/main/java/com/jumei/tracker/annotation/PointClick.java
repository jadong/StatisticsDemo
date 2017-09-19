package com.jumei.tracker.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by kayo on 17/9/18.
 * 点击事件埋点注解
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface PointClick {
    /**
     * 事件id
     * @return
     */
    String eventId();

    /**
     * 指向 @PointArg 注解的dataId
     * @return
     */
    int dataId() default 0;
}
