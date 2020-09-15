package com.flyaway.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DBConnection {
    private static StandardServiceRegistry standardServiceRegistry;
    private static Metadata metadata;
    private static SessionFactory sessionFactory;
    private Session session;

    static {
        standardServiceRegistry=
                new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").configure().build();
        metadata = new MetadataSources(standardServiceRegistry).buildMetadata();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
        System.out.println("[INFO] Successfully created session Factory..");
    }

    public DBConnection() {
        this.session =  sessionFactory.openSession();
    }

    public Session getSession() {
        assert(this.session != null):"[ERROR] Unable to create session";
        System.out.println("[INFO] successfully created a new session..");
        return this.session;
    }


}
