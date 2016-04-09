package com.thaer.jj.entities;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Apr 08, 2016.
 */
public class Color {

    private int id;

    private String name;

    private String hex;

    public int getId() {
        return id;
    }

    public Color setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Color setName(String name) {
        this.name = name;
        return this;
    }

    public String getHex() {
        return hex;
    }

    public Color setHex(String hex) {
        this.hex = hex;
        return this;
    }
}
