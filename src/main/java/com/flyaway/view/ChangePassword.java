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
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

@WebServlet(name = "ChangePassword", urlPatterns = "/change-password")
public class ChangePassword extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Properties properties = new Properties();
        try (InputStream input = getServletContext().getResourceAsStream("WEB-INF//regexp.properties")) {
            properties.load(input);
        }
        Set<String> errorMsg = new HashSet<>();
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String repass = request.getParameter("repass");
        if (!password.equals(repass))
            errorMsg.add("password and conform password did not match");
        if (!Pattern.matches(properties.getProperty("password"), password))
            errorMsg.add("password did not comply with password policy");

        try (PrintWriter writer = response.getWriter()) {
            response.setContentType("text/html");
            if (errorMsg.size() == 0) {
                AdminControl adminControl = new AdminControl();
                Admin admin = adminControl.adminLogin(email);
                if (admin != null && admin.getPhoneNo().equals(phone)) {
                    System.out.println("inside update loop");
                    HttpSession session = request.getSession(false);
                    if (session != null)
                        session.invalidate();
                    if (adminControl.changePassword(admin, password)) {
                        request.setAttribute("message", "password changed successfully");
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                    } else errorMsg.add("entered email and phone number combination is invalid..");

                } else
                    errorMsg.add("entered email and phone number combination is invalid..");

            }
            if (errorMsg.size() != 0) {
                writer.println("below errors are identified..");
                errorMsg.forEach(a -> writer.print("<h5 style=\"color: red\">" + a + "</h5>"));
                errorMsg.removeIf(Objects::nonNull);
                writer.println("Please try again...<br> <br>");
                writer.println("<form method=\"get\" action=\"/change-password\">\n" +
                        "        <input type=\"submit\" value=\"Try Again\" >\n" +
                        "    </form>");

            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("change-password.html").forward(request, response);
    }

}
