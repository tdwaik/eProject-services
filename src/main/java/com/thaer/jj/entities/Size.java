package com.thaer.jj.entities;

import java.sql.Timestamp;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 10, 2016.
 */
public class Size {

    private int id;

    private String name;

    private String type;

    private Timestamp insertDate;

    private Timestamp lastUpdate;

    public int getId() {
        return id;
    }

    public Size setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Size setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public Size setType(String type) {
        this.type = type;
        return this;
    }
}
