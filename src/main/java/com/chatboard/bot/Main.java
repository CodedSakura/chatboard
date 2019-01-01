package com.chatboard.bot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException {
        new JDABuilder(AccountType.BOT).setToken(args[0]).build();
    }
}
