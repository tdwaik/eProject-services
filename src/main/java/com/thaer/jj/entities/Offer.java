package com.thaer.jj.entities;

import java.sql.Timestamp;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 25, 2016.
 */
public class Offer {

    private int id;

    private int seller_id;

    private int brand_id;

    private int categoryId;

    private String description;

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

    public int getSeller_id() {
        return seller_id;
    }

    public Offer setSeller_id(int seller_id) {
        this.seller_id = seller_id;
        return this;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public Offer setBrand_id(int brand_id) {
        this.brand_id = brand_id;
        return this;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public Offer setDescription(String description) {
        this.description = description;
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
