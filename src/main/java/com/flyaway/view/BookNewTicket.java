package com.flyaway.view;

import com.flyaway.controller.FlightController;
import com.flyaway.controller.TicketController;
import com.flyaway.models.Flights;
import sun.security.krb5.internal.Ticket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.PlainView;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "BookNewTicket", urlPatterns = "/home")
public class BookNewTicket extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try (final PrintWriter writer = response.getWriter()) {
            if (!request.getParameterMap().entrySet().stream().allMatch(Objects::nonNull)) {
                writer.println(
                        MessageFormatter.format(Formats.ERROR, "Please fill all the detail."));
                request.getRequestDispatcher("index.jsp").include(request,response);
                return;
            }
            int place = 0;
            int numberOfPassengers = 0;
            String dayOfWeek = null;
            try {
                LocalDate travelData =  LocalDate.parse(request.getParameter("travelDate"));
                place = Integer.parseInt(request.getParameter("place"));
                numberOfPassengers = Integer.parseInt(request.getParameter("passengers"));
                dayOfWeek = travelData.getDayOfWeek().toString();
            } catch (Exception e ){
                e.printStackTrace();
                writer.println(
                        MessageFormatter.format(Formats.ERROR, "Please fill the correct detail."));
                request.getRequestDispatcher("index.jsp").include(request,response);
                return;

            }
            FlightController controller = new FlightController();
            List<Flights> flights = controller.getFlightsByWeekday(dayOfWeek, place);
            if(flights == null) {
                writer.println(MessageFormatter.format(Formats.ERROR,
                        "Unexpected error accounted.. please try again later"));
                return;
            }
            request.setAttribute("flights", flights);
            request.getRequestDispatcher("choose-your-flight.jsp").include(request,response);


        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final List<PlainView> places = TicketController.getAllPlaces();
        System.out.println(places);
        if (places == null)
            request.setAttribute("places", "0");
        else {
            request.setAttribute("places", places);
            request.getRequestDispatcher("index.jsp").include(request, response);
        }
    }
}
