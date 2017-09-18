package com.dong.visit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by dong on 2017/9/18.
 */
@Target(ElementType.FIELD)
public @interface SaParams {

    int[] value() default {0};

}
