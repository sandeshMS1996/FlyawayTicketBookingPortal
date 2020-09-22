package com.flyaway.models;

import com.flyaway.controller.DBConnection;
import com.sun.istack.NotNull;
import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Customer {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",
            strategy = "uuid2")
    public UUID uuid;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private Flights flight;

    @NotNull
    @Column(nullable = false)
    private String customerName;

    @NotNull
    @Column(nullable = false)
    private String phoneNumber;

    @NotNull
    @Column(nullable = false)
    private String upiID;

    @NotNull
    private double paymentMade;

    private int noOfPassengers;

    public Customer(String customerName,
                    String phoneNumber, String upiID, double paymentMade, int noOfPassengers) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.upiID = upiID;
        this.paymentMade = paymentMade;
        this.noOfPassengers = noOfPassengers;
    }

    public Customer() {
    }

    public UUID getUuid() {
        return uuid;
    }


    public Flights getFlight() {
        return flight;
    }

    public void setFlight(Flights flight) {
        this.flight = flight;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUpiID() {
        return upiID;
    }

    public void setUpiID(String upiID) {
        this.upiID = upiID;
    }

    public double getPaymentMade() {
        return paymentMade;
    }

    public void setPaymentMade(double paymentMade) {
        this.paymentMade = paymentMade;
    }

    public int getNoOfPassengers() {
        return noOfPassengers;
    }

    public void setNoOfPassengers(int noOfPassengers) {
        this.noOfPassengers = noOfPassengers;
    }

    public static void main(String[] args) {
        Session session = new DBConnection().getSession();
        Customer customer = new Customer();
        session.getTransaction().begin();
        session.save(customer);
        session.getTransaction().commit();

        session.close();

    }
}
