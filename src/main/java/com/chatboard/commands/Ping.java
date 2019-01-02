package com.chatboard.commands;

import com.chatboard.annotation.*;
import com.chatboard.commandparser.Command;
import com.chatboard.wrapper.JDAWrapper;

public class Ping extends Command {
    
    @Runner
    @NoAdmin
    public void runNoArgs() {
        this.getTextChannel().sendMessage(String.format("Ping: %dms", JDAWrapper.getJDA().getPing())).queue();
    }

}
