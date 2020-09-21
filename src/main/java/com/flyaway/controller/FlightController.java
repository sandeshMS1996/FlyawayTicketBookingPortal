package com.flyaway.controller;

import com.flyaway.models.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.TypedQuery;
import java.util.List;

public class FlightController {
    public boolean addNewFlight(Flights flight, Places place, int AdminId, int airlineID) {
        try (Session session = new DBConnection().getSession()) {
            session.getTransaction().begin();
            Airlines airline = session.get(Airlines.class, airlineID);
            Admin admin = session.get(Admin.class, AdminId);
            flight.setParentAirline(airline);
            //flight.setPlaces(place);
            place.setFlight(flight);
            flight.setAdmin(admin);
            session.persist(flight);
            session.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<Flights> getFlightsByWeekday(String  weeOfTheDay, int placeID) {
        Session session = new DBConnection().getSession();
        Places p  = session.get(Places.class, placeID);
        String source = null;
        String dest =  null;
        if(p.getSource() != null && p.getDestination() != null) {
            source = p.getSource();
            dest = p.getDestination();
        } else return null;
        TypedQuery<Flights>  query =
                session.createQuery("select f from Flights f join f.frequency  fre join" +
                        " f.places place where (fre = : weekOfDay or fre = :everyDay) and" +
                        "(place.source = :src and place.destination = : dest)");
        query.setParameter("everyDay" , FlightSchedule.valueOf("EVERYDAY"));
        query.setParameter("weekOfDay", FlightSchedule.valueOf(weeOfTheDay));
        query.setParameter("src", source);
        query.setParameter("dest", dest);

        List<Flights> flights =  query.getResultList();
        return flights;
    }

    public static void main(String[] args) {
        List<Flights> sunday = new FlightController()
                .getFlightsByWeekday("SUNDAY", 17 );
        System.out.println(sunday);
    }

}
