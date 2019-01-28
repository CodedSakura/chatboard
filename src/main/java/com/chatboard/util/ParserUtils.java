package com.chatboard.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.chatboard.annotation.ArgNames;
import com.chatboard.annotation.Disabled;
import com.chatboard.annotation.NoAdmin;
import com.chatboard.annotation.Restricted;
import com.chatboard.annotation.Runner;
import com.chatboard.exceptions.CommandNotFoundException;
import com.chatboard.exceptions.InvalidParametersException;
import com.chatboard.exceptions.InvalidSyntaxException;
import com.chatboard.exceptions.PermissionException;
import com.chatboard.exceptions.RestrictionException;
import com.chatboard.wrapper.JDAWrapper;
import com.chatboard.commandparser.Command;
import com.chatboard.commandparser.FriendlyTypeName;
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
        
        // Creates a stack to store appropriate methods in
        Stack<Method> stack = new Stack<>();
        
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
            
            // Pushes the method onto the stack
            stack.push(m);
            
        }
        
        if(stack.isEmpty()) {
            // The command was found, but the parameters did not match
            InvalidParametersException ipe = new InvalidParametersException();
            ipe.setFoundParameters(argTypes);
            throw ipe;
        }
        
        // Stores whether the user is an admin in a boolean value for easy access
        final boolean userIsAdmin = RoleUtil.isAdmin(u);
        
        // Sorts the stack, prioritising the appropriate permissions
        stack.sort((Method m1, Method m2) -> {
            boolean m1IsAdmin = m1.isAnnotationPresent(NoAdmin.class);
            return userIsAdmin ? (m1IsAdmin ? 1 : -1) : (m1IsAdmin ? -1 : 1);
        });
        
        // Loops through the appropriate methods
        for(Method m : stack) {
            
            // If the method does not match the restrictions, skips it
            if(!matchesRestrictions(m, args)) {
                continue;
            }
            
            // If a non-admin is trying to execute an admin command,
            // stops them
            if(!m.isAnnotationPresent(NoAdmin.class)) {
                if(!userIsAdmin) {
                    throw new PermissionException();
                }
            }
            
            // Executes the command
            com.setTextChannel(tc);
            com.setUser(u);
            
            try {
                  m.invoke(com, args);
            } catch (IllegalAccessException | IllegalArgumentException e) {
                return;
            } catch(InvocationTargetException e) {
                throw e.getTargetException();
            }
            
            // Terminates method
            return;
            
        }
        
        // No method was found, that fit the given restrictions
        String message = "Try the following:\n";
        for(Method m : stack) {
            
            if(!userIsAdmin && !m.isAnnotationPresent(NoAdmin.class)) {
                continue;
            }
            
            message += ">";
            message += c.getSimpleName().toLowerCase();
            message += " ";
            
            ArgNames argNames = m.getAnnotation(ArgNames.class);
            String[] names    = (argNames == null) ? new String[] {} : argNames.names();
            
            Class<?>[] aTypes = m.getParameterTypes();
            for(int i = 0; i < aTypes.length; i++) {
                String param = "";
                
                param += "<";
                param += FriendlyTypeName.getFriendlyName(aTypes[i]);
                if(i < names.length) {
                    param += " ";
                    param += names[i];
                }
                
                int index = 0;
                
                if(m.isAnnotationPresent(Restricted.class)) {
                    for(Restricted r : m.getAnnotationsByType(Restricted.class)) {
                        if(r.index() == i || index == i) {
                            
                            if(aTypes[i].equals(Integer.class)) {
                                if(r.range().length > 0) {
                                    Restricted.Range range = r.range()[0];
                                    
                                    param += " ";
                                    param += "[" + range.min() + "-" + range.max() + "]";
                                }
                            }
                            
                            if(aTypes[i].equals(Mode.class)) {
                                if(r.modes().length > 0) {
                                    Restricted.AllowedModes modes = r.modes()[0];
                                    
                                    param += " ";
                                    boolean first = true;
                                    for(String s : modes.value()) {
                                        if(first) {
                                            first = false;
                                        } else {
                                            param += "|";
                                        }
                                        
                                        param += s;
                                    }
                                }
                            }
                            break;
                        }
                        index++;
                    }
                }
                
                param   += ">";
                message += param + " ";
            }
            
        }
        
        RestrictionException re = new RestrictionException(message);
        throw re;
        
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
    
    public static boolean matchesRestrictions(Method m, Object[] args) {
        
        Class<?>[] argTypes = new Class[args.length];
        
        for(int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        
        Restricted[] restrictions = m.getAnnotationsByType(Restricted.class);
        for(int i = 0; i < restrictions.length; i++) {
            Restricted r = restrictions[i];
            int index = r.index() >= 0 ? r.index() : i;
            
            if(index >= args.length) {
                continue;
            }
            
            if(r.modes().length != 0) {
                if(argTypes[index] == Mode.class) {
                    String s = ((Mode) (args[index])).getMode();
                    Restricted.AllowedModes modes = r.modes()[0];
                    
                    boolean modeMatches = false;
                    for(String mode : modes.value()) {
                        if(mode.equalsIgnoreCase(s)) {
                            modeMatches = true;
                            break;
                        }
                    }
                    
                    if(!modeMatches) {
                        return false;
                    }
                    
                }
                continue;
            }
            
            if(r.range().length != 0) {
                if(argTypes[index] == Integer.class) {
                    int in = ((int) (args[index]));
                    Restricted.Range range = r.range()[0];
                    
                    boolean inclusive = range.inclusive();
                    int min = range.min(), max = range.max();
                    if(!(in > min && in < max)) {
                        if(!(inclusive && (in == min || in == max))) {
                            return false;
                        }
                    }
                    
                }
                continue;
            }
            
        }
        
        return true;
    }

}
