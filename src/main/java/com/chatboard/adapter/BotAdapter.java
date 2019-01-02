package com.chatboard.adapter;

import com.chatboard.commandparser.Commands;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BotAdapter extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent evt) {
        String message = evt.getMessage().getContentRaw();
        
        if(!message.startsWith(">")) {
            return;
        }
        
        Commands c = Commands.getMatchingCommand(message);
        if(c == null) {
            return;
        }
        c.run(message, evt.getChannel(), evt.getAuthor());
    }

}
