package com.flyaway.view;

import com.flyaway.controller.AdminControl;
import com.flyaway.controller.DBConnection;
import com.flyaway.models.Admin;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Pattern;

@WebServlet(name = "Registration", urlPatterns = "/register")
public class Registration extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {

        Properties properties = new Properties();
        try (InputStream input = getServletContext().getResourceAsStream("WEB-INF//regexp.properties")) {
            properties.load(input);
        }
        Set<String> errors = validateInput(request, properties);
        try (PrintWriter writer = response.getWriter()) {
            response.setContentType("text/html");
            if (errors.size() != 0) {
                writer.println("below filed(s) are not valid..");
                errors.forEach(a -> writer.print("<h5 style=\"color: red\">" + a + "</h5>"));
                errors.removeIf(Objects::nonNull);
                writer.println("Please try again...<br> <br>");
                writer.println("<form method=\"get\" action=\"/register\">\n" +
                        "        <input type=\"submit\" value=\"Try Again\" >\n" +
                        "    </form>");

            } else {
                String email = request.getParameter("email");
                Admin admin1 = new AdminControl().adminLogin(email);
                System.out.println(admin1);
                if( admin1!= null) {
                    String msg =  "There already exist an account with email " + email +"... Please login...";
                    request.setAttribute("message", msg);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                String firstName = request.getParameter("firstname");
                String lastname = request.getParameter("lastname");
                String phone = request.getParameter("phone");
                String pass = request.getParameter("password");
                System.out.println("pass : " + pass);
                Admin admin = new Admin(firstName, lastname, phone, email, pass);
                System.out.println(admin);
                AdminControl adminControl = new AdminControl();
                if(adminControl.RegisterAdmin(admin)) {
                    //HttpSession session = SessionCreation.createSession(request);
                    request.setAttribute("message", "registration success.. please login");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                else {
                    writer.println("Could not login due to unexpected error.. please try again..");
                    writer.println("<form method=\"get\" action=\"/register\">\n" +
                            "        <input type=\"submit\" value=\"Try Again\" >\n" +
                            "    </form>");
                }

            }
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("registration.html").forward(request, response);
    }

    private Set<String> validateInput(HttpServletRequest request, Properties properties) {
        Set<String> errorMessage = new HashSet<>();
        Enumeration<String> names = request.getParameterNames();
        if (!request.getParameter("password").equals(request.getParameter("repass"))) {
            errorMessage.add("password and conform password does not match..");
            return errorMessage;
        }
        while (names.hasMoreElements()) {
            String s = names.nextElement();
            if (request.getParameter(s) == null ||
                    !(Pattern.matches(properties.getProperty(s), request.getParameter(s)))) {
                if (s.equals("repass")) continue;
                errorMessage.add(s);
            }

        }

        return errorMessage;
    }
}
