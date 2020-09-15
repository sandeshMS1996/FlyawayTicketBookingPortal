package com.flyaway.view;

import com.flyaway.controller.AdminControl;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Login", urlPatterns = "/login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        if(session.isNew()) {
            session.setAttribute("loggedIn", false);
            session.setAttribute("loginAttempts", 1);
            session.setAttribute("forbid", false);
        }
            else {
            int attempts = (int) session.getAttribute("loginAttempts");
            if (attempts >= 3)
                session.setAttribute("forbid", true);
            session.setAttribute("loginAttempts", ++attempts);
            AdminControl adminControl = new AdminControl();
            if (adminControl.adminLogin(email, password)) {
                session.setAttribute("loginAttempts", 0);
                session.setAttribute("loggedIn", true);
                response.getWriter().println("success");
                /*if (session.getAttribute("reDirectURL") != null)
                    response.sendRedirect((String) session.getAttribute("reDirectURL"));
                else response.sendRedirect("home");*/
            } else {
                response.sendRedirect("login.jsp");


            }


        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}
