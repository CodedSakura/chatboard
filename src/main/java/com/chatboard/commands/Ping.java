package com.chatboard.commands;

import com.chatboard.annotation.*;
import com.chatboard.commandparser.Command;
import com.chatboard.wrapper.JDAWrapper;

@HelpText(value = "Shows information about ping")
public class Ping extends Command {
    
    @Runner
    public void runNoArgs() {
        this.getTextChannel().sendMessage(String.format("Ping: %dms", JDAWrapper.getJDA().getPing())).complete();
    }

}
