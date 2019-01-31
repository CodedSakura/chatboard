package com.chatboard.commands;

import com.chatboard.annotation.HelpText;
import com.chatboard.annotation.Name;
import com.chatboard.annotation.NoAdmin;
import com.chatboard.annotation.Runner;
import com.chatboard.commandparser.Command;
import com.chatboard.random.RandomnessProvider;

@HelpText(value = "Shows the result of an 8-ball") @Name(name = "8BALL")
public class EightBall extends Command {
    
    @Runner @NoAdmin
    public void eightball() {
        String message  = getUser().getAsMention();
               message += " ";
               message += "```\n";
        
        message += RandomnessProvider.getRandomArrayElement(new String[] {
                "It is certain.",
                "It is decidedly so." + 
                "Without a doubt.",
                "Yes - definitely.",
                "You may rely on it.",
                "As I see it, yes.",
                "Most likely.",
                "Outlook good.",
                "Yes.",
                "Signs point to yes.",
                "Reply hazy, try again.",
                "Ask again later.",
                "Better not tell you now.",
                "Cannot predict now.",
                "Concentrate and ask again.",
                "Don't count on it.",
                "My reply is no.",
                "My sources say no.",
                "Outlook not so good.",
                "Very doubtful."
        });
        
        message += "\n```";
        
        getTextChannel().sendMessage(message).complete();
        
    }
    
}
