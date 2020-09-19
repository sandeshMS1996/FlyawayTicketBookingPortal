package com.flyaway.view.restricted;

import com.flyaway.controller.AirlinesControl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

@WebServlet(name = "AddNewFlight", urlPatterns = "/restricted/add-new-flight")
public class AddNewFlight extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration<String> names = request.getParameterNames();
        try(PrintWriter writer = response.getWriter()) {
            while (names.hasMoreElements()) {
                String s =  names.nextElement();
                writer.println(s + "=> " + request.getParameter(s));
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final Map<Integer, String> names = AirlinesControl.getAllAirLineNames();
        if(names.size() == 0) {
            System.out.println("[INFO] Airline size is 0");
            request.setAttribute("message", "Please add an airline before adding the Flights..");
            request.getRequestDispatcher("/restricted/add-new-airline.jsp").
                    forward(request, response);

        }
        else {
            request.setAttribute("airLines", names);
            System.out.println(request.getAttribute("airLines"));
            request.getRequestDispatcher("/restricted/add-new-flight.jsp").forward(request, response);
        }
    }
}
