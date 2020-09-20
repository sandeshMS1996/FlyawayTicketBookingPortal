package com.flyaway.models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "admin_details")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "admin_id", updatable = false, nullable = false)
    private Integer adminId;

    @Column(name = "first_name", nullable = false, length = 10)
    @NotNull
    private String firstName;

    @Column(name = "last_name", length = 10, nullable = true)
    private String lastName;

    @Column(name = "phone_number", length = 15)
    private String phoneNo;

    @NotNull
    @Column(name = "email_id", unique = true, nullable = false)
    private String emailID;

    @NotNull
    @Column(name = "password",length = 64, nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "admin")
    List<Airlines> airlines = new ArrayList<Airlines>();

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "admin")
    List<Flights> routes = new ArrayList<>();

    public Admin() {
    }



    public Admin(String firstName, String lastName, String phoneNo, String emailID, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.emailID = emailID;
        this.password = password;
    }

    public List<Flights> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Flights> routes) {
        this.routes = routes;
    }

    public List<Airlines> getAirlines() {
        return airlines;
    }

    public void setAirlines(List<Airlines> admin) {
        this.airlines = admin;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }



    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", emailID='" + emailID + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
