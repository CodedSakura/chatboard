package com.chatboard.exceptions;

public class InvalidParametersException extends BotException {

    private static final long serialVersionUID = -6875299523899133366L;
    private        Class<?>[] foundParameters  = null;
    
    
    
    public InvalidParametersException() {
        super();
    }

    public InvalidParametersException(String message) {
        super(message);
    }

    public InvalidParametersException(Throwable throwable) {
        super(throwable);
        
    }

    public InvalidParametersException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public InvalidParametersException(String message, Throwable throwable, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }
    
    
    
    public void setFoundParameters(Class<?>[] foundParameters) {
        this.foundParameters = foundParameters;
    }
    public Class<?>[] getFoundParameters() {
        return this.foundParameters;
    }

}
