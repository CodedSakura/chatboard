package com.chatboard.exceptions;

/**
 * An exception for indicating that the syntax of a
 * command is invalid
 */
public class InvalidSyntaxException extends BotException {

    private static final long serialVersionUID = -7959997736832825114L;
    
    
    
    public InvalidSyntaxException() {
        super();
    }

    public InvalidSyntaxException(String message) {
        super(message);
    }

    public InvalidSyntaxException(Throwable throwable) {
        super(throwable);
    }

    public InvalidSyntaxException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public InvalidSyntaxException(String message, Throwable throwable, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }
    
    
    
    @Override
    public String getFriendlyName() {
        return "Syntax error";
    }

}
