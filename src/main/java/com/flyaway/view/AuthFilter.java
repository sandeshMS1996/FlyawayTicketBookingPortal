package com.flyaway.view;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@javax.servlet.annotation.WebFilter(filterName = "AuthFilter", urlPatterns = "/restricted/*")
public class AuthFilter implements Filter {
    static  Set<String> exclusions = new HashSet<>();

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        System.out.println("filtering " + request.getRequestURI());
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute("user") != null;
        if(!isLoggedIn) {
            request.setAttribute("message", "please login, before viewing this page");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            System.out.println("[INFO] caught un logged in request.. forwarding");
        }
        else chain.doFilter(req, resp);
    }


    public void init(FilterConfig config) throws ServletException {

    }

}
