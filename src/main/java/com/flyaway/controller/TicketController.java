package com.flyaway.controller;

import com.flyaway.models.Places;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.swing.text.PlainView;
import java.util.ArrayList;
import java.util.List;

public class TicketController {

    public static List<Places> getAllPlaces() {
        try(Session session = new DBConnection().getSession()) {
            return  (List<Places>) session.createQuery("from Places ").list();

        }catch (HibernateException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }


}
