package com.flyaway.controller;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.swing.text.PlainView;
import java.util.List;

public class TicketController {

    public static List<PlainView> getAllPlaces() {
        try(Session session = new DBConnection().getSession()) {
            return  (List<PlainView>)session.createQuery("from Places ").list();
        }catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }

    }

}
