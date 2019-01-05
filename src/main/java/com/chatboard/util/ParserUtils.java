package com.chatboard.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.chatboard.annotation.Disabled;
import com.chatboard.annotation.NoAdmin;
import com.chatboard.annotation.Runner;
import com.chatboard.exceptions.CommandNotFoundException;
import com.chatboard.exceptions.InvalidParametersException;
import com.chatboard.exceptions.InvalidSyntaxException;
import com.chatboard.exceptions.PermissionException;
import com.chatboard.wrapper.JDAWrapper;
import com.chatboard.commandparser.Command;
import com.chatboard.commandparser.Mode;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * A utility class for parsing & routing commands
 */
public class ParserUtils {

    private ParserUtils() {}
    
    
    /**
     * Handles both the parsing and execution of a command
     */
    public static void parseAndRun(String arguments, TextChannel tc, User u, Class<? extends Command> c) throws Throwable {
        Object[] args;
        
        args = parser(arguments);
        runner(c, tc, u, args);
        
    }
    
    /**
     * Finds the appropriate runner method for the
     * class of the command, checks permissions
     * and executes it
     */
    public static void runner(Class<? extends Command> c, TextChannel tc, User u, Object[] args) throws Throwable {
        
        // Creates an instance of the given command class
        Command com = null;
        try {
            com = c.getConstructor().newInstance(new Object[] {});
        } catch (Exception e) {
            return;
        }
        
        // If the whole command class is marked as disabled,
        // consider the command as non-existent
        if(c.isAnnotationPresent(Disabled.class)) {
            throw new CommandNotFoundException();
        }
        
        // Stores the parameter types of the passed arguments
        // in an array for future comparison
        Class<?>[] argTypes = new Class[args.length];
        
        // Fills the array
        for(int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        
        // Retrieves all methods of a class
        Method[] methods = c.getMethods();
        
        for(Method m : methods) {
            
            // If a method is not a runner method - ignores it
            if(!m.isAnnotationPresent(Runner.class)) {
                continue;
            }
            
            // Checks if the method is disabled - if so, ignores it
            if(m.isAnnotationPresent(Disabled.class)) {
                continue;
            }
            
            // Checks if the parameter types match
            if(!Arrays.equals(argTypes, m.getParameterTypes())) {
                continue;
            }
            
            // If the "NoAdmin" annotation is NOT present,
            // checks if the user is an admin - if not,
            // access is denied
            if(!m.isAnnotationPresent(NoAdmin.class)) {
                if(!RoleUtil.isAdmin(u)) {
                    throw new PermissionException();
                }
            }
            
            // Tries to execute the command
            com.setTextChannel(tc);
            com.setUser(u);
            try {
                  m.invoke(com, args);
            } catch (IllegalAccessException | IllegalArgumentException e) {
                return;
            } catch(InvocationTargetException e) {
                throw e.getTargetException();
            }
            
            return;
        }
        
        // The command was found, but the parameters did not match
        InvalidParametersException ipe = new InvalidParametersException();
        ipe.setFoundParameters(argTypes);
        throw ipe;
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
