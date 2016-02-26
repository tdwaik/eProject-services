package com.thaer.jj.entities;

/**
 * Created by thaer on 2/25/16.
 */
public class Category {

    private int id;

    private int isMain;

    private int subOf;

    private String name;

    public int getId() {
        return id;
    }

    public Category setId(int id) {
        this.id = id;
        return this;
    }

    public int getIsMain() {
        return isMain;
    }

    public Category setIsMain(int isMain) {
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
