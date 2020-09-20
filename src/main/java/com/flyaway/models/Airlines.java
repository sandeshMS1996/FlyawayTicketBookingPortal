package com.flyaway.models;


import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airlines")
public class Airlines {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int airlineID;

    @NotNull
    @Column(name = "airline_name", length = 30, nullable = false)
    private String name;

    @NotNull
    @Column(name = "home_country", length = 20, nullable = false)
    private String Country;

    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Flights> flights = new ArrayList<>();

    public Airlines() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin", updatable = false)
    private Admin admin;


    public Airlines(String name, String country) {
        this.name = name;
        Country = country;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<Flights> getFlights() {
        return flights;
    }

    public void setFlights(List<Flights> routes) {

        this.flights = routes;
    }

    public int getAirlineID() {
        return airlineID;
    }

    public void setAirlineID(int airlineID) {
        this.airlineID = airlineID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    @Override
    public String toString() {
        return "Airlines{" +
                "airlineID=" + airlineID +
                ", name='" + name + '\'' +
                ", Country='" + Country + '\'' +
                '}';
    }
}
