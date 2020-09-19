package com.flyaway.models;


import com.sun.istack.NotNull;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "routes")
public class Routes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(name = "source", length = 30, nullable = false)
    private String from;


    @Column(name = "destination", length = 30, nullable = false)
    private  String to;

    @NotNull
    @Column(nullable = false)
    private LocalTime departureTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FlightSchedule frequency;

    @NotNull
    @Column(nullable = false)
    private Double price;

    @NotNull
    private int totalSeats;

    @Transient
    @Formula(
         "select count(*) from routes where id = id"
            )
    private int availableSeats;

    @ManyToOne
    private Airlines airlines;

    public Routes() {
    }

    public Routes(String from, String to, LocalTime departureTime, FlightSchedule frequency, Double price, Airlines airlines) {
        this.from = from;
        this.to = to;
        this.departureTime = departureTime;
        this.frequency = frequency;
        this.price = price;
        this.airlines = airlines;
    }

    public FlightSchedule getFrequency() {
        return frequency;
    }

    public void setFrequency(FlightSchedule frequency) {
        this.frequency = frequency;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Airlines getAirlines() {
        return airlines;
    }

    public void setAirlines(Airlines airlines) {
        this.airlines = airlines;
    }

}
