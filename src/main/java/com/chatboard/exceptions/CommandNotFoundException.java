package com.chatboard.exceptions;

public class CommandNotFoundException extends BotException {

    private static final long serialVersionUID = 4227111345664378156L;
    
    
    
    protected CommandNotFoundException() {}

    public CommandNotFoundException(String message) {
        super(message);
    }

    public CommandNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public CommandNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CommandNotFoundException(String message, Throwable throwable, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }
    
    
    
    @Override
    public String getFriendlyName() {
        return "Command not found";
    }

}
