package com.flyaway.controller;

import com.flyaway.models.Customer;
import com.flyaway.models.Flights;
import org.hibernate.Session;

import java.util.UUID;

public class CustomerController {

    public String bookTicket(int flightId, Customer customer) {
        Session session = new DBConnection().getSession();
        session.getTransaction().begin();
        Flights flight = session.get(Flights.class, flightId);
        UUID paymentRefNumber = null;
        if(flight != null) {
            customer.setFlight(flight);
            session.persist(customer);
            session.getTransaction().commit();
            paymentRefNumber = customer.getUuid();
            return paymentRefNumber.toString();
        }
        return null;
    }

}
