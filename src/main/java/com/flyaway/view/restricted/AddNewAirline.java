package com.flyaway.view.restricted;

import com.flyaway.controller.AirlinesControl;
import com.flyaway.models.Airlines;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

@WebServlet(name = "AddNewAirline", urlPatterns = "/restricted/add-new-airline")
public class AddNewAirline extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Properties properties = new Properties();
        try (InputStream input = getServletContext().getResourceAsStream("WEB-INF//regexp.properties")) {
            properties.load(input);
        }
        Set<String> errors = validateInput(request, properties);
        if(errors.size() == 0) {
            String name  = request.getParameter("airlineName");
            String country  = request.getParameter("airlineCountry");
            Airlines airlines = new Airlines(name, country);
            AirlinesControl airlinesControl = new AirlinesControl();
            boolean flag = airlinesControl.addNewAirline(new Airlines(name, country));
            if(flag) {
                request.setAttribute("msg",
                        "new Airline " + name + "has been added successfully");
                request.getRequestDispatcher("/restricted/add-new-trip").forward(request, response);
            }
            else {
                request.setAttribute("msg",
                        "new Airline " + name + "Could not be added, " +
                                "PLease contact admin if this persists");
                request.getRequestDispatcher("/restricted/add-new-airline").forward(request, response);

            }

        }



    }

    private Set<String> validateInput(HttpServletRequest request, Properties properties) {
        Set<String> errors = new HashSet<>();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String s =  names.nextElement();
            if(!Pattern.matches(properties.getProperty(s), request.getParameter(s)))
                errors.add(s + " is not correct");
        }

        return errors;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
