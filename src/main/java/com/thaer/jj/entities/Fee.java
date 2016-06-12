package com.thaer.jj.entities;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since June 11, 2016.
 */
public class Fee {

    private int id;

    private int sellerId;

    private int categoryId;

    private float percentage;

    public int getId() {
        return id;
    }

    public Fee setId(int id) {
        this.id = id;
        return this;
    }

    public int getSellerId() {
        return sellerId;
    }

    public Fee setSellerId(int sellerId) {
        this.sellerId = sellerId;
        return this;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public Fee setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public float getPercentage() {
        return percentage;
    }

    public Fee setPercentage(float percentage) {
        this.percentage = percentage;
        return this;
    }
}
