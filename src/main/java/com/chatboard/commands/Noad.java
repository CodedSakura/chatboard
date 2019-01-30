package com.chatboard.commands;

import com.chatboard.annotation.*;
import com.chatboard.commandparser.Command;
import com.chatboard.exceptions.PermissionException;
import com.chatboard.util.RoleUtil;

import net.dv8tion.jda.core.MessageBuilder;

@HelpText(value = "Toggles the No-Admin mode for the server, for testing purposes")
public class Noad extends Command {
    
    @Runner @NoAdmin
    public void noad() {
        
        if(!RoleUtil.isAdminIgnoringNoAd(getUser())) {
            throw new PermissionException();
        }
        
        MessageBuilder mb = new MessageBuilder();
        
        boolean noAd = RoleUtil.isNoAdMode();
        if(noAd) {
            mb.append(getUser().getAsMention() + " ");
            mb.append("No-Admin mode is now `OFF`");
            
            RoleUtil.setNoAdMode(false);
        } else {
            mb.append(getUser().getAsMention() + " ");
            mb.append("No-Admin mode is now `ON`");
            
            RoleUtil.setNoAdMode(true);
        }
        
        getTextChannel().sendMessage(mb.build()).complete();
    }
    
}
