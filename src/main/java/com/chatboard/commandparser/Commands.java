package com.chatboard.commandparser;

public enum Commands {
    
    HELP (null),
    D20  (null);
    
    
    
    private Commands(Class<?> c) {
        this.c = c;
    }
    
    
    
    private Class<?> c;
    
    
    
    public void run(String arguments) {
        Object[] args = ParserUtils.parser(arguments);
        ParserUtils.runner(c, args);
    }
    
}
