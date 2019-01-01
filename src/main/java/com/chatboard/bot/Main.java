package com.chatboard.bot;

import com.chatboard.wrapper.JDAWrapper;

/** The main launcher class */
public class Main {
	
    public static void main(String... args) {
    	
    	String token = args[0];            // Takes the token as an argument
    	try {
    		JDAWrapper.initialize(token);  // Initializes the JDA in the JDA wrapper
    	} catch(Exception e) {
    		System.exit(-1);
    	}
    	
    }
    
}
