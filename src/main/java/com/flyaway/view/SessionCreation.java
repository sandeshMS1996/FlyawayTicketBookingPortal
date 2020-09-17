package com.flyaway.view;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionCreation {


    public static boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("user") != null)
            return true;
        return false;
    }
}
