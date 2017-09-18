package com.dong.visit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by dong on 2017/9/13.
 */
@Target(ElementType.METHOD)
public @interface SaFlag {

    String eventId() default "";

    int dataId() default 0;
}
