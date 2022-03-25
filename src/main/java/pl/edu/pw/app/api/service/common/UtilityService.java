package pl.edu.pw.app.api.service.common;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.edu.pw.app.domain.user.User;


public class UtilityService {

    public static String getCurrentUser(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static User getLoggedUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
