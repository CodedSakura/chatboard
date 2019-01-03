package com.chatboard.commandparser;

import com.chatboard.exceptions.BotException;
import com.chatboard.exceptions.CommandNotFoundException;
import com.chatboard.exceptions.InvalidParametersException;
import com.chatboard.exceptions.InvalidSyntaxException;
import com.chatboard.exceptions.PermissionException;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandFlowExecutor {

    private CommandFlowExecutor() {}
    
    
    
    public static void executeCommandFlow(String arguments, TextChannel tc, User u) {
        try {
            Commands c = Commands.getMatchingCommand(arguments);
            c.run(arguments, tc, u);
        } catch(CommandNotFoundException e) {
            sendErrorMessage("Command not found", "Try >help", tc, u);
            return;
        } catch(PermissionException e) {
            sendErrorMessage("Insufficient permission", null, tc, u);
            return;
        } catch(InvalidParametersException e) {
            String message = "No command found for parameters: ";
            Class<?>[] foundParameters = e.getFoundParameters();
            if(foundParameters.length == 0) {
                message += "<none>";
            } else {
                for(Class<?> c : foundParameters) {
                    message += "[";
                    message += FriendlyTypeName.getFriendlyName(c);
                    message += "] ";
                }
            }
            sendErrorMessage("Invalid parameters", message, tc, u);
            return;
        } catch(InvalidSyntaxException e) {
            sendErrorMessage("Invalid command syntax", null, tc, u);
            return;
        } catch(BotException e) {
            sendErrorMessage(e.getFriendlyName(), e.getMessage(), tc, u);
            return;
        } catch(Throwable e) {
            e.printStackTrace();
            return;
        }
    }
    
    private static void sendErrorMessage(String errorText, String subText, TextChannel tc, User u) {
        MessageBuilder mb = new MessageBuilder();
        mb.append(u.getAsMention());
        if(subText != null) {
            mb.appendCodeBlock("- " + errorText + "\n" + subText, "diff");
        } else {
            mb.appendCodeBlock("- " + errorText, "diff");
        }
        
        
        tc.sendMessage(mb.build()).complete();
    }

}
