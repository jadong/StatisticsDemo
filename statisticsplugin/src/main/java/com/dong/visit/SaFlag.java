package com.dong.visit;
<<<<<<< HEAD
=======

>>>>>>> 638a779bfaa050f61283e272dea2bcd76e9750e5
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
<<<<<<< HEAD
 * Created by dong on 2017/9/7.
 */
@Target(ElementType.METHOD)
public @interface SaFlag {
=======
 * Created by dong on 2017/9/13.
 */
@Target(ElementType.METHOD)
public @interface SaFlag {

    String eventId() default "";

    int dataId() default 0;
>>>>>>> 638a779bfaa050f61283e272dea2bcd76e9750e5
}
