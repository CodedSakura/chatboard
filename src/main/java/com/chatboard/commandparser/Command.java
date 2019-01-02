package com.chatboard.commandparser;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class Command {
    
    private User        contextUser;
    private TextChannel contextTextChannel;
    
    
    
    public User getContextUser() {
        return contextUser;
    }
    public void setContextUser(User contextUser) {
        this.contextUser = contextUser;
    }
    public TextChannel getContextTextChannel() {
        return contextTextChannel;
    }
    public void setContextTextChannel(TextChannel contextTextChannel) {
        this.contextTextChannel = contextTextChannel;
    }
    
}
