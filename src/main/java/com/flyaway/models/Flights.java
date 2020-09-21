package com.flyaway.models;


import com.sun.istack.NotNull;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Flights")
public class Flights {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(nullable = false)
    private LocalTime departureTime;

    @NotNull
    @ElementCollection
    @CollectionTable(
            name = "flights_frequency",
            joinColumns = @JoinColumn(name = "flight_id")
    )
    @Enumerated(EnumType.STRING)
    private Set<FlightSchedule> frequency;

    @NotNull
    @Column(nullable = false)
    private Double price;

    @NotNull
    private int totalSeats;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", updatable = false, nullable = false)
    private Admin admin;

    @Transient
    @Formula(
         "select count(*) from routes where id = id"
            )
    private int availableSeats;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parent_airline_id", updatable = false,  nullable = false)
    private Airlines ParentAirline;

    @JoinColumn(nullable = false)
    @OneToOne(mappedBy = "flight", cascade = CascadeType.ALL, optional = false)
    private Places places;

    public Flights() {
    }


    public Flights(LocalTime departureTime, Set<FlightSchedule> frequency, Double price) {
        this.departureTime = departureTime;
        this.frequency = frequency;
        this.price = price;

    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Set getFrequency() {
        return frequency;
    }


    public int getTotalSeats() {
        return totalSeats;
    }

    public void setFrequency(Set<FlightSchedule> frequency) {
        this.frequency = frequency;
    }

    public Places  getPlaces() {
        return places;
    }

    public void setPlaces(Places places) {

        this.places = places;
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

    public Airlines getParentAirline() {
        return ParentAirline;
    }

    public void setParentAirline(Airlines airlines) {
        this.ParentAirline = airlines;
    }

    @Override
    public String toString() {
        return "Routes{" +
                "id=" + id +
                ", departureTime=" + departureTime +
                ", frequency=" + frequency +
                ", price=" + price +
                ", totalSeats=" + totalSeats +
                ", admin=" + admin +
                ", availableSeats=" + availableSeats +
                ", airlines=" + ParentAirline +
                ", places=" + places +
                '}';
    }
}
