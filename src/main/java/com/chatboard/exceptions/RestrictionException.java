package com.chatboard.exceptions;

public class RestrictionException extends BotException {

    private static final long serialVersionUID = 177944759739883254L;
    
    
    
    public RestrictionException() {
        super();
    }

    public RestrictionException(String message) {
        super(message);
    }

    public RestrictionException(Throwable throwable) {
        super(throwable);
    }

    public RestrictionException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public RestrictionException(String message, Throwable throwable, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }
    
    
    
    @Override
    public String getFriendlyName() {
        return "Parameters did not match the given restrictions";
    }

}
