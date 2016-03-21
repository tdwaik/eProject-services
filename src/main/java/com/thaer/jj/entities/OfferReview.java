package com.thaer.jj.entities;

import java.sql.Timestamp;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Mar 11, 2016.
 */
public class OfferReview {

    private int id;

    private int offerId;

    private int userId;

    private int rate;

    private String comment;

    private Timestamp insertDate;

    private Timestamp lastUpdate;

    public int getId() {
        return id;
    }

    public OfferReview setId(int id) {
        this.id = id;
        return this;
    }

    public int getOfferId() {
        return offerId;
    }

    public OfferReview setOfferId(int offerId) {
        this.offerId = offerId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public OfferReview setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getRate() {
        return rate;
    }

    public OfferReview setRate(int rate) {
        this.rate = rate;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public OfferReview setComment(String comment) {
        this.comment = comment;
        return this;
    }

}
