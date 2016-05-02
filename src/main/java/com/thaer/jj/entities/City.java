package com.thaer.jj.entities;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Apr 30, 2016.
 */
public class City {

    private int id;

    private int countryId;

    private String name;

    public int getId() {
        return id;
    }

    public City setId(int id) {
        this.id = id;
        return this;
    }

    public int getCountryId() {
        return countryId;
    }

    public City setCountryId(int countryId) {
        this.countryId = countryId;
        return this;
    }

    public String getName() {
        return name;
    }

    public City setName(String name) {
        this.name = name;
        return this;
    }
}
