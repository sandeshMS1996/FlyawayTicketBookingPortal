package com.flyaway.view;

import com.flyaway.controller.CustomerController;
import com.flyaway.models.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Enumeration;

@WebServlet(name = "PaymentGateway", urlPatterns = "/payment-gateway")
public class PaymentGateway extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);
        if (session == null
                || session.getAttribute("isValidPaymentRequest") == null
                || session.getAttribute("flightId") == null
                || session.getAttribute("numberOfPassengers") == null
                || session.getAttribute("travelDate") == null) {
            System.out.println("Returning to home.. from servlet");
            response.sendRedirect("/home");
        }
        try {
            Integer flightId = Integer.parseInt((String) session.getAttribute("flightId"));
            Integer numberOfPassengers = (Integer) session.getAttribute("numberOfPassengers");
            LocalDate travelDate = (LocalDate) session.getAttribute("travelDate");
            response.getWriter().println(flightId + " " + numberOfPassengers + " " +
                    travelDate);
            String name = request.getParameter("name");
            String email = request.getParameter("CustomerEmail");
            String phone = request.getParameter("phone");
            String upiID = request.getParameter("upiId");

            Customer cust = new Customer(name, phone, upiID, 200, numberOfPassengers);
            CustomerController customerController = new CustomerController();
            String ticket = customerController.bookTicket(flightId, cust);
            try (PrintWriter writer = response.getWriter()) {
                writer.println(
                        MessageFormatter.format(Formats.SUCCESS, "Payment successful"));
                writer.println(
                        MessageFormatter.format(Formats.SUCCESS, ("Your unique payment " +
                                "reference number is: " + ticket)));
                writer.println("<a href=\"/home\"> Return to Home</a>\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println(
                    MessageFormatter.format(Formats.ERROR, "Payment request " +
                            "failed due to unexpected error"));
            request.getRequestDispatcher("/home").include(request, response);

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Got payment request.. POST");
        response.sendRedirect("/home");
    }
}
