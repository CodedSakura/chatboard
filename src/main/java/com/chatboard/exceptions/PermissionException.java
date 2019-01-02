package com.chatboard.exceptions;

/**
 * An exception for indicating that the user did not have sufficient
 * permission to execute a command / perform an action
 */
public class PermissionException extends BotException {

    private static final long serialVersionUID = -8437554014730952529L;
    
    
    
    protected PermissionException() {}

    public PermissionException(String message) {
        super(message);
    }

    public PermissionException(Throwable throwable) {
        super(throwable);
    }

    public PermissionException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public PermissionException(String message, Throwable throwable, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }
    
    
    
    @Override
    public String getFriendlyName() {
        return "Insufficient permission";
    }

}
