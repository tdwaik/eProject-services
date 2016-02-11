package com.thaer.jj.entities;

import java.sql.Timestamp;

/**
 * Created by Thaer AlDwaik on February 10, 2016.
 */
public class User {

    private int id;

    private String username;

    private String firstname;

    private String lastname;

    private Timestamp regestrationDate;

    private String phoneNumber;

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public User setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public User setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public Timestamp getRegestrationDate() {
        return regestrationDate;
    }

    public User setRegestrationDate(Timestamp regestrationDate) {
        this.regestrationDate = regestrationDate;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
