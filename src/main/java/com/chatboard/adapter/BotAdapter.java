package com.chatboard.adapter;

import com.chatboard.commandparser.CommandFlowExecutor;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BotAdapter extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent evt) {
        String message = evt.getMessage().getContentRaw();
        
        if(!message.startsWith(commandCharacter)) {
            return;
        }
        
        CommandFlowExecutor.executeCommandFlow(message, evt.getChannel(), evt.getAuthor());
    }
    
    private static final String ORIGINAL_CC      = ">";
    private static       String commandCharacter = ORIGINAL_CC;
    
    public static void setCommandCharacter(String cc) {
        if(cc == null || cc.length() == 0) {
            throw new IllegalArgumentException();
        }
        
        commandCharacter = cc;
    }
    public static String getCommandCharacter() {
        return new String(commandCharacter);
    }
    public static void resetCommandCharacter() {
        commandCharacter = ORIGINAL_CC;
    }

}
