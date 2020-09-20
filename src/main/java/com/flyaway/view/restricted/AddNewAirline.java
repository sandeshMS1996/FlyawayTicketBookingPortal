package com.flyaway.view.restricted;

import com.flyaway.controller.AirlinesControl;
import com.flyaway.models.Airlines;
import com.flyaway.view.Formats;
import com.flyaway.view.MessageFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

@WebServlet(name = "AddNewAirline", urlPatterns = "/restricted/add-new-airline")
public class AddNewAirline extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final HttpSession session = request.getSession(false);
        response.setContentType("text/html");
        try (final PrintWriter writer = response.getWriter()) {
            if (session == null || session.getAttribute("user") == null) {
                writer.println(MessageFormatter.format(
                        Formats.ERROR, "please login before adding/updating data.."));
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            Properties properties = new Properties();
            try (InputStream input = getServletContext().getResourceAsStream("WEB-INF//regexp.properties")) {
                properties.load(input);
            }
            Set<String> errors = validateInput(request, properties);
            if (errors.size() == 0) {
                final Integer adminId = (Integer) session.getAttribute("user");
                System.out.println("[INFO] Admin + " + adminId + "adding new Flight");

                String name = request.getParameter("airlineName");
                String country = request.getParameter("airlineCountry");

                Airlines airlines = new Airlines(name, country);

                AirlinesControl airlinesControl = new AirlinesControl();
                boolean flag = airlinesControl.addNewAirline(airlines, adminId);
                if (flag) {

                    writer.println(MessageFormatter.format(
                            Formats.SUCCESS,
                            "new Airline \"" + name + "\" has been added successfully"));
                    writer.println(
                            "<a href=\"welcome.html\">Go to Home</a>\n"
                    );

                } else {
                    writer.println(
                            MessageFormatter.format(
                                    Formats.ERROR,
                                    "new Airline \"" + name + "\" Could not be added. please check the logs"));
                    request.getRequestDispatcher("add-new-airline.jsp").include(request, response);

                }
            } else {
                String message = "";
                for (String error : errors)
                    message += error + ", ";
                message += " \nPlease try again";
                writer.println(MessageFormatter.format(
                        Formats.ERROR, message));
                request.getRequestDispatcher("add-new-airline.jsp").include(request, response);

            }
        }
    }


    private Set<String> validateInput(HttpServletRequest request, Properties properties) {
        Set<String> errors = new HashSet<>();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String s = names.nextElement();
            if (!Pattern.matches(properties.getProperty(s), request.getParameter(s)))
                errors.add(s + " is not correct");
        }

        return errors;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/restricted/add-new-airline.jsp");

    }
}
