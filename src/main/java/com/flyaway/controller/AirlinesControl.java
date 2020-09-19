package com.flyaway.controller;

import com.flyaway.models.Airlines;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirlinesControl {
    public boolean addNewAirline(Airlines airlines) {
        Session session =  null;
        try {
            session = new DBConnection().getSession();
            session.getTransaction().begin();
            session.save(airlines);
            session.getTransaction().commit();
            session.close();
            return true;
        }catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }finally {
            if(session != null)
                session.close();
        }



    }

    public  static Map<Integer, String> getAllAirLineNames() {
        Map<Integer, String> tuple = new HashMap<>();
        Session session = null;
        try {
           session= new DBConnection().getSession();
           session.getTransaction().begin();
           Query query = session.createQuery("from Airlines ");

           List q =  query.list();
            Airlines a = null;
            for(Object o : q) {
               a = (Airlines) o;
               tuple.put(a.getAirlineID(), a.getName());
           }

            return tuple;

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            session.close();

       }


    }


}
