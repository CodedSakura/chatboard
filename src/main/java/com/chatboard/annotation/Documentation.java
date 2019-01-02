package com.chatboard.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
/**
 * Used on run methods of command classes
 * to retrieve their documentation
 */
public @interface Documentation {
    String syntax();
    String explanation();
}
