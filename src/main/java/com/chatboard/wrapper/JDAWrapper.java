package com.chatboard.wrapper;

import javax.security.auth.login.LoginException;

import com.chatboard.adapter.BotAdapter;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.managers.GuildController;

/**
 * Keeps the universal JDA that is used
 * throughout the whole project in an
 * accessible wrapper class
 */
public class JDAWrapper {

    /**
     * As this is a wrapper class,
     * no public constructors are needed
     */
    private JDAWrapper() {}
    
    
    
    /** The JDA that this class wraps around */
    private static JDA jda;
    
    private static Guild            guild;
    private static GuildController  gc;
    
    
    
    public static void initialize(String token) throws LoginException {
        jda = new JDABuilder(AccountType.BOT).setToken(token).build();
        jda.addEventListener(new BotAdapter());
        
        guild = jda.getGuildById("529758581810266134");
        gc    = new GuildController(guild);
    }
    public static JDA getJDA() {
        return jda;
    }
    public static Guild getGuild() {
        return guild;
    }
    public static GuildController getGuildController() {
        return gc;
    }

}
