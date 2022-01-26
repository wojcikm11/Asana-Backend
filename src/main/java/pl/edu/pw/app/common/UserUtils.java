package pl.edu.pw.app.common;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.edu.pw.app.domain.User;

public class UserUtils {

    public static User getLoggedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

