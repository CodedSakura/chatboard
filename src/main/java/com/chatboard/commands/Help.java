package com.chatboard.commands;

import com.chatboard.annotation.*;
import com.chatboard.commandparser.Command;
import com.chatboard.commandparser.Commands;
import com.chatboard.util.RoleUtil;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.User;

public class Help extends Command {

    @Runner @NoAdmin
    public void help() {
        
        User u = getUser();
        boolean isAdmin = RoleUtil.isAdmin(u);
        
        MessageBuilder mb = new MessageBuilder();
        mb.append(u.getAsMention() + " ");
        mb.append("A list of available commands:");
        
        String commands = "";
        
        for(Commands c : Commands.getAllCommands()) {
            if(!isAdmin && !c.hasNoAdminMethods()) {
                continue;
            }
            if(commands.length() != 0) {
                commands += "; ";
            }
            commands += c.toString();
        }
        
        mb.appendCodeBlock(commands, "");
        getTextChannel().sendMessage(mb.build()).complete();
    }

}
