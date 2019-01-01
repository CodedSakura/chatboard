package com.chatboard.bot;

import javax.security.auth.login.LoginException;
import com.chatboard.wrapper.JDAWrapper;

/** The main launcher class */
public class Main {
	
    public static void main(String... args) throws LoginException {
    	
    	String token = args[0];				// Takes the token as an argument
    	JDAWrapper.initialize(token);		// Initializes the JDA in the JDA wrapper
    	
    }
    
}
