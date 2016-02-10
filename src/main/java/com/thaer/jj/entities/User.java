package com.thaer.jj.entities;

/**
 * Created by thaer on 2/10/16.
 */
public class User {

    private int id;

    private String username;

    private String firstname;

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
}
