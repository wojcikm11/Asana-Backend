package pl.edu.pw.app.api.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


public class UtilityService {

    public static String getCurrentUser(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
