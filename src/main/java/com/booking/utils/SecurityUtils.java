package com.booking.utils;


import com.booking.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static Long getCurrentUserId() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserPrincipal) {
            return ((UserPrincipal) principal).getUserId();
        }
        return null;
    }

    public static String getCurrentUsername() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserPrincipal) {
            return ((UserPrincipal) principal).getUsername();
        }
        return null;
    }

}