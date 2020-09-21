package com.flyaway.models;


import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Places {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(name = "source", length = 30, nullable = false)
    private String source;

    @NotNull
    @Column(name = "destination", length = 30, nullable = false)
    private String destination;

    private Double distance;

    public Places(String source, String destination, Double distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    @OneToOne(cascade = CascadeType.ALL)
    private Flights flight;

    public Flights getFlight() {
        return flight;
    }

    public void setFlight(Flights flight) {
        this.flight = flight;
    }

    public Places() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
