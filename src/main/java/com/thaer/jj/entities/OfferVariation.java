package com.thaer.jj.entities;

import java.sql.Timestamp;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 25, 2016.
 */
public class OfferVariation {

    private int id;

    private int OfferId;

    private String color;

    private String picture;

    private int totalPictures;

    private String status;

    private Timestamp insertDate;

    private Timestamp lastUpdate;

    public int getId() {
        return id;
    }

    public OfferVariation setId(int id) {
        this.id = id;
        return this;
    }

    public int getOfferId() {
        return OfferId;
    }

    public OfferVariation setOfferId(int offerId) {
        OfferId = offerId;
        return this;
    }

    public String getColor() {
        return color;
    }

    public OfferVariation setColor(String color) {
        this.color = color;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public OfferVariation setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public int getTotalPictures() {
        return totalPictures;
    }

    public OfferVariation setTotalPictures(int totalPictures) {
        this.totalPictures = totalPictures;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public OfferVariation setStatus(String status) {
        this.status = status;
        return this;
    }

    public Timestamp getInsertDate() {
        return insertDate;
    }

    public OfferVariation setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
        return this;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public OfferVariation setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }
}
