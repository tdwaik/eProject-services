package com.thaer.jj.entities;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Feb 28, 2016.
 */
public class ItemAttribute {

    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public ItemAttribute setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ItemAttribute setName(String name) {
        this.name = name;
        return this;
    }
}
