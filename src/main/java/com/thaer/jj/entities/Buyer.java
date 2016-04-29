package com.thaer.jj.entities;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */
public class Buyer {

    private int id;

    private String email;

    private String status;

    private String firstname;

    private String lastname;

    private BigInteger phone;

    private String gender;

    private Timestamp insertDate;

    private Timestamp lastUpdate;

    public int getId() {
        return id;
    }

    public Buyer setId(int id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Buyer setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Buyer setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public Buyer setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public Buyer setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public BigInteger getPhone() {
        return phone;
    }

    public Buyer setPhone(BigInteger phone) {
        this.phone = phone;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public Buyer setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public Timestamp getInsertDate() {
        return insertDate;
    }

    public Buyer setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
        return this;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public Buyer setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

}
