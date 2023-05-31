package net.amygdalum.allotropy.fluent.canvas;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface Canvas {
    boolean print() default false;

    int width() default 10;

    int height() default 10;
}
