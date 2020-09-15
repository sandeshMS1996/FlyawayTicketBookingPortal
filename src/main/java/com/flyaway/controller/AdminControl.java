package com.flyaway.controller;

import com.flyaway.models.Admin;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import java.util.Set;

public class AdminControl {
    public boolean RegisterAdmin(Admin admin) {
        DBConnection connection = new DBConnection();
        Session session = connection.getSession();
        session.beginTransaction();
        session.save(admin);
        try {
            session.getTransaction().commit();
            System.out.println(session.getTransaction().getStatus());
            return true;
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            System.out.println("duplicate key");
            return false;
        }
        finally {
            session.close();
        }
    }
    
    public boolean adminLogin(String uname, String password) {
        DBConnection connection = new DBConnection();
        Session session = connection.getSession();
        session.beginTransaction();
        Query query1 = session.createQuery(" from Admin  as a where a.emailID = :email");
        query1.setParameter("email", uname);
        Admin admin = (Admin) query1.uniqueResult();
        System.out.println(admin);
        session.close();
        if(admin.getEmailID().equals(uname) && admin.getPassword().equals(password))
            return true;
        else return false;
    }

    //test
    public static void main(String[] args) {
        AdminControl control = new AdminControl();
        System.out.println(control.adminLogin("sandesh@gmail.com", "San@1234"));
    }
}
