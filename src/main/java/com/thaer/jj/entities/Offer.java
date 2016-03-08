package com.thaer.jj.entities;

import java.sql.Timestamp;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 25, 2016.
 */
public class Offer {

    private int id;

    private int userId;

    private int itemId;

    private int price;

    private int amount;

    private String condition;

    private String status;

    private Timestamp insertDate;

    private Timestamp lastUpdate;

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

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public Offer setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

}
