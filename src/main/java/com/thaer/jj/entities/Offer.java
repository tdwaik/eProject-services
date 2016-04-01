package com.thaer.jj.entities;

import java.sql.Timestamp;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 25, 2016.
 */
public class Offer {

    private int id;

    private int sellerId;

    private int brandId;

    private int categoryId;

    private String title;

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

    public int getSellerId() {
        return sellerId;
    }

    public Offer setSellerId(int sellerId) {
        this.sellerId = sellerId;
        return this;
    }

    public int getBrandId() {
        return brandId;
    }

    public Offer setBrandId(int brandId) {
        this.brandId = brandId;
        return this;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public Offer setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Offer setTitle(String title) {
        this.title = title;
        return this;
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
