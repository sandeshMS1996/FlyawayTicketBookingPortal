package com.flyaway.view.restricted;

import com.flyaway.controller.AirlinesControl;
import com.flyaway.controller.FlightController;
import com.flyaway.models.FlightSchedule;
import com.flyaway.models.Places;
import com.flyaway.models.Flights;
import com.flyaway.view.Formats;
import com.flyaway.view.MessageFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static com.flyaway.view.restricted.ValidateInput.validateInput;

@WebServlet(name = "AddNewFlight", urlPatterns = "/restricted/add-new-flight")
public class AddNewFlight extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        final HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            request.setAttribute("message", "please login before adding/updating data..");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        Properties properties = new Properties();
        try (InputStream input = getServletContext().getResourceAsStream("WEB-INF//regexp.properties")) {
            properties.load(input);
        }

        Set<String> errors = validateInput(request, properties);
        try {
            String to = request.getParameter("to").toLowerCase();
            String from = request.getParameter("from").toLowerCase();
            if(from.equalsIgnoreCase(to))
                errors.add("Source and destination cannot be same");
        }catch (Exception e) {
            response.getWriter().println("PLease Enter the correct details and try again...");
            doGet(request, response);
        }
        if(errors.size() != 0) {
            String message = "";
            for (String error : errors)
                message += error + ", ";
            message += " \nPlease try again";
            response.getWriter().println(MessageFormatter.format(
                    Formats.ERROR, message));
            response.getWriter().println("<a href=\"/restricted/add-new-flight.jsp\">Add new Flight</a>\n");    
            return;
        }
        try {
            final Integer adminId = (Integer) session.getAttribute("user");
            System.out.println("[INFO] Admin + " + adminId + "adding new Flight");
            int airLineId = Integer.parseInt(request.getParameter("airline"));
            String from = request.getParameter("from").toLowerCase();
            String to = request.getParameter("to").toLowerCase();
            LocalTime departureTime = LocalTime.parse(request.getParameter("departure"), DateTimeFormatter.ofPattern("HH:mm"));
            //final String frequency = request.getParameter("frequency");
            final String[] frequencies = request.getParameterValues("frequency");
            Set<FlightSchedule> flightFrequency = new HashSet<FlightSchedule>();
            for (String frequency : frequencies) {
                flightFrequency.add(FlightSchedule.valueOf(frequency));
            }

            Double price = Double.parseDouble(request.getParameter("price"));
            Places place = new Places(from, to, 0.0);
            Flights flight = new Flights(departureTime, flightFrequency, price);
            //place.setFlight(flight);
            flight.setPlaces(place);
            System.out.println(flight);
            FlightController flightController = new FlightController();

            try (final PrintWriter writer = response.getWriter()) {
                if (flightController.addNewFlight(flight, place,  adminId, airLineId)) {
                    writer.println("    <h3 style=\"background-color: chartreuse\"> New Flight has been added successfully</h3>\n");
                    request.getRequestDispatcher("welcome.html").include(request, response);

                } else {
                    writer.println("<h3 style=\"background-color: red\">" +
                            "Could not add flight due to unexpected error<br>\n" +
                            "        please check the server log and try again..\n" +
                            "    </br>..</h3>\n" +
                            "    ");
                    //request.getRequestDispatcher("/restricted/add-new-flight").include(request, response);

                }
            }

        }catch (Exception e) {
            response.getWriter().println("PLease Enter the correct details and try again...");
            doGet(request, response);
        }

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("in get metod of add new flight");
        final Map<Integer, String> names = AirlinesControl.getAllAirLineNames();
        if (names.size() == 0) {
            System.out.println("[INFO] Airline size is 0");
            request.setAttribute("message", "Please add an airline before adding the Flights..");
            request.getRequestDispatcher("/restricted/add-new-airline.jsp").
                    forward(request, response);

        } else {
            request.setAttribute("airLines", names);
            System.out.println(request.getAttribute("airLines"));
            request.getRequestDispatcher("/restricted/add-new-flight.jsp").include(request, response);
        }
    }

}
