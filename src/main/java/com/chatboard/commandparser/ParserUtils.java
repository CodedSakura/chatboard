package com.chatboard.commandparser;

import java.lang.reflect.Method;
import java.util.Stack;

import com.chatboard.annotation.Disabled;
import com.chatboard.annotation.NoAdmin;
import com.chatboard.annotation.Runner;

/**
 * A utility class for parsing & routing commands
 */
public class ParserUtils {

    private ParserUtils() {}
    
    
    /**
     * Finds the appropriate run method for the
     * class of the command, checks permissions
     * and executes it
     */
    public static void runner(Class<?> c, Object[] args) {
        Method[] methods = c.getMethods();
        Stack<Method> possibleMethods = new Stack();
        
        for(Method m : methods) {
            if(!m.isAnnotationPresent(Runner.class)) {
                continue;
            }
            if(m.isAnnotationPresent(Disabled.class)) {
                continue;
            }
            if(!m.isAnnotationPresent(NoAdmin.class)) {
                continue;
                // TODO Set up permission checking
            }
            
            possibleMethods.push(m);
        }
        
        if(possibleMethods.size() > 1) {
            
        }
        if(possibleMethods.size() == 0) {
            
        }
    }
    
    /**
     * Parses a given command, returning an array
     * of its arguments as a result
     */
    public static Object[] parser(String args) {
        return null;
    }

}
