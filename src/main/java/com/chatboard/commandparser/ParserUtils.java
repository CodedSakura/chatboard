package com.chatboard.commandparser;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.chatboard.annotation.Disabled;
import com.chatboard.annotation.NoAdmin;
import com.chatboard.annotation.Runner;
import com.chatboard.exceptions.InvalidSyntaxException;

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
        Stack<Method> possibleMethods = new Stack<Method>();
        
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
    public static Object[] parser(String args) throws InvalidSyntaxException {
        Matcher m = Pattern.compile("(\\w+|(\".*?((?<!\\\\)(\"|$))))+").matcher(args);
        ArrayList<Object> out = new ArrayList<>();
        m.find();
        while (m.find()) {
            String d = m.group();
            if (d.matches("\".*?((?<!\\\\)\")")) {
                out.add(
                        d
                                .replaceAll("\\\\n", "\n")
                                .replaceAll("\\\\\"", "\"")
                                .replaceAll("^.|.$", "")
                );
            } else if (d.matches("[a-zA-Z][a-zA-Z\\d]*")) {
                out.add(new Mode(d.trim()));
            } else if (d.matches("<@\\d{18}>")) {
                out.add(d.trim()); // TODO: convert to User object
            } else if (d.matches("\\d+")) {
                out.add(Integer.parseInt(d.trim()));
            } else throw new InvalidSyntaxException("");
        }
        return out.toArray();
    }

}
