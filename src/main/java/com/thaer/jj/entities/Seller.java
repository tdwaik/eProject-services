package com.thaer.jj.entities;

import java.sql.Timestamp;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since April 23, 2016.
 */
public class Seller {

    private int id;

    private String email;

    private String name;

    private String status;

    private Timestamp insertDate;

    private Timestamp lastUpdate;

    public int getId() {
        return id;
    }

    public Seller setId(int id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Seller setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public Seller setName(String name) {
        this.name = name;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Seller setStatus(String status) {
        this.status = status;
        return this;
    }

    public Timestamp getInsertDate() {
        return insertDate;
    }

    public Seller setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
        return this;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public Seller setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

}
