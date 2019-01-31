package com.chatboard.commands;

import com.chatboard.commandparser.Command;
import com.chatboard.annotation.Runner;
import com.chatboard.random.RandomnessProvider;
import com.chatboard.annotation.*;

@HelpText(value = "Rolls a D20 (a 20-sided die) and outputs the result")
public class D20 extends Command {
    
    @Runner @NoAdmin
    public void d20() {
        int number = RandomnessProvider.getRandomInt(20) + 1;
        
        String message  = getUser().getAsMention();
               message += " ";
        
        message += RandomnessProvider.getRandomArrayElement(new String[] {
                "Rolling a D20... it\'s",
                "Tossing a D20... it\'s",
                "The D20 says:",
                "The mighty D20 says:",
                ""
        });
        
        message += " ";
        message += number;
        
        getTextChannel().sendMessage(message).complete();
        
    }
    
}
