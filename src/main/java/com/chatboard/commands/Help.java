package com.chatboard.commands;

import java.lang.reflect.Method;

import com.chatboard.adapter.BotAdapter;
import com.chatboard.annotation.*;
import com.chatboard.commandparser.Command;
import com.chatboard.commandparser.Commands;
import com.chatboard.exceptions.PermissionException;
import com.chatboard.util.ParserUtils;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.User;

@HelpText(value = "Shows a list of commands or gives help on a specific one")
public class Help extends Command {

    @Runner @NoAdmin
    public void help() {
        
        User u = getUser();
        
        MessageBuilder mb = new MessageBuilder();
        mb.append(u.getAsMention() + " ");
        mb.append("A list of available commands:");
        
        String commands = "";
        
        for(Commands c : Commands.getAllCommands()) {
            if(!getUserIsAdmin() && !c.hasNoAdminMethods()) {
                continue;
            }
            if(commands.length() != 0) {
                commands += "; ";
            }
            commands += c.getFriendlyName();
        }
        
        mb.appendCodeBlock(commands, "");
        getTextChannel().sendMessage(mb.build()).complete();
    }
    
    @Runner @NoAdmin @ArgNames(names = {"command_name"})
    public void help(String commandName) {
        
        Commands c = Commands.getMatchingCommand(commandName);
        
        if(!c.hasNoAdminMethods() && !getUserIsAdmin()) {
            throw new PermissionException();
        }
        
        Class<? extends Command> cl = c.getImplementingClass();
        
        MessageBuilder mb = new MessageBuilder();
        mb.append(getUser().getAsMention() + " ");
        mb.append("Help on the" + " ");
        mb.append("`" + c.getFriendlyName() + "` command:");
        
        String message = "";
        
        message += "+ ";
        message += c.name();
        
        if(cl.isAnnotationPresent(HelpText.class)) {
            message += "\n";
            message += cl.getAnnotationsByType(HelpText.class)[0].value();
            message += "\n";
        }
        
        message += "\n";
        
        for(Method me : cl.getMethods()) {
            if(!me.isAnnotationPresent(Runner.class)) {
                continue;
            }
            if(me.isAnnotationPresent(Disabled.class)) {
                continue;
            }
            if(!me.isAnnotationPresent(NoAdmin.class) && !getUserIsAdmin()) {
                continue;
            }
            
            String[] params = ParserUtils.getParameterRepresentations(me);
            
            message += BotAdapter.getCommandCharacter() + c.name();
            message += " ";
            
            for(String param : params) {
                message += param + " ";
            }
            
            message += "\n";
        }
        
        mb.appendCodeBlock(message, "diff");
        getTextChannel().sendMessage(mb.build()).complete();
        
    }
    
}
