package com.thaer.jj.entities;

/**
 * Created by Thaer AlDwaik on February 25, 2016.
 */
public class Item {

    private int id;

    private String title;

    private String description;

    private String picture;

    private String status;

    public int getId() {
        return id;
    }

    public Item setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Item setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Item setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public Item setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Item setStatus(String status) {
        this.status = status;
        return this;
    }
}
