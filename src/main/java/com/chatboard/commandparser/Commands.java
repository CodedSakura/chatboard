package com.chatboard.commandparser;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import com.chatboard.commands.*;
import com.chatboard.exceptions.CommandNotFoundException;

public enum Commands {
    
    HELP (null),
    D20  (null),
    PING (Ping.class);
    
    
    
    private Commands(Class<? extends Command> c) {
        this.c = c;
    }
    
    
    
    private Class<? extends Command> c;
    
    
    
    public void run(String arguments, TextChannel tc, User u) {
        ParserUtils.parseAndRun(arguments, tc, u, c);
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
