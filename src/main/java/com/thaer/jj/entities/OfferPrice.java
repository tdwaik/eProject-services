package com.thaer.jj.entities;

import java.math.BigDecimal;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 10, 2016.
 */
public class OfferPrice {

    private int id;

    private int offerId;

    private int sizeId;

    private BigDecimal price;

    private int stockQuantity;

    public int getId() {
        return id;
    }

    public OfferPrice setId(int id) {
        this.id = id;
        return this;
    }

    public int getOfferId() {
        return offerId;
    }

    public OfferPrice setOfferId(int offerId) {
        this.offerId = offerId;
        return this;
    }

    public int getSizeId() {
        return sizeId;
    }

    public OfferPrice setSizeId(int sizeId) {
        this.sizeId = sizeId;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferPrice setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public OfferPrice setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
        return this;
    }
}
