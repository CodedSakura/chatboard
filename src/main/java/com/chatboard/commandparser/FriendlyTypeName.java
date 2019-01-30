package com.chatboard.commandparser;

import net.dv8tion.jda.core.entities.User;

public class FriendlyTypeName {

    private FriendlyTypeName() {}
    
    
    
    public static String getFriendlyName(Class<?> c) {
        if(c == null) {
            return "NULL";
        }
        
        if(c.equals(Integer.class)) {
            return "INTEGER";
        }
        
        if(c.equals(User.class)) {
            return "USER";
        }
        
        if(c.equals(String.class)) {
            return "STRING";
        }
        
        return "UNKNOWN";
    }

}
