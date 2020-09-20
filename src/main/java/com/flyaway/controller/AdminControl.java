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
            System.out.println("[INFO] Registration success userID + " + admin.getAdminId());
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
    
    public Admin adminLogin(String uname) {
        DBConnection connection = new DBConnection();
        Session session = connection.getSession();
        session.beginTransaction();
        Query query1 = session.createQuery(" from Admin  as a where a.emailID = :email");
        query1.setParameter("email", uname);
        Admin admin = (Admin) query1.uniqueResult();
        System.out.println(admin);
        session.close();
        return admin;
    }

    public boolean changePassword(Admin admin, String newPassword) {
        DBConnection connection = new DBConnection();
        Session session = connection.getSession();
        Query query = session.createQuery("update Admin a set password = : pass where a.emailID = : email");
        session.beginTransaction();
        query.setParameter("pass", newPassword);
        query.setParameter("email", admin.getEmailID());
        try {
            if(query.executeUpdate() == 1) {
                session.getTransaction().commit();
                return true;
            }
            else {
                session.getTransaction().rollback();
                return false;
            }
        }catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }

    }

    //test
    public static void main(String[] args) {
        AdminControl adminControl = new AdminControl();
        Admin admin = new Admin("sandesh", "ms",
                "9008600398","sandesnnnnh@gmail.com", "San@1234");
        System.out.println(adminControl.changePassword(admin, "1234"));
    }

}
