package com.thaer.jj.entities;

import java.sql.Timestamp;

/**
 * Created by thaer on 2/25/16.
 */
public class Offer {

    private int id;

    private int userId;

    private int price;

    private int amount;

    private String condition;

    private String status;

    private Timestamp insertDate;

    public int getId() {
        return id;
    }

    public Offer setId(int id) {
        this.id = id;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Offer setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Offer setPrice(int price) {
        this.price = price;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public Offer setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public String getCondition() {
        return condition;
    }

    public Offer setCondition(String condition) {
        this.condition = condition;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Offer setStatus(String status) {
        this.status = status;
        return this;
    }

    public Timestamp getInsertDate() {
        return insertDate;
    }

    public Offer setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
        return this;
    }
}
