package com.flyaway.view;

import com.flyaway.controller.FlightController;
import com.flyaway.controller.TicketController;
import com.flyaway.models.Flights;
import com.flyaway.models.Places;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "BookNewTicket", urlPatterns = "/home")
public class BookNewTicket extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }
        HttpSession session = request.getSession(true);
        response.setContentType("text/html");
        try (final PrintWriter writer = response.getWriter()) {
            if (!request.getParameterMap().values().stream().allMatch(Objects::nonNull)) {
                writer.println(
                        MessageFormatter.format(Formats.ERROR, "Please fill all the detail."));
                request.getRequestDispatcher("index.jsp").include(request, response);
                return;
            }
            try {
                LocalDate travelDate =
                        LocalDate.parse(request.getParameter("travelDate"));
                int place = Integer.parseInt(request.getParameter("place"));
                //int numberOfPassengers = Integer.parseInt(request.getParameter("passengers"));
                String dayOfWeek = travelDate.getDayOfWeek().toString();
                //session.setAttribute("numberOfPassengers", numberOfPassengers);
                session.setAttribute("travelDate", travelDate);
                FlightController controller = new FlightController();
                List<Flights> flights = controller.getFlightsByWeekday(dayOfWeek, place);
                if (flights == null) {
                    writer.println(MessageFormatter.format(Formats.ERROR,
                            "Unexpected error accounted.. please try again later"));
                    return;
                }
                request.setAttribute("flights", flights);
                request.getRequestDispatcher("choose-your-flight.jsp").include(request, response);

            } catch (Exception e) {
                e.printStackTrace();
                writer.println(
                        MessageFormatter.format(Formats.ERROR, "Please fill the correct detail."));
                request.getRequestDispatcher("index.jsp").include(request, response);

            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final List<Places> places = TicketController.getAllPlaces();
        System.out.println(places);
        if (places == null) {
            response.getWriter().println(
                    MessageFormatter.format(Formats.ERROR,
                            "UnExpected error.. Please contact administrator"));
        } else {
            request.setAttribute("places", places);
            request.getRequestDispatcher("index.jsp").include(request, response);
        }
    }
}
