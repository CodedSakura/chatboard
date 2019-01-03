package com.chatboard.adapter;

import com.chatboard.commandparser.CommandFlowExecutor;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BotAdapter extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent evt) {
        String message = evt.getMessage().getContentRaw();
        
        if(!message.startsWith(">")) {
            return;
        }
        
        CommandFlowExecutor.executeCommandFlow(message, evt.getChannel(), evt.getAuthor());
    }

}
