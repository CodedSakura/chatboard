package com.chatboard.wrapper;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class JDAWrapper {

	/**
	 * As this is a wrapper class,
	 * no public constructors are needed
	 */
	private JDAWrapper() {}
	
	
	
	/** The JDA that this class wraps around */
	private static JDA jda;
	
	
	
	public static void initialize(String token) throws LoginException {
		jda = new JDABuilder(AccountType.BOT).setToken(token).build();
	}
	public static JDA getJDA() {
		return jda;
	}

}
