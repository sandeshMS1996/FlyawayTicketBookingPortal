package com.flyaway.controller;

import com.flyaway.models.Admin;
import com.flyaway.models.Airlines;
import com.flyaway.models.Flights;
import org.hibernate.Session;

public class FlightController {
    public boolean addNewFlight(Flights flight, int AdminId, int airlineID) {
        try (Session session = new DBConnection().getSession()) {
            session.getTransaction().begin();
            Airlines airline = session.get(Airlines.class, airlineID);
            Admin admin = session.get(Admin.class, AdminId);
            flight.setParentAirline(airline);
            flight.setAdmin(admin);
            airline.getFlights().add(flight);
            admin.getAirlines().add(airline);
            session.persist(admin);
            session.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
