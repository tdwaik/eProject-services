package com.thaer.jj.entities;

import java.sql.Timestamp;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 25, 2016.
 */
public class OfferOption {

    private int id;

    private int OfferId;

    private String title;

    private String color;

    private String picture;

    private String status;

    private Timestamp insertDate;

    private Timestamp lastUpdate;

    public int getId() {
        return id;
    }

    public OfferOption setId(int id) {
        this.id = id;
        return this;
    }

    public int getOfferId() {
        return OfferId;
    }

    public OfferOption setOfferId(int offerId) {
        OfferId = offerId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public OfferOption setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getColor() {
        return color;
    }

    public OfferOption setColor(String color) {
        this.color = color;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public OfferOption setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public OfferOption setStatus(String status) {
        this.status = status;
        return this;
    }

    public Timestamp getInsertDate() {
        return insertDate;
    }

    public OfferOption setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
        return this;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public OfferOption setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }
}
