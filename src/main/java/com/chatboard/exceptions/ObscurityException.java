package com.chatboard.exceptions;

public class ObscurityException extends BotException {
    
    private static final long serialVersionUID = 4503233767850604170L;
    
    
    
    protected ObscurityException() {}

    public ObscurityException(String message) {
        super(message);
    }

    public ObscurityException(Throwable throwable) {
        super(throwable);
    }

    public ObscurityException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ObscurityException(String message, Throwable throwable, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }
    
    
    
    @Override
    public String getFriendlyName() {
        return "Obscure command";
    }

}
