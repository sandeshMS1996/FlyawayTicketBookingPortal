package com.flyaway.view;

import com.flyaway.controller.AdminControl;
import com.flyaway.models.Admin;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "Login", urlPatterns = "/login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession(true);
        System.out.println("new Session: " + session.getId());
        int attempts =1;
        if(session.getAttribute("loginAttempts") != null)
            attempts = (int) session.getAttribute("loginAttempts");
        AdminControl adminControl = new AdminControl();
        Admin admin = adminControl.adminLogin(email);
        if ((admin != null) && admin.getPassword().equals(password)) {
            session.removeAttribute("loginAttempts");
            session.setAttribute("user", admin.getEmailID());
            response.sendRedirect("restricted/welcome.html");
        }
        else {
            request.setAttribute("message", "invalid credentials");
            System.out.println("setting error message invalid credentials ");
            if(admin != null)
                session.setAttribute("loginAttempts", ++attempts);
            Enumeration<String> attributeNames = session.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String s =  attributeNames.nextElement();
                System.out.println(s + " ==> " + session.getAttribute(s));
            }
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
