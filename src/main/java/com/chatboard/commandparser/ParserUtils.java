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
import com.chatboard.wrapper.JDAWrapper;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

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
    public static void runner(Class<? extends Command> c, TextChannel tc, User u, Object[] args) {
        
        Command com = null;
        try {
            com = c.getConstructor().newInstance(new Object[] {});
        } catch (Exception e) {
            return;
        }
        
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
            try {
                com.setTextChannel(tc);
                com.setUser(u);
                
                m.invoke(com, args);
            } catch (Exception e) {}
            return;
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
            String d = m.group().trim();
            if (d.matches("\".*?((?<!\\\\)\")")) {
                out.add(d.replaceAll("^\"|\"$", "")
                        .replaceAll("\\\\n", "\n")
                        .replaceAll("\\\\\"", "\""));
            } else if (d.matches("[a-zA-Z][a-zA-Z\\d]*")) {
                out.add(new Mode(d));
            } else if (d.matches("<@\\d{18}>")) {
                out.add(JDAWrapper.getJDA().getUserById(d.replaceAll("^(<@)|>$", "")));
            } else if (d.matches("\\d+")) {
                out.add(Integer.parseInt(d));
            } else throw new InvalidSyntaxException("");
        }
        return out.toArray();
    }

}
