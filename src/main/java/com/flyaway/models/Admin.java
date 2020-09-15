package com.flyaway.models;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "admin_details")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "admin_id")
    private Long adminId;

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

    public Admin() {
    }

    public Admin(String firstName, String lastName, String phoneNo, String emailID, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.emailID = emailID;
        this.password = password;
    }

    public Long getId() {
        return adminId;
    }

    public Long getAdminId() {
        return adminId;
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

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getPassword() {
        return password;
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
