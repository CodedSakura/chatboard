package com.chatboard.commandparser;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.lang.reflect.Method;

import com.chatboard.annotation.Disabled;
import com.chatboard.annotation.NoAdmin;
import com.chatboard.annotation.Runner;
import com.chatboard.commands.*;
import com.chatboard.exceptions.CommandNotFoundException;
import com.chatboard.util.ParserUtils;

public enum Commands {
    
    HELP (Help.class),
    D20  (null),
    PING (Ping.class);
    
    
    
    private Commands(Class<? extends Command> c) {
        this.c = c;
    }
    
    
    
    private Class<? extends Command> c;
    
    
    
    public void run(String arguments, TextChannel tc, User u) throws Throwable {
        ParserUtils.parseAndRun(arguments, tc, u, c);
    }
    public boolean hasNoAdminMethods() {
        if(c == null) {
            return false;
        }
        if(c.isAnnotationPresent(Disabled.class)) {
            return false;
        }
        for(Method m : c.getMethods()) {
            if(m.isAnnotationPresent(Runner.class) && m.isAnnotationPresent(NoAdmin.class)) {
                if(!m.isAnnotationPresent(Disabled.class)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static Commands[] getAllCommands() {
        return Commands.class.getEnumConstants();
    }
    public static Commands getMatchingCommand(String command) throws CommandNotFoundException {
        if(command == null) {
            throw new CommandNotFoundException();
        }
        
        String c = command.split(" ")[0].toUpperCase();
        if(c.startsWith(">")) {
            c = c.substring(1);
        }
        for(Commands com : getAllCommands()) {
            if(com.toString().equals(c)) {
                return com;
            }
        }
        throw new CommandNotFoundException();
    }
    
}
