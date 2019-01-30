package com.chatboard.commands;

import com.chatboard.annotation.Runner;
import com.chatboard.commandparser.Command;
import com.chatboard.exceptions.BotException;
import com.chatboard.exceptions.InvalidParametersException;
import com.chatboard.adapter.BotAdapter;
import com.chatboard.annotation.*;

import net.dv8tion.jda.core.MessageBuilder;

/**
 * A command for changing the command character
 */
@HelpText(value = "A command for changing the command character")
public class Ccc extends Command {
    
    @Runner @ArgNames(names = {"command_character"})
    public void changeCommandCharacter(String cc) {
        
        if(cc.length() < 1 || cc.length() > 4) {
            throw new InvalidParametersException("Command string length has to be between 1 and 4 chars long");
        }
        
        MessageBuilder mb = new MessageBuilder();
        mb.append(getUser().getAsMention() + " ");
        mb.append("Command string changed from" + " ");
        mb.append("`" + BotAdapter.getCommandCharacter() + "`" + " ");
        mb.append("to `" + cc + "`");
        
        char[] allowedCharacters = {'>', '<', '!', '_', '!', '.', '#', ':', ';', '[', ']', '|', '(', ')', '='};
        for(int i = 0; i < cc.length(); i++) {
            boolean legal   = false;
            char    current = cc.charAt(i);
            
            for(char c : allowedCharacters) {
                if(c == current) {
                    legal = true;
                }
            }
            
            if(!legal) {
                throw new BotException("Illegal characters in command string");
            }
        }
        
        BotAdapter.setCommandCharacter(cc);
        getTextChannel().sendMessage(mb.build()).complete();
        
    }
    
    @Runner
    public void resetCommandCharacter() {
        MessageBuilder mb = new MessageBuilder();
        mb.append(getUser().getAsMention() + " ");
        mb.append("Command string reset from" + " ");
        mb.append("`" + BotAdapter.getCommandCharacter() + "`" + " ");
        
        BotAdapter.resetCommandCharacter();
        
        mb.append("to `" + BotAdapter.getCommandCharacter() + "`");
        
        getTextChannel().sendMessage(mb.build()).complete();
    }
    
}
