package com.chatboard.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface Restricted {
    
    @Retention(RUNTIME) @Target(METHOD)
    @interface Regex {
        public String value();
    }
    @Retention(RUNTIME) @Target(METHOD)
    @interface AllowedModes {
        public String[] value();
    }
    @Retention(RUNTIME) @Target(METHOD)
    @interface Range {
        public int min();
        public int max();
        
        public boolean inclusive() default true;
    }
    
    
    
    Regex[]        regex() default {};
    AllowedModes[] modes() default {};
    Range[]        range() default {};
    
    int index() default -1;
    
    
    
    @Retention(RUNTIME)
    @Target({METHOD})
    @interface List {
        Restricted[] value();
    }
    
}
