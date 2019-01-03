package com.chatboard.commands;

import com.chatboard.annotation.*;
import com.chatboard.commandparser.Command;

public class Help extends Command {

    @Runner @NoAdmin
    public void help() {
        
    }

}
