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
import java.io.PrintWriter;

@WebServlet(name = "Login", urlPatterns = "/login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if(email == null || password == null) {
            request.setAttribute("message", "invalid credentials");
            request.getRequestDispatcher("login.jsp").include(request, response);
        }
        HttpSession session = request.getSession(true);
        System.out.println("new Session: " + session.getId());
        int attempts = 0;
        if (session.getAttribute("loginAttempts") != null)
            attempts = (int) session.getAttribute("loginAttempts");
        AdminControl adminControl = new AdminControl();
        Admin admin = adminControl.adminLogin(email);
        response.setContentType("text/html");
        try (final PrintWriter writer = response.getWriter()) {
            if (admin == null) {
                writer.println(MessageFormatter.format(Formats.ERROR,
                        "Entered email \"" + email + "\" is invalid, " +
                                "Please Register before Logging in"));
                request.getRequestDispatcher("login.jsp").include(request, response);
            } else if (admin.getPassword().equals(password)) {
                session.removeAttribute("loginAttempts");
                session.setAttribute("user", admin.getAdminId());
                response.sendRedirect("restricted/welcome.html");
            } else {
                writer.println("Invalid credentials");
                session.setAttribute("loginAttempts", ++attempts);
                request.getRequestDispatcher("login.jsp").include(request, response);

            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").include(request, response);

    }
}
