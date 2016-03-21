package com.thaer.jj.entities;

import java.sql.Timestamp;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 25, 2016.
 */
public class Category {

    private int id;

    private boolean isMain;

    private int subOf;

    private String name;

    private Timestamp insertDate;

    private Timestamp lastUpdate;

    public int getId() {
        return id;
    }

    public Category setId(int id) {
        this.id = id;
        return this;
    }

    public boolean getIsMain() {
        return isMain;
    }

    public Category setIsMain(boolean isMain) {
        this.isMain = isMain;
        return this;
    }

    public int getSubOf() {
        return subOf;
    }

    public Category setSubOf(int subOf) {
        this.subOf = subOf;
        return this;
    }

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }
}
