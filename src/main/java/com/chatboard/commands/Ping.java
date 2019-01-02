package com.chatboard.commands;

import com.chatboard.annotation.*;

public class Ping {

    private Ping() {}
    
    
    
    @Runner
    @NoAdmin
    public static void runNoArgs() {
        
    }

}
