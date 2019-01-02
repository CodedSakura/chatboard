package com.chatboard.exceptions;

/**
 * An exception type for any bot-related exceptions -
 * typically for use in situations where the parsing
 * or execution of a bot command fails
 */
public class BotException extends RuntimeException {
    
    private static final long serialVersionUID = 2494862482052533593L;
    
    
    
    protected BotException() {}

    public BotException(String message) {
        super(message);
    }

    public BotException(Throwable throwable) {
        super(throwable);
    }

    public BotException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BotException(String message, Throwable throwable, boolean enableSuppression, boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }
    
    
    
    public String getFriendlyName() {
        return "Exception";
    }
    
}
