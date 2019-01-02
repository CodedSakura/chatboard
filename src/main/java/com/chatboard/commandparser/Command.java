package com.chatboard.commandparser;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class Command {
    
    private User        contextUser;
    private TextChannel contextTextChannel;
    
    
    
    public User getUser() {
        return contextUser;
    }
    public void setUser(User user) {
        this.contextUser = user;
    }
    public TextChannel getTextChannel() {
        return contextTextChannel;
    }
    public void setTextChannel(TextChannel textChannel) {
        this.contextTextChannel = textChannel;
    }
    
}
