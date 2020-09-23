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
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import static com.flyaway.view.restricted.ValidateInput.validateInput;

@WebServlet(name = "PaymentGateway", urlPatterns = "/payment-gateway")
public class PaymentGateway extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Properties properties = new Properties();
        try (InputStream input = getServletContext().getResourceAsStream("WEB-INF//regexp.properties")) {
            properties.load(input);
        }
        response.setContentType("text/html");
        Set<String> errors = validateInput(request, properties);
        System.out.println("error size " + errors.size());
        if (errors.size() != 0) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            String s = "";
            for (String error : errors)
                s += (error + ", ");
            response.getWriter().println(MessageFormatter.format(Formats.ERROR, s));
            response.getWriter().println("<a href=\"/home\">Try Again </a>\n");
            return;
        }
        HttpSession session = request.getSession(false);
        if (session == null
                || session.getAttribute("isValidPaymentRequest") == null
                || session.getAttribute("flightId") == null
                || session.getAttribute("travelDate") == null) {
            System.out.println("Returning to home.. from servlet  .. some session " +
                    "attributes are null");
            request.getRequestDispatcher("/home").include(request, response);
            return;
        }
        try {
            Integer flightId = Integer.parseInt((String) session.getAttribute("flightId"));
            Integer numberOfPassengers =
                    Integer.parseInt(request.getParameter("numberOfPassengers"));
            Double price =
                    Double.parseDouble((String) session.getAttribute("price"));
            //LocalDate travelDate = (LocalDate) session.getAttribute("travelDate");
            String name = request.getParameter("firstname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String upiID = request.getParameter("upiId");
            double paymentMade = price * numberOfPassengers;

            Customer cust = new Customer(name, phone, email, upiID, paymentMade, numberOfPassengers);
            CustomerController customerController = new CustomerController();
            String ticket = customerController.bookTicket(flightId, cust);
            try (PrintWriter writer = response.getWriter()) {
                String msg = "<hr>Rupees " + paymentMade + " has been deducted from your Account..<br>"
                        + "Your unique payment reference number is: " + ticket + "<hr><br><br>" +
                        "<a href=\"/home\"> Return to Home</a>";

                writer.println(
                        MessageFormatter.format(Formats.SUCCESS, msg));

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

