package com.flyaway.view;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@javax.servlet.annotation.WebFilter(filterName = "WebFilter", urlPatterns = "/*")
public class AuthFilter implements Filter {
    static  Set<String> exclusions = new HashSet<>();

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        boolean loggedIn = SessionCreation.isLoggedIn(request);
        System.out.println("logged in: " + loggedIn);
        String requestURI = request.getRequestURI();
        boolean excluded  = requestURI.equals("/login") || requestURI.equals("/register") ||
                requestURI.equals("/")|| requestURI.equals("/change-password") ||
                requestURI.equals("login.jsp");
        System.out.println("excluded " + excluded + requestURI);
        if(!loggedIn && !excluded) {
            request.setAttribute("message", "please login, before viewing this page");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            System.out.println("[INFO] caught un logged in request.. forwording");
        }
        else chain.doFilter(req, resp);
    }


    public void init(FilterConfig config) throws ServletException {

    }

}
