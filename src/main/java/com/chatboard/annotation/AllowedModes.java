package com.chatboard.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Repeatable(value = AllowedModes.AllowedModesList.class)
@Retention(RUNTIME)
@Target(METHOD)
public @interface AllowedModes {
    
    String[] modes();
    
    @Retention(RUNTIME)
    @Target({METHOD})
    @interface AllowedModesList {
        AllowedModes[] value();
    }
    
}
