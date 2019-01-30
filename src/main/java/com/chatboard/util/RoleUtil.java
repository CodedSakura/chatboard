package com.chatboard.util;

import com.chatboard.wrapper.JDAWrapper;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;

public class RoleUtil {

    private RoleUtil() {}
    
    
    
    private static boolean noAdMode = false;
    
    
    
    public static boolean isNoAdMode() {
        return noAdMode;
    }
    public static void setNoAdMode(boolean noAdMode) {
        RoleUtil.noAdMode = noAdMode;
    }
    
    public static boolean isAdmin(Member m) {
        if(noAdMode) {
            return false;
        }
        return hasRole(m, "529767478260400157");
    }
    public static boolean isAdmin(User u) {
        return isAdmin(JDAWrapper.getGuild().getMember(u));
    }
    
    public static boolean isAdminIgnoringNoAd(Member m) {
        return hasRole(m, "529767478260400157");
    }
    public static boolean isAdminIgnoringNoAd(User u) {
        return isAdminIgnoringNoAd(JDAWrapper.getGuild().getMember(u));
    }
    
    public static boolean hasRole(Member m, String roleId) {
        Role[] roles = m.getRoles().toArray(new Role[] {});
        for(Role r : roles) {
            if(r.getId().equals(roleId)) {
                return true;
            }
        }
        
        return false;
    }
    public static boolean hasRole(User u, String roleId) {
        return hasRole(JDAWrapper.getGuild().getMember(u), roleId);
    }

}
